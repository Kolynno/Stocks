package nick.invest.stock.database.repositories

import jakarta.transaction.Transactional
import nick.invest.stock.database.tables.StockChanges
import nick.invest.stock.database.tables.StockHistory
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockChangesRepository: CrudRepository<StockChanges, Int> {

    @Transactional
    @Modifying
    @Query("""UPDATE stock.stock_changes sc
SET change14d = (
    (SELECT close
     FROM stock.stock_history sh
     WHERE sh.ticker = sc.ticker
       AND sh.date = (
          SELECT MAX(date)
           FROM stock.stock_history
           WHERE ticker = ticker
       )
     )
     / 
     (SELECT close
      FROM stock.stock_history sh
      WHERE sh.ticker = sc.ticker
        AND sh.date = (
            SELECT MAX(date)
            FROM stock.stock_history
            WHERE ticker = sc.ticker
              AND date = GetDateWorkDaysAgo(CURDATE(), 16)
        )
     ) - 1
) * 100;
""", nativeQuery = true)
    fun updateAll()


    @Query("""SELECT info.id, ch.ticker, sh.close, ch.change14d, info.name, sh.date
FROM stock.stock_changes ch
INNER JOIN stock.stock_info info ON ch.ticker = info.ticker
INNER JOIN (
    SELECT ticker, MAX(date) AS max_date
    FROM stock.stock_history
    GROUP BY ticker
) max_date_per_ticker ON max_date_per_ticker.ticker = info.ticker
INNER JOIN stock.stock_history sh ON sh.ticker = info.ticker AND sh.date = max_date_per_ticker.max_date;

""", nativeQuery = true)
    fun getDataForTable(): List<String>
}
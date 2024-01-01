package nick.invest.stock.database

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockHistoryRepository: CrudRepository<StockHistory, Int> {

    @Query("SELECT * FROM stock.stock_history WHERE ticker = :ticker", nativeQuery = true)
    fun getStockHistoryByTicker(ticker: String): Iterable<StockHistory>
}
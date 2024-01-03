package nick.invest.stock.database

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockHistoryRepository: CrudRepository<StockHistory, Int> {

    @Query("SELECT * FROM stock.stock_history WHERE ticker = :ticker", nativeQuery = true)
    fun getStockHistoryByTicker(ticker: String): Iterable<StockHistory>

    @Query("SELECT distinct ticker FROM stock.stock_history", nativeQuery = true)
    fun getTickers(): List<String>

    @Query("SELECT  max(date) FROM stock.stock_history", nativeQuery = true)
    fun getLastDate(): String

    @Query("SELECT close, date FROM stock.stock_history WHERE ticker = :ticker AND date >= :date", nativeQuery = true)
    fun getCloseAndDateByTicketFromYear(ticker: String, date: String = "2000-01-01" ):  List<Double>

    @Query("""SELECT 
    main.ticker, 
    main.close, 
    main.date,
    main.id,
    main.low,
    main.high,
    main.open,
    main.vol,
    prev.close AS close_14_days_ago
FROM 
    stock.stock_history main
JOIN stock.stock_history prev ON main.ticker = prev.ticker
WHERE 
    main.date = (SELECT MAX(date) FROM stock.stock_history)
    AND prev.date = DATE_SUB(main.date, INTERVAL 20 DAY);
""", nativeQuery = true)
    fun getTickerDateCloseForTable(): List<StockHistory>

}
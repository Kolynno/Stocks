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
}
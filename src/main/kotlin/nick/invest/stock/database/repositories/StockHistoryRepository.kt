package nick.invest.stock.database.repositories

import nick.invest.stock.database.tables.StockHistory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockHistoryRepository: CrudRepository<StockHistory, Int> {

    @Query("SELECT * FROM stock.stock_history WHERE ticker = :ticker", nativeQuery = true)
    fun getStockHistoryByTicker(ticker: String): Iterable<StockHistory>

    @Query("SELECT distinct ticker FROM stock.stock_history", nativeQuery = true)
    fun getTickers(): List<String>

    @Query("SELECT  max(date) + INTERVAL 1 DAY FROM stock.stock_history", nativeQuery = true)
    fun getLastDatePlusOne(): String

    @Query("SELECT close, date FROM stock.stock_history WHERE ticker = :ticker AND date >= :dateStart AND date <= :dateEnd", nativeQuery = true)
    fun getCloseAndDateByTickerFromDateToDate(ticker: String, dateStart: String, dateEnd: String ):  List<Double>

    @Query("SELECT close, date FROM stock.stock_history WHERE ticker = :ticker AND date >= :dateStart", nativeQuery = true)
    fun getCloseAndDateByTickerFromDate(ticker: String, dateStart: String ):  List<Double>

    //temp
    @Query("SELECT date FROM stock.stock_history WHERE ticker = 'SBER' AND date >=  '2019-01-01' AND date <= '2024-01-01'", nativeQuery = true)
    fun getCloseAndDateByTicket():  List<String>

}
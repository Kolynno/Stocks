package nick.invest.stock.database.repositories

import nick.invest.stock.database.tables.StockHistory
import nick.invest.stock.database.tables.StockInfo
import nick.invest.stock.database.tables.StockNow
import nick.invest.stock.database.tables.StockTemplates
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockNowRepository: CrudRepository<StockNow, Int> {

    @Query("SELECT id, count, `window`, ticker FROM stock.stock_now", nativeQuery = true)
    fun getStocks(): List<StockNow>


}
package nick.invest.stock.database.repositories

import nick.invest.stock.database.tables.StockHistory
import nick.invest.stock.database.tables.StockInfo
import nick.invest.stock.database.tables.StockTemplates
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockTemplatesRepository: CrudRepository<StockTemplates, Int> {


    @Query("SELECT stock.stock_templates.percent FROM stock.stock_templates WHERE ticker=:ticker AND `window`=:window AND count=:count ", nativeQuery = true)
    fun getPercent(ticker: String, window: Int, count: Int): Double


}
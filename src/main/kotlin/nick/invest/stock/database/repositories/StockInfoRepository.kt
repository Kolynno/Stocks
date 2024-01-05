package nick.invest.stock.database.repositories

import nick.invest.stock.database.tables.StockHistory
import nick.invest.stock.database.tables.StockInfo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StockInfoRepository: CrudRepository<StockInfo, Int> {


}
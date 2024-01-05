package nick.invest.stock.database.repositories

import nick.invest.stock.database.tables.StockDividends
import org.springframework.data.repository.CrudRepository

interface StockDividendsRepository: CrudRepository<StockDividends, Int> {

}
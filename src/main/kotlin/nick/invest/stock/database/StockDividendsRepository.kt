package nick.invest.stock.database

import org.springframework.data.repository.CrudRepository

interface StockDividendsRepository: CrudRepository<StockDividends, Int> {

}
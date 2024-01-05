package nick.invest.stock.database

import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.database.tables.StockHistory
import nick.invest.stock.file.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DatabaseController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository
) {
    fun updateAllStocks() {
        val stockList: MutableList<StockHistory> = FileRepository.readDataFromCSV()

        for (stock in stockList) {
            println("Current stock: ${stock.ticker}, ${stock.date}")
            stockHistoryRepository.save(stock)
        }
    }
}

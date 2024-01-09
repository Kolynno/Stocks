package nick.invest.stock.database

import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.database.repositories.StockNowRepository
import nick.invest.stock.database.repositories.StockTemplatesRepository
import nick.invest.stock.database.tables.StockHistory
import nick.invest.stock.file.FileRepository
import nick.invest.stock.strategy.StrategyOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.max

@Service
class DatabaseController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val stockNowRepository: StockNowRepository,
    private val stockTemplatesRepository: StockTemplatesRepository,
) {
    fun updateAllStocks() {
        val stockList: MutableList<StockHistory> = FileRepository.readDataFromCSV()

        for (stock in stockList) {
            println("Current stock: ${stock.ticker}, ${stock.date}")
            stockHistoryRepository.save(stock)
        }
    }

    fun updateTemplates() {
        val stocks = stockNowRepository.getStocks()
        val so = StrategyOne()

        for (stock in stocks) {
            println(stock.ticker)
            val ticker = stock.ticker!!
            var maxPercent = 0.0
            var bestWC: Pair<Int, Int> = Pair(0,0)

            val data = so.getWindowAndCount(ticker, stockHistoryRepository)
            for (d in data) {

                val window = d.first
                val count = d.second

                val currentPercent = stockTemplatesRepository.getPercent(ticker,window, count)
                if (currentPercent > maxPercent) {
                    maxPercent = currentPercent
                    bestWC = Pair(window, count)
                }
            }
            stock.window = bestWC.first
            stock.count = bestWC.second
        }
        stockNowRepository.saveAll(stocks)
    }
}

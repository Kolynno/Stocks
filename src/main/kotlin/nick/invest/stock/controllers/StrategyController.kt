package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.strategy.StrategyOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.io.File


@Controller
@RequestMapping("/strategy")
class StrategyController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val databaseController: DatabaseController
) {

    @GetMapping(path = ["/main"])
    fun main(): String {
        return "strategy"
    }

    @GetMapping("/one")
    @ResponseBody
    fun strategyOne(@RequestParam start: String, @RequestParam end: String, @RequestParam percent: Double): String {

        val outputFile = File("output.csv")
        outputFile.bufferedWriter().use { writer ->
            val tickers = stockHistoryRepository.getTickers()
            writer.write("Ticker\tPercent\tWindow\tCount\tTotal\n")
            for (ticker in tickers) {

                val closeList =
                    stockHistoryRepository.getCloseAndDateByTickerFromYear(ticker, start, end)

                val closeValues: List<Double> = closeList

                val so = StrategyOne()

                for (w in 1..20) {
                    for (c in 1..20) {
                        val result = so.calculate(closeValues, w, c, percent)
                        writer.write("$ticker\t$result\n")
                    }
                }
            }
        }

        return "Results written to output.csv"
    }
}
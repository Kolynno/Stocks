package nick.invest.stock.controllers

import nick.invest.stock.database.*
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
    fun strategyOne(): String {

        val outputFile = File("output.txt")
        outputFile.bufferedWriter().use { writer ->
            val tickers = stockHistoryRepository.getTickers()


                val closeList = stockHistoryRepository.getCloseAndDateByTicketFromYear("SBER", "2000-01-01")
                val closeValues: List<Double> = closeList

                val so = StrategyOne()
                writer.write("SBER\n") // Write ticker name to file
                writer.write("Percent\tW\tC\n")

                for (w in 22..22) {
                    for (c in 1..1) {
                        val result = so.calculate(closeValues, w, c)
                        writer.write("$result\t$w\t$c\n")
                    }
                }
            }


        return "Results written to output.txt"
    }
}
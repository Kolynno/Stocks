package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.strategy.StrategyOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


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
        val closeList = stockHistoryRepository.getCloseAndDateByTicketFromYear("SBER", "2000-01-01")
        val closeValues: List<Double> = closeList

        val so = StrategyOne()
        println("Percent\tW\tC")
        for (w in 1..10) {
            for (c in 1..10 ) {
                so.calculate(closeValues, w,c)
            }
        }



        return "1"
    }
}
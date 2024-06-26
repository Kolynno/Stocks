package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.database.repositories.StockChangesRepository
import nick.invest.stock.database.repositories.StockDividendsRepository
import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.database.repositories.StockInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import kotlin.math.max
import kotlin.math.roundToInt


@Controller
@RequestMapping("/game")
class GameController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val databaseController: DatabaseController,
    private val stockDividendsRepository: StockDividendsRepository,
    private val stockInfoRepository: StockInfoRepository,
    private val stockChangesRepository: StockChangesRepository
) {


    @GetMapping(path = ["/play"])
    fun main(@RequestParam ticker: String, @RequestParam w: Int, @RequestParam c: Int, @RequestParam percent: Double, @RequestParam dayStart: Int): String {

        val closes = stockHistoryRepository.getCloseAndDateByTickerFromDateToDate(ticker, "2019-01-01", "2024-01-01")
        val dateList = stockHistoryRepository.getCloseAndDateByTicket()
        val closeList: List<Double> = closes

        var window = w
        while(window > 0) {

            var money = 100000
            var stocks: Int = 0
            var lastChoice = "s"
            var maxPrice = 0.0
            var buyPrice = 0.0

        var day = dayStart
        while (day < dateList.size) {


                var isOk = false
                val change16d = (closeList[day - 1] / closeList[day-16] - 1) * 100
                if (change16d > window*percent) {
                    isOk = true
                }


            val change1d = (closeList[day] / closeList[day-1] - 1) * 100
            val change1dStr = String.format("%.2f", change1d)

            //println("Day:$day, date:${dateList[day]}, price:${closeList[day]}, 1dayChange:$change1dStr, buy:$isOk")

            if (lastChoice == "b") {
                maxPrice = max(maxPrice, closeList[day])
            }


            val act = chooseAct(lastChoice, closeList[day], isOk, maxPrice, buyPrice)
            when(act){
                "b" -> {
                    stocks = (money/closeList[day]).toInt()
                    money = (money - stocks*closeList[day]).toInt()
                    lastChoice = "b"
                    buyPrice = closeList[day]
                }
                "s" -> {
                    money = (money + stocks*closeList[day]).toInt()
                    stocks = 0
                    lastChoice = "s"
                    maxPrice = 0.0
                }
            }


            //println("stocks:$stocks, money:$money")
            day++
        }

            println("TICKER:$ticker, W:$window, C:$c, Percent:$percent, Profit:${((closeList[closeList.size-1] * stocks + money)/1000).roundToInt() - 100}")
            window--
        }
        return "game"
    }

    private fun chooseAct(lastChoice: String, price: Double, isOk: Boolean, maxPrice: Double, buyPrice: Double): String {
        if (isOk && lastChoice == "s") {
            return "b"
        }
        if (lastChoice == "b" && maxPrice * 0.98 > price) {
            return "s"
        }
        if (lastChoice == "b" && buyPrice * 0.98 > price) {
            return "s"
        }
        return "\n"

    }


    private fun calculate(close: List<Double>, window: Int, count: Int, percentPerDay: Double): DoubleArray {
        val positivePercent = percentPerDay * window

        val wArraySize = close.size / window
        var currentClose = 0
        var currentWArrayIndex = 0

        var wArray = DoubleArray(wArraySize)
        while (currentClose < close.size - window) {

            val wEnd = close[currentClose + window]
            val wStart = close[currentClose]

            // Save percent
            wArray[currentWArrayIndex] = (wEnd / wStart - 1) * 100

            currentClose += window
            currentWArrayIndex++
        }

        return calculateResult(wArray, count, positivePercent, window)
    }

    private fun calculateResult(wArray: DoubleArray, count: Int, positivePercent: Double, window: Int): DoubleArray {
        var total = 0
        var positive = 0

        var currentWIndex = 0
        while (currentWIndex < wArray.size - count) {

            var daySum = 0.0
            var isOk = true
            for (i in 0 until count) {
                daySum += wArray[currentWIndex + i]
            }

            if (daySum < positivePercent) {
                isOk = false
            }
            if (isOk && wArray[currentWIndex + count] >= positivePercent) {
                positive++
            }

            total++
            currentWIndex++
        }

        val percent = (positive.toDouble() / total.toDouble())

        return wArray
    }

}
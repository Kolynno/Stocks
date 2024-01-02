package nick.invest.stock.controllers

import nick.invest.stock.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.File


@Controller
@RequestMapping("/db")
class MainController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val databaseController: DatabaseController,
    private val stockDividendsRepository: StockDividendsRepository
) {

    @GetMapping(path = ["/update"])
    @ResponseBody
    fun updateStocks(): String {
        databaseController.updateAllStocks()
        return "Updated"
    }

    @GetMapping(path = ["/updateCSV"])
    @ResponseBody
    fun updateStocksCSV(): String {
        UpdateData.updateData(stockHistoryRepository)
        return "Updated"
    }

    @GetMapping(path = ["/stock"])
    @ResponseBody
    fun getAllStocksHistory(@RequestParam ticker: String): String {
        val data = stockHistoryRepository.getStockHistoryByTicker(ticker)
        val header = "id,ticker,date,open,high,low,close,vol\n"
        val strRet = header + data.toString().substring(1, data.toString().length - 1).replace(", ", "")
        return strRet
    }

    @GetMapping(path = ["/main"])
    fun main(): String {
        return "main"
    }

    @PostMapping("/getStocksNames")
    @ResponseBody
    fun getName(): Map<String, List<String>> {
        val response: MutableMap<String, List<String>> = HashMap()
        val tickers = stockHistoryRepository.getTickers()
        response["stocks"] = tickers
        return response
    }
    @GetMapping("/updateDiv")
    @ResponseBody
    fun temp(@RequestParam ticker: String): String {
        val file = File("src/main/resources/data/data.csv")
        val reader = BufferedReader(file.reader())
        val csvString = reader.use(BufferedReader::readText)
        var values: List<String>
        val result: MutableMap<Int, Double> = mutableMapOf()

        csvString.lines().forEachIndexed { index, line ->
            if (index == 0 || line.isBlank()) {
                return@forEachIndexed
            }
            values = line.replace("%", "").split("\t")
            val year = values[0].substring(6, 10).toInt()
            val percent = values[3].replace(",", ".").toDouble()
            if (result[year] == null) {
                result[year] = percent
            } else {
                result[year] = result[year]!! + percent
            }
        }


        val readyResult = result.mapValues { "%.2f".format(it.value) }

        for (div in readyResult) {

            val stockDiv = StockDividends(
                ticker = ticker,
                year = div.key,
                percent = div.value.replace(",",".").toDouble()
            )
            stockDividendsRepository.save(stockDiv)
        }
        return "Done"
    }
}
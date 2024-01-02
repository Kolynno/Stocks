package nick.invest.stock.controllers

import nick.invest.stock.database.DatabaseController
import nick.invest.stock.database.StockHistoryRepository
import nick.invest.stock.database.UpdateData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/db")
class MainController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val databaseController: DatabaseController
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

}
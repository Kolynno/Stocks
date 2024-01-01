package nick.invest.stock.controllers

import nick.invest.stock.database.DatabaseController
import nick.invest.stock.database.StockHistory
import nick.invest.stock.database.StockHistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


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

    @GetMapping(path = ["/stock"])
    @ResponseBody
    fun getAllStocksHistory(@RequestParam ticker: String): Iterable<StockHistory> {
        return stockHistoryRepository.getStockHistoryByTicker(ticker)
    }
    @GetMapping(path = ["/getTestData"])
    @ResponseBody
    fun getAllStocksHistory(): String {
        val data = stockHistoryRepository.getStockHistoryByTicker("SBER")
        val header = "id,ticker,date,open,high,low,close,vol\n"
        val strRet = header + data.toString().substring(1, data.toString().length - 1).replace(", ", "")
        print(strRet)
        return strRet
    }



    @GetMapping(path = ["/main"])
    fun main(): String {
        return "main"
    }
}
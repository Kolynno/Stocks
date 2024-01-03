package nick.invest.stock.controllers

import nick.invest.stock.database.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/table")
class TableController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val databaseController: DatabaseController
) {

    @GetMapping("/main")
    fun main(model: Model): String {
        val tableData = getTableData()
        model.addAttribute("tableData", tableData)
        return "table"
    }


    @GetMapping("/data")
    @ResponseBody
    fun getTableData(): List<StockHistory> {
        return stockHistoryRepository.getTickerDateCloseForTable()
    }


}
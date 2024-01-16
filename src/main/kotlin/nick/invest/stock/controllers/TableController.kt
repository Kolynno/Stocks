package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.database.repositories.StockChangesRepository
import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.file.ExcelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/table")
class TableController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val stockChangesRepository: StockChangesRepository
) {

    @GetMapping("/main")
    fun main(model: Model, @RequestParam(required = false) createTable: Boolean): String {
        val tableData = getTableData(createTable)
        model.addAttribute("tableData", tableData)
        return "table"
    }


    @GetMapping("/data")
    @ResponseBody
    fun getTableData(createTable: Boolean): List<StockChange> {
        val rawData = stockChangesRepository.getDataForTable()
        val lines = rawData.map { line ->
            val parts = line.split(",")
            StockChange(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10])
        }

        if (createTable) {
            val excelRepository = ExcelRepository()
            excelRepository.createTable(lines)
        }

        return lines
    }
}

data class StockChange(
    val ticker: String,
    val close: String,
    val name: String,
    val date: String,
    val capitalization: String,
    val window: String,
    val count: String,
    val total: String,
    val percent: String,
    val change16d: String
)

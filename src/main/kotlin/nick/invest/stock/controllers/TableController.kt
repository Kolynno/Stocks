package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.database.repositories.StockChangesRepository
import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.database.tables.StockChanges
import nick.invest.stock.database.tables.StockHistory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/table")
class TableController @Autowired constructor(
    private val stockHistoryRepository: StockHistoryRepository,
    private val stockChangesRepository: StockChangesRepository
) {

    @GetMapping("/main")
    fun main(model: Model): String {
        val tableData = getTableData()
        model.addAttribute("tableData", tableData)
        return "table"
    }


    @GetMapping("/data")
    @ResponseBody
    fun getTableData(): List<StockChange> {
        val rawData = stockChangesRepository.getDataForTable()
        // Преобразование каждой строки в объект StockChange
        return rawData.map { line ->
            val parts = line.split(",") // Предположим, что данные разделены запятыми, замените на ваш разделитель
            StockChange(parts[1], parts[4], parts[5], parts[2], parts[3], parts[6])
        }
    }



}

data class StockChange(
    val ticker: String,
    val name: String,
    val date: String,
    val close: String,
    val change14d: String,
    val capitalization: String
)

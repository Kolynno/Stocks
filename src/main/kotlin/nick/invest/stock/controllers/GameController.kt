package nick.invest.stock.controllers

import nick.invest.stock.database.*
import nick.invest.stock.database.repositories.StockChangesRepository
import nick.invest.stock.database.repositories.StockDividendsRepository
import nick.invest.stock.database.repositories.StockHistoryRepository
import nick.invest.stock.database.repositories.StockInfoRepository
import nick.invest.stock.database.tables.StockChanges
import nick.invest.stock.database.tables.StockDividends
import nick.invest.stock.database.tables.StockInfo
import nick.invest.stock.strategy.StrategyOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.File


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
    fun main(@RequestParam ticker: String, @RequestParam w: Int, @RequestParam c: Int, @RequestParam percent: Double): String {

        val so = StrategyOne()


        return "game"
    }

}
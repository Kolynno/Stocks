package nick.invest.stock.database

import nick.invest.stock.database.repositories.StockHistoryRepository
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*


class UpdateData {
    companion object {
        fun updateData(stockHistoryRepository: StockHistoryRepository) {

            val tickers = stockHistoryRepository.getTickers()
            val lastDate = stockHistoryRepository.getLastDate()

            val currentDate = Date()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val todayDate = sdf.format(currentDate)

            //SET INDEXes
            setIMOEXindexToCSV(lastDate, todayDate)
            //SET STOCKS
            for(ticker in tickers) {
                setDataToCSV(ticker, lastDate, todayDate)
            }
        }

        private fun setIMOEXindexToCSV(lastDate: String, todayDate: String?) {
            val url = "https://iss.moex.com/iss/history/engines/stock/markets/index/securities/IMOEX.csv?from=$lastDate&till=$todayDate"

            val httpClient: HttpClient = HttpClients.createDefault()
            val httpGet = HttpGet(url)
            try {
                val response: HttpResponse = httpClient.execute(httpGet)
                val reader = BufferedReader(InputStreamReader(response.entity.content))
                val csvString = reader.use(BufferedReader::readText)
                val outputFile = File("src/main/resources/data/IMOEX_STOCK.csv")
                outputFile.bufferedWriter().use { writer ->
                    csvString.lines().forEachIndexed { index, line ->
                        if (index == 0 || line.isBlank()) {
                            return@forEachIndexed
                        }
                        val values = line.split(";")
                        if (values.size >= 15) {
                            val selectedValues = listOf(values[0], values[1], values[4], values[5], values[6], values[7], values[14])
                            writer.write(selectedValues.joinToString(";") + "\n")
                        }
                    }
                }
                println("Файл успешно создан в: ${outputFile.absolutePath}")
            } finally {}
        }

        private fun setDataToCSV(ticker: String, lastDate: String, todayDate: String?) {
            val url = "https://iss.moex.com/iss/history/engines/stock/totals/boards/MRKT/securities/$ticker.csv?from=$lastDate&till=$todayDate"

            val httpClient: HttpClient = HttpClients.createDefault()
            val httpGet = HttpGet(url)
            try {
                val response: HttpResponse = httpClient.execute(httpGet)
                val reader = BufferedReader(InputStreamReader(response.entity.content))
                val csvString = reader.use(BufferedReader::readText)
                val outputFile = File("src/main/resources/data/${ticker}_STOCK.csv")
                outputFile.bufferedWriter().use { writer ->
                    csvString.lines().forEachIndexed { index, line ->
                        if (index == 0 || line.isBlank()) {
                            return@forEachIndexed
                        }
                        val values = line.split(";")
                        if (values.size >= 15) {
                            val selectedValues = listOf(values[0], values[1], values[4], values[5], values[6], values[7], values[14])
                            writer.write(selectedValues.joinToString(";") + "\n")
                        }
                    }
                }
                println("Файл успешно создан в: ${outputFile.absolutePath}")
            } finally {}
        }
    }
}
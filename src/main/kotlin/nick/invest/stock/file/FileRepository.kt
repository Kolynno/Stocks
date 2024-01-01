package nick.invest.stock.file

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import nick.invest.stock.database.StockHistory
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FileRepository {

    companion object {

        private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

        fun readDataFromCSV(): MutableList<StockHistory> {

            val stockDataList: MutableList<StockHistory> = mutableListOf()

            var currentFile = setFile()
            while (currentFile != null) {
                val csvReader = csvReader {
                    delimiter = ';'
                }
                csvReader.open(currentFile) {
                    readAllAsSequence().forEach { row ->
                        if (row[0] != "<TICKER>") {
                            val stockHistory = StockHistory(
                                ticker = row[0],
                                date = LocalDate.parse(row[2], dateFormatter),
                                open = row[4].toDouble(),
                                high = row[5].toDouble(),
                                low = row[6].toDouble(),
                                close = row[7].toDouble(),
                                vol = row[8].toDouble()
                            )
                            stockDataList.add(stockHistory)
                        }
                    }
                }
                currentFile = setFile()
            }
            return stockDataList
        }

        private fun setFile(): File? {
            val folderPath = "src/main/resources/data"
            val regexPattern = Regex("\\D*_FROM_2000")

            val currentFile = findFileInTheFolder(folderPath, regexPattern)
            if (currentFile != null) {
                val updatedFile = updateFileWithDate(currentFile)
                println("File successful upload and rename to $updatedFile")
                return updatedFile
            } else {
                println("File didn't find")
            }
            return null
        }

        private fun updateFileWithDate(currentFile: File): File? {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val fileNameWithoutExtension = currentFile.nameWithoutExtension
            val fileExtension = currentFile.extension

            val newFileName = fileNameWithoutExtension + "_" + currentDate + "." + fileExtension
            val renamedFile = File(currentFile.parent, newFileName)

            currentFile.renameTo(renamedFile)

            return renamedFile
        }

        private fun findFileInTheFolder(folderPath: String, regexPattern: Regex): File? {
            val folder = File(folderPath)
            val files = folder.listFiles()



            files?.forEach { file ->
                println(file.name)
                if (file.isFile && regexPattern.matches(file.nameWithoutExtension)) {
                    return file
                }
            }

            return null
        }


    }


}
package nick.invest.handle
import java.io.File
import java.lang.Math.abs

fun main() {

    val fileNames = GetData.getFileNames()

    val totalData = mutableListOf<Triple<String, String, Double>>()

    for (file in fileNames) {

        val ticker = file.nameWithoutExtension
        val filePath = "src/main/resources/data10min/$ticker.csv"
        val dataList = mutableListOf<Pair<String, Double>>()

        File(filePath).forEachLine { line ->
            val tokens = line.split(";")
            if (tokens.size == 5) {
                val time = tokens[3].trim()
                val close = tokens[4].toDoubleOrNull()
                if (close != null) {
                    dataList.add(time to close)
                }
            }
        }

        val averageDays = getAVGDays(ticker)

        var counter = 0
        var daysBetweenList = mutableListOf<Int>()

        for (d in 1 until dataList.size) {

            val closeCurrent = dataList[d].second
            val closeBefore = dataList[d-1].second
            val timeCurrent = dataList[d].first
            val percent = (closeCurrent/closeBefore - 1) * 100
            if (timeCurrent == "10:00:00") {
                counter++
          if (ticker == "GTRK") {
                println(ticker +";"+ String.format("%.2f", percent))
            }
                if (percent >= 10) {
                    daysBetweenList.add(kotlin.math.abs(counter - averageDays))
                    counter = 0
                }
            }
        }
        if (counter != 0) {
            daysBetweenList.add(kotlin.math.abs(counter - averageDays))
        }
        totalData.add(Triple(ticker, "10" ,daysBetweenList.average()))
    }


        var outfile = File("E:\\Dowload\\Stocks2\\output.csv")
        for (triple in totalData) {
            outfile.appendText("${triple.first};${String.format("%.2f",triple.third)};${triple.second}\n")
        }

}

fun getAVGDays(ticker: String): Int {
    val filePath = "src/main/kotlin/nick/invest/handle/data/percent10.csv"
    var days = 0
    File(filePath).forEachLine { line ->
        val tokens = line.split(";")
        if (tokens.size == 3) {
            if (tokens[0] == ticker) {
                days = tokens[1].replace(",",".").toDouble().toInt()
            }
        }
    }
    return days
}


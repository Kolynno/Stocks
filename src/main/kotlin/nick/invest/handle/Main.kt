package nick.invest.handle
import java.io.File

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

        val timeData = mutableMapOf<String, Double>()

        for (d in 1 until dataList.size) {
            val closeCurrent = dataList[d].second
            val closeBefore = dataList[d-1].second
            val timeCurrent = dataList[d].first
            val percent = (closeCurrent/closeBefore - 1) * 100
            if (timeData[timeCurrent] == null) {
                timeData[timeCurrent] = percent
            } else {
                var old = timeData[timeCurrent]!!
                var new = old + percent
                timeData[timeCurrent] = new
            }
        }

        timeData.toSortedMap()

        for (data in timeData) {
            totalData.add(Triple(ticker, data.key.substring(0,5), data.value))
        }

    }

        var outfile = File("E:\\Dowload\\Stocks2\\output.csv")
        for (triple in totalData) {
            outfile.appendText("${triple.first};${triple.second};${triple.third}\n")
        }

}


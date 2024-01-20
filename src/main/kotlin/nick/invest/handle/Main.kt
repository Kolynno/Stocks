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

        var counter = 0
        var lastPercent = 999.9
        var days = mutableMapOf<Int, Int>()

        for (d in 1 until dataList.size) {

            val closeCurrent = dataList[d].second
            val closeBefore = dataList[d-1].second
            val timeCurrent = dataList[d].first
            val percent = (closeCurrent/closeBefore - 1) * 100
            if (lastPercent == 999.9) {
                lastPercent = percent
            } else {
                if (timeCurrent == "10:00:00") {
                    if (percent > 0 && lastPercent > 0) {
                        counter++
                    } else if (percent <= 0 && lastPercent <= 0) {
                        counter++
                    } else if (percent > 0 && lastPercent <= 0) {
                        if (days[counter] != null) {
                            val num = days[counter]!! + 1
                            days[counter] = num
                        } else {
                            days[counter] = 1
                        }
                        counter = 0
                    } else if (percent <= 0 && lastPercent > 0) {
                        if (days[counter] != null) {
                            val num = days[counter]!! + 1
                            days[counter] = num
                        } else {
                            days[counter] = 1
                        }
                        counter = 0
                    }
                }
            }
        }


        var totalDays = 0
        for(d in days.toSortedMap()) {
           totalDays += d.value
        }


        print("$ticker;")
        for(d in days.toSortedMap()) {
            if (((d.value.toDouble()/totalDays.toDouble())*100).toInt() != 0) {
                print("${((d.value.toDouble() / totalDays.toDouble()) * 100).toInt()};")
            }
        }
        print("0;\n")

        //var outfile = File("E:\\Dowload\\Stocks2\\output.csv")
        //outfile.appendText("$ticker;${percents.toSortedMap()}\n")
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


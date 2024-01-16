package nick.invest.handle
import java.io.File

fun main() {
    val filePath = "src/main/resources/data/SFIN_3.csv"
    val dataList = mutableListOf<Pair<String, Double>>()

    File(filePath).forEachLine { line ->
        val tokens = line.split(";")
        if (tokens.size == 5) {
            val time = tokens[3].trim() // Добавьте .trim() для удаления возможных пробелов
            val close = tokens[4].toDoubleOrNull()

            if (close != null && time == "10:00:00") {
                dataList.add(time to close)
            }
        }
    }

    val percentageChanges = mutableListOf<Pair<String, Double>>()

    for (i in 1 until dataList.size) {
        val (currentTime, currentGrowth) = dataList[i]
        val (previousTime, previousGrowth) = dataList[i - 1]

        val percentageChange = ((currentGrowth / previousGrowth) - 1) * 100.0
        percentageChanges.add(currentTime to percentageChange)
    }

    var isFirst = true
    var lastClose = 0.0
    var curMaxOneWay = 0
    var mutableMap = mutableMapOf<Int, Double>()
    var total = 0.0

    percentageChanges.forEach {(time, close) ->
        var closeCorrect = close

//        if (closeCorrect < -1) {
//            closeCorrect = -1.0
//        }
//        if (closeCorrect > 5) {
//            closeCorrect = 5.0
//        }

        if (isFirst) {
            isFirst = false
            lastClose = close
        } else {
            if (close > 0 && lastClose <= 0) {
                if (mutableMap[curMaxOneWay] != null) {
                    var num = mutableMap[curMaxOneWay]!!
                    num++
                    mutableMap[curMaxOneWay] = num + 1
                } else {
                    mutableMap[curMaxOneWay] = 1.0
                }
                curMaxOneWay = 0
            } else if (close <= 0 && lastClose > 0) {
                if (mutableMap[curMaxOneWay] != null) {
                    var num = mutableMap[curMaxOneWay]!!
                    num++
                    mutableMap[curMaxOneWay] = num + 1
                } else {
                    mutableMap[curMaxOneWay] = 1.0
                }
                curMaxOneWay = 0
            } else if (close > 0 && lastClose > 0) {
                curMaxOneWay++
            } else if (close <= 0 && lastClose <= 0) {
                curMaxOneWay++
            }
            total++
            lastClose = close
        }

        println(closeCorrect.toString().replace(".",","))

    }
    println(mutableMap.toSortedMap().toString())

    for(map in mutableMap.toSortedMap()) {
        println("${map.key}:${String.format("%.2f", map.value/total)}%")
    }

    //println(pp/total)

   // var groupedData = mutableMapOf<String, Double>()

   //for ((time, growth) in percentageChanges) {
   //     groupedData.merge(time, growth) { oldValue, newValue -> oldValue + newValue }
   // }

     //Time
   // groupedData = groupedData.toSortedMap()

    // Value
    //groupedData = groupedData.entries.sortedByDescending { it.value }.associateTo(mutableMapOf()) { it.key to it.value }


     //Вывести результат
   // groupedData.forEach { (time, totalGrowth) ->
  //      println("${time.substring(0,5)};${String.format("%.2f", totalGrowth)}")
  // }



}


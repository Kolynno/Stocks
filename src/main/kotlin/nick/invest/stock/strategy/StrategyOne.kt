package nick.invest.stock.strategy

import nick.invest.stock.database.repositories.StockHistoryRepository

class StrategyOne {
    fun calculate(close: List<Double>, window: Int, count: Int, percentPerDay: Double): String {
        val positivePercent = percentPerDay * window

        val wArraySize = close.size / window
        var currentClose = 0
        var currentWArrayIndex = 0

        var wArray = DoubleArray(wArraySize)
        while (currentClose < close.size - window) {

            val wEnd = close[currentClose + window]
            val wStart = close[currentClose]

            // Save percent
            wArray[currentWArrayIndex] = (wEnd / wStart - 1) * 100

            currentClose += window
            currentWArrayIndex++
        }

        return calculateResult(wArray, count, positivePercent, window)
    }

    private fun calculateResult(wArray: DoubleArray, count: Int, positivePercent: Double, window: Int): String {
        var total = 0
        var positive = 0

        var currentWIndex = 0
        while (currentWIndex < wArray.size - count) {

            var daySum = 0.0
            var isOk = true
            for (i in 0 until count) {
                daySum += wArray[currentWIndex + i]
            }

            if (daySum < positivePercent) {
                isOk = false
            }
            if (isOk && wArray[currentWIndex + count] >= positivePercent) {
                positive++
            }

            total++
            currentWIndex++
        }

        val percent = (positive.toDouble() / total.toDouble())

        return "${String.format("%.3f", percent)}\t$window\t$count\t$total"
    }

    fun getWindowAndCount(ticker: String, stockHistoryRepository: StockHistoryRepository): List<Pair<Int, Int>> {

        val pairList :MutableList<Pair<Int, Int>> = mutableListOf()

        val closeList = stockHistoryRepository.getCloseAndDateByTickerFromDate(ticker, "2022-06-01")
        val positivePercent = 0.2


        for (w in 1..20) {
            val wArray = getWArray(closeList, w)
            for (c in 1..20) {
                var isOk = true
                var wArrayIsOk = true
                var daySum: MutableList<Double> = mutableListOf()
                for (i in 0 until c) {
                    if ((wArray.size - 1 - i) < 0) {
                            wArrayIsOk = false
                            break
                        }
                    daySum.add(wArray[wArray.size - 1 - i])
                }

                val minDaySum = daySum.min()

                if (minDaySum < positivePercent) {
                    isOk = false
                }

                if (isOk && wArrayIsOk) {
                    pairList.add(Pair(w,c))
                }

            }
        }
        return pairList
    }

    private fun getWArray(closeList: List<Double>, w: Int): DoubleArray {
        val wArraySize = closeList.size / w
        var currentClose = 0
        var currentWArrayIndex = 0

        var wArray = DoubleArray(wArraySize)
        while (currentClose < closeList.size - w) {

            val wEnd = closeList[currentClose + w]
            val wStart = closeList[currentClose]

            // Save percent
            wArray[currentWArrayIndex] = (wEnd / wStart - 1) * 100

            currentClose += w
            currentWArrayIndex++
        }
        return wArray
    }
}
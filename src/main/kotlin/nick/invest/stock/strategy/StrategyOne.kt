package nick.invest.stock.strategy

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
}
package nick.invest.stock.strategy

class StrategyOne {
    fun calculate(close: List<Double>, window: Int, count: Int) {

        val positivePercent = 0.2 * window

        val wArraySize = close.size/window
        var currentClose = 0
        var currentWArrayIndex = 0

        var wArray = DoubleArray(wArraySize)
        while(currentClose < close.size - window) {

            val wEnd = close[currentClose + window]
            val wStart = close[currentClose]

            //Save percent
            wArray[currentWArrayIndex] = (wEnd / wStart - 1) * 100

            currentClose += window
            currentWArrayIndex++
        }
        calculateResult(wArray, count, positivePercent, window)
    }

    private fun calculateResult(wArray: DoubleArray, count: Int, positivePercent: Double, window: Int) {

//        for(w in wArray) {
//            println(w)
//        }

        var total = 0
        var positive = 0

        var currentWIndex = 0
        while(currentWIndex < wArray.size - count - 1) {

            var isOk = true
            for(i in 1..count) {
                if(wArray[currentWIndex + count] < positivePercent) {
                    isOk = false
                }
            }

            if (isOk && wArray[currentWIndex + count + 1] >= positivePercent) {
                positive++
            }

            total++
            currentWIndex++
        }

        val percent = (positive.toDouble()/total.toDouble())

        println("$percent\t$window\t$count")

    }
}
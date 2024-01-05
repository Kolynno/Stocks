package nick.invest.stock

import nick.invest.stock.strategy.StrategyOne
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StockApplicationTests {

    @Test
    fun contextLoads() {
        val so = StrategyOne()
        val list = listOf(10.0,20.0,25.0,20.0,19.0,20.0,21.0,25.0,15.0,13.0)
        println("Percent\tW\tC")

        for (w in 1 .. 10) {
            for (c in 2 .. 2) {
                println(so.calculate(list, w, c))
            }
        }

    }

}

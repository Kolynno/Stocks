package nick.invest.stock.database

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class StockHistory(
    ticker: String,
    date: LocalDate,
    open: Double,
    high: Double,
    low: Double,
    close: Double,
    vol: Double
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var ticker: String? = ticker
    var date: LocalDate? = date
    var open: Double? = open
    var high: Double? = high
    var low: Double? = low
    var close: Double? = close
    var vol: Double? = vol
    var close_14_days_ago: Double? = 0.0

    override fun toString(): String {
        return "$date,$open,$high,$low,$close,$vol\n"
    }
}

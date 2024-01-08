package nick.invest.stock.database.tables

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class StockNow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 1
    var ticker: String? = null
    var window: Int? = null
    var count: Int? = null
}

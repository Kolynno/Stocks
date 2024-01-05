package nick.invest.stock.database.tables

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class StockDividends(ticker: String, year: Int, percent: Double) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var ticker: String? = ticker
    var year: Int? = year
    var percent: Double? = percent
}


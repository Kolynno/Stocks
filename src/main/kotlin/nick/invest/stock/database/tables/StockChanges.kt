package nick.invest.stock.database.tables

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class StockChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    var ticker: String? = null
    var change14d: Double? = null
    override fun toString(): String {
        return "StockChanges(id=$id, ticker=$ticker, change14d=$change14d)"
    }


}


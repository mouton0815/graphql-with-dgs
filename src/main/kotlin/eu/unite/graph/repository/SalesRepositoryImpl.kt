package eu.unite.graph.repository

import org.springframework.stereotype.Component

@Component
class SalesRepositoryImpl: SalesRepository {
    private val sales = mutableMapOf<String, Int>()

    override fun incrSales(bookId: String): Int {
        return sales.merge(bookId, 1, Int::plus)!!
    }
}
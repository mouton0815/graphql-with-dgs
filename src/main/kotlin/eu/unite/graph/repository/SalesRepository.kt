package eu.unite.graph.repository

interface SalesRepository {
    fun incrSales(bookId: String): Int
}
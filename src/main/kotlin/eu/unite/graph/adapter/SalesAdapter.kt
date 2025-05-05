package eu.unite.graph.adapter

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsSubscription
import eu.unite.graph.codegen.types.Book
import eu.unite.graph.codegen.types.Sale
import eu.unite.graph.repository.BookRecord
import eu.unite.graph.repository.BookRepository
import eu.unite.graph.repository.SalesRepository
import kotlinx.coroutines.delay
import org.reactivestreams.Publisher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asPublisher
import kotlin.random.Random

@DgsComponent
class SalesAdapter(val bookRepo: BookRepository, val salesRepo: SalesRepository) {
    @DgsSubscription
    fun sales(): Publisher<Sale> {
        return flow {
            while (true) {
                val books = bookRepo.getBooks()
                val book = books[Random.nextInt(books.size)]
                val sales = salesRepo.incrSales(book.id)
                emit(Sale(recordToBook(book), sales))
                delay(Random.nextLong(500, 3000))
            }
        }.asPublisher()
    }

    private fun recordToBook(record: BookRecord): Book {
        return Book(record.id, record.title, record.year)
    }
}

package eu.unite.graph.adapter

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book
import eu.unite.graph.repository.BookRecord
import eu.unite.graph.repository.BookRepository

@DgsComponent
class BookAdapter(val repository: BookRepository)  {
    @DgsMutation
    fun createBook(title: String, year: Int, authorId: String): Book {
        return recordToBook(repository.createBook( title, year, authorId))
    }

    @DgsQuery(field = "books")
    fun allBooks(): List<Book> {
        return repository.getBooks().map(::recordToBook)
    }

    @DgsQuery(field = "book")
    fun selectedBook(id: String): Book? {
        val record = repository.getBook(id) ?: return null
        return recordToBook(record)
    }

    @DgsData(parentType = "Author", field = "books")
    fun booksOfAuthor(dfe: DgsDataFetchingEnvironment): List<Book> {
        val author = dfe.getSource<Author>() ?: return emptyList()
        return repository.getBooksOfAuthor(author.id).map(::recordToBook)
    }

    private fun recordToBook(record: BookRecord): Book {
        return Book(record.id, record.title, record.year)
    }
}
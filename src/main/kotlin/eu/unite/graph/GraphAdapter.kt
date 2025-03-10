package eu.unite.graph

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book
import eu.unite.graph.repository.AuthorRecord
import eu.unite.graph.repository.BookRecord
import eu.unite.graph.repository.Repository

@DgsComponent
class GraphAdapter(val repository: Repository) {

    @DgsMutation
    fun createAuthor(name: String, birth: String?, city: String?): Author {
        return recordToAuthor(repository.createAuthor(name, birth, city))
    }

    @DgsQuery(field = "authors")
    fun allAuthors(): List<Author> {
        return repository.getAuthors().map(::recordToAuthor)
    }

    @DgsQuery(field = "author")
    fun selectAuthor(id: String): Author? {
        val record = repository.getAuthor(id) ?: return null
        return recordToAuthor(record)
    }

    @DgsData(parentType = "Book", field = "author")
    fun authorOfBook(dfe: DgsDataFetchingEnvironment): Author? {
        val book = dfe.getSource<Book>() ?: return null
        val record = repository.getAuthorOfBook(book.id) ?: return null
        return recordToAuthor(record)
    }

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

    private fun recordToAuthor(record: AuthorRecord): Author {
        return Author(record.id, record.name, record.birth)
    }

    private fun recordToBook(record: BookRecord): Book {
        return Book(record.id, record.title, record.year)
    }
}
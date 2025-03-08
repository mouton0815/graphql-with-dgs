package eu.unite.graph

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book

@DgsComponent
class GraphAdapter {

    private data class BookAuthor(val bookId: String, val authorId: String)

    private val authorList = mutableListOf(
        Author("1", "Kate Chopin", "1850-02-08", "St. Louis"),
        Author("2", "Paul Auster","1947-02-03","New York"),
        Author("3", "Jennifer Egan", "1962-09-07", "New York"),
        Author("4", "T.C. Boyle", "1948-12-02", "Montecito CA")
    )

    private val bookList = listOf(
        Book("1", "The Awakening", 1899),
        Book("2", "City of Glass", 1985),
        Book("3", "Moon Palace", 1989),
        Book("4", "The Book of Illusions", 2002),
        Book("5", "Oracle Night", 2003),
        Book("6", "Sunset Park", 2010),
        Book("7", "A Visit from the Goon Squad", 2010),
        Book("8", "Manhattan Beach", 2017),
        Book("9", "Water Music", 1981),
        Book("10", "Drop City", 2003),
        Book("11", "Talk Talk", 2006)
    )

    private val bookAuthors = listOf(
        BookAuthor("1", "1"),
        BookAuthor("2", "2"),
        BookAuthor("3", "2"),
        BookAuthor("4", "2"),
        BookAuthor("5", "2"),
        BookAuthor("6", "2"),
        BookAuthor("7", "3"),
        BookAuthor("8", "3"),
        BookAuthor("9", "4"),
        BookAuthor("10", "4"),
        BookAuthor("11", "4")

    )

    @DgsMutation
    fun createAuthor(name: String, birth: String?, city: String?): Author {
        val id = (authorList.size + 1).toString()
        authorList.addLast(Author(id, name, birth, city))
        return Author(id, name, birth, city)
    }

    @DgsQuery(field = "authors")
    fun allAuthors(): List<Author> {
        return authorList
    }

    @DgsQuery(field = "author")
    fun selectAuthor(id: String): Author? {
        return authorList.find { author -> author.id == id }
    }

    @DgsData(parentType = "Author", field = "books")
    fun authorBooks(dfe: DgsDataFetchingEnvironment): List<Book> {
        val author = dfe.getSource<Author>() ?: return emptyList()
        return bookAuthors
            .filter { ba -> ba.authorId == author.id }
            .mapNotNull { ba -> bookList.find { b -> b.id == ba.bookId }}
    }

    @DgsQuery(field = "books")
    fun allBooks(): List<Book> {
        return bookList
    }

    @DgsQuery(field = "book")
    fun selectedBook(id: String): Book? {
        return bookList.find { book -> book.id == id }
    }

    @DgsData(parentType = "Book", field = "author")
    fun bookAuthor(dfe: DgsDataFetchingEnvironment): Author? {
        val book = dfe.getSource<Book>() ?: return null
        val bookAuthor = bookAuthors.find { ba -> ba.bookId == book.id } ?: return null
        return authorList.find { a -> a.id == bookAuthor.authorId }
    }
}
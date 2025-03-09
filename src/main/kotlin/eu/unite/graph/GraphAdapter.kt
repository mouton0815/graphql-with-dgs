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

    private data class AuthorRecord(val id: String, val name: String, val birth: String?, val city: String?) {
        fun toAuthor(): Author {
            return Author(this.id, this.name, this.birth, this.city)
        }
    }

    private data class BookRecord(val id: String, val title: String, val year: Int, val authorId: String) {
        fun toBook(): Book {
            return Book(this.id, this.title, this.year)
        }
    }

    private val authorList = mutableListOf(
        AuthorRecord("1", "Kate Chopin", "1850-02-08", "St. Louis"),
        AuthorRecord("2", "Paul Auster","1947-02-03","New York"),
        AuthorRecord("3", "Jennifer Egan", "1962-09-07", "New York"),
        AuthorRecord("4", "T.C. Boyle", "1948-12-02", "Montecito CA")
    )

    private val bookList = listOf(
        BookRecord("1", "The Awakening", 1899, "1"),
        BookRecord("2", "City of Glass", 1985, "2"),
        BookRecord("3", "Moon Palace", 1989, "2"),
        BookRecord("4", "The Book of Illusions", 2002, "2"),
        BookRecord("5", "Oracle Night", 2003, "2"),
        BookRecord("6", "Sunset Park", 2010, "2"),
        BookRecord("7", "A Visit from the Goon Squad", 2010, "3"),
        BookRecord("8", "Manhattan Beach", 2017, "3"),
        BookRecord("9", "Water Music", 1981, "4"),
        BookRecord("10", "Drop City", 2003, "4"),
        BookRecord("11", "Talk Talk", 2006, "4")
    )

    @DgsMutation
    fun createAuthor(name: String, birth: String?, city: String?): Author {
        val id = (authorList.size + 1).toString()
        authorList.addLast(AuthorRecord(id, name, birth, city))
        return Author(id, name, birth, city) // No associated books yet
    }

    @DgsQuery(field = "authors")
    fun allAuthors(): List<Author> {
        return authorList.map { a -> a.toAuthor() }
    }

    @DgsQuery(field = "author")
    fun selectAuthor(id: String): Author? {
        return authorList.find { author -> author.id == id }?.toAuthor()
    }

    @DgsData(parentType = "Author", field = "books")
    fun authorBooks(dfe: DgsDataFetchingEnvironment): List<Book> {
        val author = dfe.getSource<Author>() ?: return emptyList()
        return bookList.filter { b -> b.authorId == author.id }.map { b -> b.toBook() }
    }

    @DgsQuery(field = "books")
    fun allBooks(): List<Book> {
        return bookList.map { b -> b.toBook() }
    }

    @DgsQuery(field = "book")
    fun selectedBook(id: String): Book? {
        return bookList.find { book -> book.id == id }?.toBook()
    }

    @DgsData(parentType = "Book", field = "author")
    fun bookAuthor(dfe: DgsDataFetchingEnvironment): Author? {
        val book = dfe.getSource<Book>() ?: return null
        val record = bookList.find { b -> b.id == book.id } ?: return null
        return authorList.find { a -> a.id == record.authorId }?.toAuthor()
    }
}
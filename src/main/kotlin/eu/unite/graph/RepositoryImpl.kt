package eu.unite.graph

import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book
import org.springframework.stereotype.Component

@Component
class RepositoryImpl: Repository {

    private data class AuthorRecord(val id: String, val name: String, val birth: String?, val city: String?) {
        fun toAuthor(): Author {
            return Author(this.id, this.name, this.birth, this.city)
        }
    }

    private val authorList = mutableListOf(
        AuthorRecord("1", "Kate Chopin", "1850-02-08", "St. Louis"),
        AuthorRecord("2", "Paul Auster","1947-02-03","New York"),
        AuthorRecord("3", "Jennifer Egan", "1962-09-07", "New York"),
        AuthorRecord("4", "T.C. Boyle", "1948-12-02", "Montecito CA")
    )

    override fun createAuthor(name: String, birth: String?, city: String?): Author {
        val id = (authorList.size + 1).toString()
        val record = AuthorRecord(id, name, birth, city)
        authorList.addLast(record)
        return record.toAuthor()
    }

    override fun getAuthors(): List<Author> {
        return authorList.map { a -> a.toAuthor() }
    }

    override fun getAuthor(id: String): Author? {
        return authorList.find { author -> author.id == id }?.toAuthor()
    }

    override fun getAuthorOfBook(book: Book): Author? {
        val record = bookList.find { b -> b.id == book.id } ?: return null
        return authorList.find { a -> a.id == record.authorId }?.toAuthor()
    }

    private data class BookRecord(val id: String, val title: String, val year: Int, val authorId: String?) {
        fun toBook(): Book {
            return Book(this.id, this.title, this.year)
        }
    }

    private val bookList = mutableListOf(
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

    override fun createBook(title: String, year: Int, authorId: String): Book {
        val id = (bookList.size + 1).toString()
        val record = BookRecord(id, title, year, authorId)
        bookList.addLast(record)
        return record.toBook()
    }

    override fun getBooks(): List<Book> {
        return bookList.map { b -> b.toBook() }
    }

    override fun getBook(id: String): Book? {
        return bookList.find { book -> book.id == id }?.toBook()
    }

    override fun getBooksOfAuthor(author: Author): List<Book> {
        return bookList.filter { b -> b.authorId == author.id }.map { b -> b.toBook() }
    }
}
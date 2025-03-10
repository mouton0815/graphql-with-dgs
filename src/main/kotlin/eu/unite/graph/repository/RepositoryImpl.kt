package eu.unite.graph.repository

import org.springframework.stereotype.Component

@Component
class RepositoryImpl: Repository {

    private val authorList = mutableListOf(
        AuthorRecord("1", "Kate Chopin", "1850-02-08", "St. Louis"),
        AuthorRecord("2", "Paul Auster","1947-02-03","New York"),
        AuthorRecord("3", "Jennifer Egan", "1962-09-07", "New York"),
        AuthorRecord("4", "T.C. Boyle", "1948-12-02", "Montecito CA")
    )

    override fun createAuthor(name: String, birth: String?, city: String?): AuthorRecord {
        val id = (authorList.size + 1).toString()
        val record = AuthorRecord(id, name, birth, city)
        authorList.addLast(record)
        return record
    }

    override fun getAuthors(): List<AuthorRecord> {
        return authorList
    }

    override fun getAuthor(id: String): AuthorRecord? {
        return authorList.find { author -> author.id == id }
    }

    override fun getAuthorOfBook(bookId: String): AuthorRecord? {
        val record = bookList.find { b -> b.id == bookId } ?: return null
        return authorList.find { a -> a.id == record.authorId }
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

    override fun createBook(title: String, year: Int, authorId: String): BookRecord {
        val id = (bookList.size + 1).toString()
        val record = BookRecord(id, title, year, authorId)
        bookList.addLast(record)
        return record
    }

    override fun getBooks(): List<BookRecord> {
        return bookList
    }

    override fun getBook(id: String): BookRecord? {
        return bookList.find { book -> book.id == id }
    }

    override fun getBooksOfAuthor(authorId: String): List<BookRecord> {
        return bookList.filter { b -> b.authorId == authorId }
    }
}
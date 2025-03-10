package eu.unite.graph.repository

import org.springframework.stereotype.Component

@Component
class BookRepositoryImpl: BookRepository {

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
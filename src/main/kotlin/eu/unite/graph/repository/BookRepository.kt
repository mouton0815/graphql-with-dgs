package eu.unite.graph.repository

interface BookRepository {
    fun createBook(title: String, year: Int, authorId: String): BookRecord
    fun getBooks(): List<BookRecord>
    fun getBook(id: String): BookRecord?
    fun getBooksOfAuthor(authorId: String): List<BookRecord>
}
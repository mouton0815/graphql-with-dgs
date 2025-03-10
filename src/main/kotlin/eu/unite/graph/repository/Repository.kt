package eu.unite.graph.repository

interface Repository {
    fun createAuthor(name: String, birth: String?, city: String?): AuthorRecord
    fun getAuthors(): List<AuthorRecord>
    fun getAuthor(id: String): AuthorRecord?
    fun getAuthorOfBook(bookId: String): AuthorRecord?

    fun createBook(title: String, year: Int, authorId: String): BookRecord
    fun getBooks(): List<BookRecord>
    fun getBook(id: String): BookRecord?
    fun getBooksOfAuthor(authorId: String): List<BookRecord>
}
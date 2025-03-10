package eu.unite.graph.repository

interface AuthorRepository {
    fun createAuthor(name: String, birth: String?, city: String?): AuthorRecord
    fun getAuthors(): List<AuthorRecord>
    fun getAuthor(id: String): AuthorRecord?
    fun getAuthorOfBook(bookId: String): AuthorRecord?
}
package eu.unite.graph.repository

import org.springframework.stereotype.Component

@Component
class AuthorRepositoryImpl(val bookRepository: BookRepository): AuthorRepository {

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
        val record = bookRepository.getBook(bookId) ?: return null
        return authorList.find { a -> a.id == record.authorId }
    }
}
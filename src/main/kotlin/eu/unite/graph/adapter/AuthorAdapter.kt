package eu.unite.graph.adapter

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book
import eu.unite.graph.repository.AuthorRecord
import eu.unite.graph.repository.AuthorRepository

@DgsComponent
class AuthorAdapter(val repository: AuthorRepository) {
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

    private fun recordToAuthor(record: AuthorRecord): Author {
        return Author(record.id, record.name, record.birth)
    }
}
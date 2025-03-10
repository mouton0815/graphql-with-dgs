package eu.unite.graph

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book

@DgsComponent
class GraphAdapter(val repository: Repository) {

    @DgsMutation
    fun createAuthor(name: String, birth: String?, city: String?): Author {
        return repository.createAuthor(name, birth, city)
    }

    @DgsQuery(field = "authors")
    fun allAuthors(): List<Author> {
        return repository.getAuthors()
    }

    @DgsQuery(field = "author")
    fun selectAuthor(id: String): Author? {
        return repository.getAuthor(id)
    }

    @DgsData(parentType = "Author", field = "books")
    fun authorBooks(dfe: DgsDataFetchingEnvironment): List<Book> {
        val author = dfe.getSource<Author>() ?: return emptyList()
        return repository.getBooksOfAuthor(author)
    }

    @DgsMutation
    fun createBook(title: String, year: Int, authorId: String): Book {
        return repository.createBook( title, year, authorId)
    }

    @DgsQuery(field = "books")
    fun allBooks(): List<Book> {
        return repository.getBooks()
    }

    @DgsQuery(field = "book")
    fun selectedBook(id: String): Book? {
        return repository.getBook(id)
    }

    @DgsData(parentType = "Book", field = "author")
    fun bookAuthor(dfe: DgsDataFetchingEnvironment): Author? {
        val book = dfe.getSource<Book>() ?: return null
        return repository.getAuthorOfBook(book)
    }
}
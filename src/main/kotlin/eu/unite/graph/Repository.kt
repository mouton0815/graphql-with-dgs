package eu.unite.graph

import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.Book

interface Repository {
    fun createAuthor(name: String, birth: String?, city: String?): Author
    fun getAuthors(): List<Author>
    fun getAuthor(id: String): Author?
    fun getAuthorOfBook(book: Book): Author?

    fun createBook(title: String, year: Int, authorId: String): Book
    fun getBooks(): List<Book>
    fun getBook(id: String): Book?
    fun getBooksOfAuthor(author: Author): List<Book>
}
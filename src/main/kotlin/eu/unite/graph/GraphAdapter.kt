package eu.unite.graph;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery
import eu.unite.graph.codegen.types.Author
import eu.unite.graph.codegen.types.AuthorInput
import eu.unite.graph.codegen.types.Book

@DgsComponent
public class GraphAdapter {

    private data class BookRecord(val id: String, val title: String, val year: Int, val authorId: String) {
        fun toBook(authors: List<Author>): Book {
            return Book(this.id, this.title, this.year, authors.find { a -> a.id == this.authorId })
        }
    }

    private val authorList = mutableListOf(
        Author("1", "Kate Chopin", "1850-02-08", "St. Louis"),
        Author("2", "Paul Auster","1947-02-03","New York"),
        Author("3", "Jennifer Egan", "1962-09-07", "New York"),
        Author("4", "T.C. Boyle", "1948-12-02", "Montecito CA")
    )

    private val bookList = listOf(
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

    @DgsMutation
    fun createAuthor(input: AuthorInput): Author {
        val id = (authorList.size + 1).toString()
        val author = Author(id, input.name, input.birth, input.city)
        authorList.addLast(author)
        return author
    }

    @DgsQuery
    fun authors(): List<Author> {
        return authorList
    }

    @DgsQuery
    fun author(id: String): Author? {
        return authorList.find { author -> author.id == id }
    }

    @DgsQuery
    fun books(): List<Book> {
        return bookList
            .stream()
            .map { b -> b.toBook(authorList) }
            .toList()
    }

    @DgsQuery
    fun book(id: String): Book? {
        return bookList
            .find { book -> book.id == id }
            ?.toBook(authorList)
    }
}

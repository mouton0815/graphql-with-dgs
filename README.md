# GraphQL with Netflix DGS

```
mvn spring-boot:run
```

Example query for a GraphQL client:
```graphql
{
  author(id: 2) {
    name
    birth
    books {
      title
      year  
    }
  }
}
```
Curl equivalent:
```shell
curl -X POST \
  -H 'Content-Type: application/json' \
  -d '{"query": "{ author(id: 2) { name birth books { title year } } }"}' \
  http://localhost:8080/graphql
```
Create new author:
```graphql
mutation CreateAuthor($name: String!, $birth: String, $city: String) {
    createAuthor(name: $name, birth: $birth, city: $city) {
        id
        name
    }
}
```
with example author data:
```json
{
    "name": "John Steinbeck",
    "birth": "1902-02-27",
    "city": "Salinas"
}
```
Create new book for the author:
```graphql
mutation CreateBook($title: String!, $year: Int!, $authorId: ID!) {
    createBook(title: $title, year: $year, authorId: $authorId) {
        id
        title
    }
}
```
with example book data:
```json
{
  "title": "Of Mice and Men",
  "year": 1937,
  "authorId": "5"
}
```




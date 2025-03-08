# GraphQL with Netflix DSG

```
mvn spring-boot:run
```

Example query for a GraphQL client:
```graphql
{
  author(id: 2) {
    id
    name
    birth
    books {
      title
    }
  }
}
```
Curl equivalent:
```shell
curl -X POST \
  -H 'Content-Type: application/json' \
  -d '{"query": "{ author(id: 2) { id name birth books { title } } }"}' \
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
with an example author:
```json
{
    "name": "John Steinbeck",
    "birth": "1902-02-27",
    "city": "Salinas"
}
```
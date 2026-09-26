# Project 1 — Public API Data Dashboard

## Purpose

A Java CLI application that:

1. Calls a public REST API
2. Receives JSON data
3. Converts JSON into Java objects using Jackson
4. Filters data using Streams
5. Handles HTTP errors and invalid user input
6. Displays a simple TODO dashboard

---

# 1. HTTP Client in Java

Java provides `HttpClient` for making HTTP requests.

### Important classes

```java
HttpClient
HttpRequest
HttpResponse
URI
```

### Basic flow

```text
Create HttpClient
       ↓
Create HttpRequest
       ↓
Send request
       ↓
Receive HttpResponse
       ↓
Check status code
       ↓
Process response body
```

### Create client

```java
HttpClient client = HttpClient.newHttpClient();
```

### Create GET request

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://example.com"))
        .GET()
        .build();
```

### Send request

```java
HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());
```

### Important response methods

```java
response.statusCode()
response.body()
```

Remember:

```text
statusCode() → HTTP status
body()       → response data
```

---

# 2. HTTP Status Codes

Common status codes:

| Code | Meaning |
|---|---|
| 200 | OK |
| 201 | Created |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Server Error |

In the project we checked the status before processing the JSON.

Example:

```java
if (response.statusCode() == 200) {
    // process response
} else {
    handleError(response.statusCode());
}
```

---

# 3. JSON

JSON is a common data format used by APIs.

### JSON object

```json
{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```

This represents one object.

### JSON array

```json
[
  {
    "id": 1,
    "title": "Todo 1"
  },
  {
    "id": 2,
    "title": "Todo 2"
  }
]
```

This represents multiple objects.

Important distinction:

```text
JSON object → Java object

JSON array  → List<JavaObject>
```

---

# 4. Jackson

Jackson converts JSON into Java objects and vice versa.

Main class used:

```java
ObjectMapper
```

Create it:

```java
ObjectMapper mapper = new ObjectMapper();
```

---

## JSON Object → Java Object

Suppose the API returns one Todo.

```java
Todo todo = mapper.readValue(
        response.body(),
        Todo.class
);
```

Concept:

```text
JSON
 ↓
ObjectMapper
 ↓
Todo object
```

---

# 5. JSON Array → List<Todo>

For an array, Jackson needs to know the generic type.

```java
List<Todo> todos = mapper.readValue(
        response.body(),
        new TypeReference<List<Todo>>() {}
);
```

Required imports:

```java
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
```

### Why TypeReference?

Because Java generics use type erasure at runtime.

Jackson needs explicit information that the response represents:

```text
List<Todo>
```

rather than just:

```text
List
```

---

# 6. Java Model Class

The API's JSON structure was represented by:

```java
Todo
```

The fields were:

```text
userId
id
title
completed
```

Example:

```java
private int userId;
private int id;
private String title;
private boolean completed;
```

Getters followed Java naming conventions:

```java
getUserId()
getId()
getTitle()
isCompleted()
```

Boolean getters commonly use:

```java
isCompleted()
```

instead of:

```java
getCompleted()
```

---

# 7. Collections — List

The API returned multiple TODOs, so we stored them in:

```java
List<Todo>
```

Example:

```java
List<Todo> todos
```

Useful methods:

```java
todos.size()
todos.isEmpty()
todos.forEach(...)
```

### `size()`

Returns number of elements.

```java
todos.size()
```

### `isEmpty()`

Checks whether the list contains no elements.

```java
if (todos.isEmpty()) {
    System.out.println("No todos");
}
```

Prefer `isEmpty()` when the actual question is:

> "Are there any elements?"

---

# 8. Streams

Streams allow us to process collections in a declarative way.

Basic structure:

```java
todos.stream()
```

A stream does not normally modify the original collection.

---

# 9. filter()

`filter()` keeps elements that satisfy a condition.

Example:

```java
todos.stream()
        .filter(todo -> !todo.isCompleted())
        .toList();
```

Meaning:

```text
Take todos
 ↓
Check each todo
 ↓
Keep only incomplete todos
 ↓
Create a new List
```

---

# 10. Lambda Expressions

A lambda is a short way to provide behavior.

Example:

```java
todo -> todo.isCompleted()
```

Can be understood as:

```text
For each todo:
    return todo.isCompleted()
```

Another example:

```java
todo -> todo.getUserId() == userId
```

Meaning:

```text
Keep todos whose userId matches userId
```

---

# 11. Multiple Filters

Filters can be chained.

Project example:

```java
return todos.stream()
        .filter(todo -> todo.getUserId() == userId)
        .filter(todo -> !todo.isCompleted())
        .toList();
```

This means:

```text
All todos
   ↓
Only requested user
   ↓
Only incomplete todos
   ↓
Return List<Todo>
```

The order is often written from broad filtering to more specific filtering.

---

# 12. toList()

After filtering, we often wanted a collection.

```java
.toList()
```

Example:

```java
List<Todo> incompleteTodos = todos.stream()
        .filter(todo -> !todo.isCompleted())
        .toList();
```

Important:

```text
stream()
```

starts stream processing.

```text
filter()
```

selects elements.

```text
toList()
```

collects the result into a List.

---

# 13. count()

When we only need the number of matching elements, use `count()`.

Project example:

```java
long completed = todos.stream()
        .filter(todo -> todo.isCompleted())
        .filter(todo -> todo.getUserId() == userId)
        .count();
```

Return type:

```java
long
```

Do not unnecessarily create a List just to count it.

Avoid:

```java
.toList().size()
```

when the actual goal is counting.

Prefer:

```java
.count()
```

---

# 14. forEach()

`forEach()` performs an action for every element.

Example:

```java
todos.forEach(todo -> {
    System.out.println(todo.getId());
});
```

You can also use it directly on a collection.

Instead of:

```java
todos.stream()
        .forEach(...)
```

use:

```java
todos.forEach(...)
```

when you are simply iterating and not doing stream operations.

---

# 15. Filtering by User

Project method:

```java
static List<Todo> getIncompleteTodosByUser(
        List<Todo> todos,
        int userId
)
```

Logic:

```text
todos
 ↓
userId matches?
 ↓ yes
completed == false?
 ↓ yes
keep todo
```

Implementation pattern:

```java
todos.stream()
        .filter(todo -> todo.getUserId() == userId)
        .filter(todo -> !todo.isCompleted())
        .toList();
```

---

# 16. Handling Empty Results

Filtering may produce zero results.

Always consider:

```java
if (todos.isEmpty()) {
    System.out.println("No todos found");
    return;
}
```

This is different from an error.

For example:

```text
User 999
    ↓
API works
    ↓
No matching todos
```

This is a valid result, not an HTTP error.

---

# 17. Error Handling

The project handled HTTP errors using a method:

```java
static void handleError(int statusCode)
```

A `switch` was used:

```java
switch (statusCode) {
    case 400:
        // Bad Request
        break;

    case 401:
        // Unauthorized
        break;

    case 403:
        // Forbidden
        break;

    case 404:
        // Not Found
        break;

    case 500:
        // Server Error
        break;

    default:
        // Unexpected status
}
```

Important:

`break` prevents execution from continuing into the next case.

---

# 18. Exceptions

The CLI accepts integer input.

```java
int userId = scanner.nextInt();
```

If the user enters:

```text
abc
```

instead of:

```text
1
```

Java can throw:

```java
InputMismatchException
```

We handled it with:

```java
try {
    // input
}
catch (InputMismatchException e) {
    System.out.println("Invalid Input, Enter Integer");
    scanner.nextLine();
}
```

### Important distinction

```text
HTTP error
    ↓
API/server problem

InputMismatchException
    ↓
User entered invalid input
```

They are different failure types.

---

# 19. Scanner

Used for CLI input:

```java
Scanner scanner = new Scanner(System.in);
```

Read integer:

```java
int choice = scanner.nextInt();
```

Close scanner when finished:

```java
scanner.close();
```

---

# 20. switch

The menu used a `switch`.

Example:

```java
switch (choice) {
    case 1:
        // incomplete todos
        break;

    case 2:
        // completed todos
        break;

    case 3:
        // exit
        break;

    default:
        // invalid option
}
```

Flow:

```text
User chooses option
        ↓
switch
   ↙    ↓    ↘
  1     2     3
  ↓     ↓     ↓
incomplete completed exit
```

---

# 21. Maven

Maven manages the Java project build.

Important commands:

### Compile

```bash
mvn compile
```

### Clean + compile

```bash
mvn clean compile
```

`clean` removes previous build output.

`compile` compiles the source code.

### Run

This project configured the Maven Exec Plugin, so:

```bash
mvn exec:java
```

runs:

```text
com.apidashboard.Main
```

---

# 22. Maven Dependency — Jackson

Jackson was added to `pom.xml` as a dependency.

The important idea:

```text
pom.xml
   ↓
Maven downloads dependency
   ↓
Java project can import Jackson
```

Without the dependency, imports such as:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
```

would not work.

---

# 23. Project Architecture

Project 1 intentionally stayed simple.

```text
Main
 │
 ├── HTTP request
 │
 ├── Jackson JSON parsing
 │
 ├── filtering
 │
 ├── counting
 │
 └── CLI display
```

Main learning goal was not architecture.

The goal was learning how Java can:

```text
Call API
   ↓
Receive JSON
   ↓
Convert JSON to objects
   ↓
Store objects in List
   ↓
Process List with Streams
   ↓
Display results
```

---

# 24. Complete Data Flow

The most important thing to remember from Project 1:

```text
User
 │
 │ enters User ID
 ▼
Java CLI
 │
 │ HTTP GET
 ▼
Public REST API
 │
 │ JSON response
 ▼
HttpResponse<String>
 │
 │ response.body()
 ▼
Jackson ObjectMapper
 │
 │ deserialize
 ▼
List<Todo>
 │
 │ Stream operations
 ├───────────────┐
 ▼               ▼
filter()       count()
 │               │
 ▼               ▼
Filtered List   long
 │
 ▼
CLI output
```

---

# 25. Common Patterns to Remember

## Get data from an API

```text
HttpClient
→ HttpRequest
→ client.send()
→ response
```

## Convert one JSON object

```text
mapper.readValue(body, Todo.class)
```

## Convert JSON array

```text
mapper.readValue(
    body,
    new TypeReference<List<Todo>>() {}
)
```

## Filter a list

```text
list.stream()
    .filter(...)
    .toList()
```

## Count matching elements

```text
list.stream()
    .filter(...)
    .count()
```

## Check empty result

```text
list.isEmpty()
```

---

# 26. Things I Should Be Able to Explain Without Looking

Before considering Project 1 fully learned, I should be able to explain:

- What an HTTP GET request does
- What `HttpClient` does
- Difference between `HttpRequest` and `HttpResponse`
- What `statusCode()` and `body()` return
- What JSON is
- Difference between a JSON object and JSON array
- What `ObjectMapper` does
- Why `TypeReference<List<Todo>>` is used
- What a Java `List` is
- What `stream()` does
- What `filter()` does
- What a lambda expression is
- Difference between `toList()` and `count()`
- What `isEmpty()` checks
- Why `InputMismatchException` occurs
- How `switch` handles menu choices
- What Maven does
- Why dependencies are declared in `pom.xml`

---

# 27. Debugging Checklist

When an API project fails, check in this order:

### 1. Does the project compile?

```bash
mvn clean compile
```

### 2. Is the API URL correct?

Check the URI.

### 3. What HTTP status did we receive?

```java
response.statusCode()
```

### 4. What did the API actually return?

```java
response.body()
```

### 5. Does the Java model match the JSON?

Check:

```text
field names
field types
getters/setters
```

### 6. Is the JSON an object or array?

```text
object → Todo
array  → List<Todo>
```

### 7. Is the filtering condition correct?

Check each `.filter()`.

### 8. Is the result empty?

Use:

```java
result.isEmpty()
```

### 9. Is user input valid?

Check for:

```java
InputMismatchException
```

---

# 28. Project 1 Lessons

The major lesson is that a backend program often performs a pipeline like:

```text
INPUT
  ↓
REQUEST
  ↓
RESPONSE
  ↓
DESERIALIZATION
  ↓
COLLECTION
  ↓
PROCESSING
  ↓
OUTPUT
```

Project 1 connected several Java concepts together:

```text
Collections
    +
Generics
    +
Lambda expressions
    +
Streams
    +
Exceptions
    +
Maven
    +
HTTP
    +
REST
    +
JSON
```

This is the bridge between basic Java programming and backend development.

---

# 29. Revision Exercise

When revisiting this project, try rebuilding these pieces without looking at the original code:

### Exercise 1

Create an `HttpClient` and make a GET request.

### Exercise 2

Print:

```text
HTTP status
response body
```

### Exercise 3

Create a `Todo` model and deserialize one JSON object.

### Exercise 4

Deserialize an array into:

```java
List<Todo>
```

### Exercise 5

Return only incomplete todos.

### Exercise 6

Return todos belonging to a specific user.

### Exercise 7

Count completed todos for a user.

### Exercise 8

Handle an invalid integer entered through `Scanner`.

### Exercise 9

Create a menu using `switch`.

If these can be implemented without copying, the concepts are being retained rather than memorized.

---

# 30. Key Mental Models

### HTTP

```text
Request → Server → Response
```

### JSON deserialization

```text
JSON → Java Object
```

### Streams

```text
Collection → Stream → Operations → Result
```

### filter

```text
Keep elements that satisfy condition
```

### count

```text
How many elements satisfy condition?
```

### Maven

```text
pom.xml → dependencies/build configuration → Maven build
```

### Exception

```text
Unexpected situation → catch → handle
```

---

# Project Status

## Project 1: COMPLETE ✅

Validated:

- API request works
- JSON parsing works
- User filtering works
- Completed filtering works
- Incomplete filtering works
- Empty results handled
- Invalid menu option handled
- Invalid integer input handled
- Maven compilation successful
- Maven execution successful

Next major roadmap area:

**SQL + PostgreSQL**
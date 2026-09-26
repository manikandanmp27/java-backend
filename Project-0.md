# Project 0 — Java Expense Analyzer

## Purpose

A Java CLI application built to practice core Java and modern Java concepts before moving into backend development.

The application manages expenses in memory and supports:

1. Add Expense
2. View All Expenses
3. Calculate Total Expenses
4. Group Expenses by Category
5. Find Highest Expense
6. Sort Expenses
7. Filter by Category
8. Filter by Date
9. Exit

---

# 1. Project Structure

The project intentionally uses a simple architecture:

```text
expense-analyzer/
├── pom.xml
└── src/
    ├── main/java/com/expenseanalyzer/
    │   ├── Main.java
    │   ├── Expense.java
    │   ├── ExpenseManager.java
    │   └── Category.java
    └── test/java/
```

Main relationship:

```text
Main
  ↓
ExpenseManager
  ↓
List<Expense>
```

The goal was to learn Java fundamentals, not create a complicated architecture.

---

# 2. Expense Model

An expense contains:

```text
id
description
category
amount
date
```

The types used were:

```text
id          → int
description → String
category    → Category
amount      → BigDecimal
date        → LocalDate
```

---

# 3. Encapsulation

The `Expense` class keeps its fields private.

Example:

```java
private int id;
private String description;
private Category category;
private BigDecimal amount;
private LocalDate date;
```

Other classes access the data through methods such as:

```java
getId()
getDescription()
getCategory()
getAmount()
getDate()
```

### Why?

Encapsulation means:

```text
Keep data protected inside the object
        ↓
Control access through methods
```

Instead of allowing direct access:

```java
expense.amount
```

we use:

```java
expense.getAmount()
```

---

# 4. Getters and Setters

A getter reads a value.

```java
expense.getAmount()
```

A setter changes a value.

```java
expense.setAmount(...)
```

The `id` did not have a setter because an expense ID should not normally be changed after creation.

Mental model:

```text
Getter → get data
Setter → modify data
```

---

# 5. Enum

Categories were represented using an enum:

```text
FOOD
TRAVEL
SHOPPING
BILLS
ENTERTAINMENT
OTHER
```

Example:

```java
Category.FOOD
```

instead of:

```java
"food"
```

### Why use enum?

An enum represents a fixed set of allowed values.

```text
Category
   ↓
FOOD
TRAVEL
SHOPPING
BILLS
ENTERTAINMENT
OTHER
```

This reduces invalid category values.

---

# 6. Collections — List

Expenses are stored in memory using:

```java
List<Expense>
```

The implementation used:

```java
ArrayList<Expense>
```

Example concept:

```java
List<Expense> expenses = new ArrayList<>();
```

Mental model:

```text
List<Expense>
      ↓
[Expense, Expense, Expense, Expense]
```

A List:

- stores multiple objects
- preserves order
- allows duplicates
- can grow dynamically

---

# 7. Generics

This:

```java
List<Expense>
```

is a generic collection.

`Expense` tells Java what type the list should contain.

Example:

```java
List<Expense>
```

means:

```text
This List contains Expense objects.
```

Another example:

```java
List<String>
```

means:

```text
This List contains String objects.
```

### Mental model

```text
List<T>
   ↓
T = type
```

For the project:

```text
T = Expense
```

---

# 8. ArrayList

`ArrayList` is a common implementation of `List`.

```java
new ArrayList<>()
```

It provides dynamic storage.

Instead of deciding the number of expenses beforehand:

```text
expense 1
expense 2
expense 3
...
```

the list grows as expenses are added.

---

# 9. Adding an Expense

The manager provides an operation for adding expenses.

Conceptually:

```java
expenses.add(expense);
```

Flow:

```text
User input
   ↓
Create Expense
   ↓
ExpenseManager
   ↓
expenses.add(...)
   ↓
List<Expense>
```

---

# 10. Defensive Copy

`getAllExpenses()` does not directly expose the internal list.

Instead, it returns a copy.

Mental model:

```text
Internal list
    ↓
Create copy
    ↓
Return copy
```

Why?

Because exposing the actual internal list would allow outside code to modify the manager's internal state directly.

This is another example of encapsulation.

---

# 11. BigDecimal

Expense amounts use:

```java
BigDecimal
```

instead of:

```java
double
```

Example:

```java
BigDecimal amount
```

### Why?

Money calculations need decimal precision.

For example, financial calculations can produce unexpected results when floating-point types are used.

For this project:

```text
Money → BigDecimal
```

Mental rule:

> Use `BigDecimal` when exact decimal arithmetic is important.

---

# 12. BigDecimal Addition

BigDecimal is immutable.

Instead of:

```java
amount += otherAmount;
```

you use methods such as:

```java
amount.add(otherAmount)
```

Example:

```java
BigDecimal total = amount1.add(amount2);
```

The result is a new `BigDecimal`.

---

# 13. LocalDate

The expense date uses:

```java
LocalDate
```

Example:

```java
LocalDate date
```

It represents a date without a time.

Example:

```text
2026-09-26
```

Use:

```text
LocalDate
```

when you need:

```text
year + month + day
```

but not:

```text
hour + minute + second
```

---

# 14. Date Filtering

The project can filter expenses by date.

Conceptually:

```text
Expense date
     ↓
Compare with requested date
     ↓
Keep matching expenses
```

Useful `LocalDate` operations include:

```java
isBefore(...)
isAfter(...)
isEqual(...)
```

Mental model:

```text
LocalDate
   ↓
compare dates
   ↓
filter expenses
```

---

# 15. Streams

Streams were one of the major goals of Project 0.

Basic structure:

```java
expenses.stream()
```

A stream allows us to process the collection declaratively.

Typical pattern:

```text
Collection
    ↓
stream()
    ↓
filter / map / sort / etc.
    ↓
result
```

---

# 16. filter()

`filter()` keeps elements that satisfy a condition.

Example:

```java
expenses.stream()
        .filter(expense -> ...)
```

Mental model:

```text
All expenses
     ↓
Condition?
   ↙     ↘
 yes      no
  ↓        ↓
keep     discard
```

---

# 17. Lambda Expressions

A lambda provides a small piece of behavior.

Example:

```java
expense -> expense.getAmount()
```

Another example:

```java
expense -> expense.getCategory() == Category.FOOD
```

Mental model:

```text
input → expression
```

For example:

```java
expense -> expense.getAmount()
```

means roughly:

```text
Take an expense
and return its amount.
```

---

# 18. map()

`map()` transforms each element into another value.

Example concept:

```java
expenses.stream()
        .map(expense -> expense.getAmount())
```

Before:

```text
Expense
Expense
Expense
```

After `map()`:

```text
BigDecimal
BigDecimal
BigDecimal
```

Mental model:

```text
map = transform
```

---

# 19. Calculate Total Expenses

The project calculates the total using a stream.

Conceptual pipeline:

```text
List<Expense>
      ↓
stream()
      ↓
map(getAmount)
      ↓
BigDecimal values
      ↓
reduce()
      ↓
total
```

Important idea:

```text
map()
```

extracts the amounts.

```text
reduce()
```

combines them into one result.

---

# 20. reduce()

`reduce()` combines multiple stream elements into a single result.

Example concept:

```text
10 + 20 + 30
     ↓
   reduce
     ↓
    60
```

For expenses:

```text
Expense amounts
     ↓
10.50
20.00
15.25
     ↓
reduce
     ↓
45.75
```

Mental model:

```text
reduce = combine many values into one
```

---

# 21. Optional

Finding the highest expense may produce no result if there are no expenses.

Instead of returning `null`, the project used:

```java
Optional<Expense>
```

Mental model:

```text
Optional<Expense>
        ↓
May contain Expense
or
May contain nothing
```

This forces the caller to think about the empty case.

---

# 22. Why Optional?

Imagine:

```text
Expense list
    ↓
find highest
    ↓
No expenses?
```

There may be no Expense to return.

`Optional` represents that possibility explicitly.

Conceptually:

```text
Optional<Expense>
        │
        ├── Expense exists
        │
        └── Empty
```

---

# 23. Finding Highest Expense

The project used a comparator to determine which expense has the highest amount.

Conceptual flow:

```text
List<Expense>
      ↓
stream()
      ↓
max(...)
      ↓
Optional<Expense>
```

Important:

```text
max()
```

finds the largest element according to a comparison rule.

---

# 24. Comparator

A `Comparator` defines how two objects should be compared.

For expenses, we can compare:

```text
amount
date
description
```

Example concept:

```java
Comparator.comparing(Expense::getAmount)
```

Mental model:

```text
Comparator
    ↓
How should these objects be ordered?
```

---

# 25. Sorting

The project supports sorting expenses.

Sorting requires deciding what property determines the order.

Examples:

```text
amount
date
description
```

Conceptual flow:

```text
List<Expense>
      ↓
sort
      ↓
ordered List
```

---

# 26. Comparator vs Comparable

Important distinction:

### Comparable

Defines the object's natural ordering.

Concept:

```text
Expense knows its default ordering.
```

### Comparator

Defines an external/custom ordering.

Concept:

```text
Compare expenses by amount
Compare expenses by date
Compare expenses by description
```

Mental model:

```text
Comparable
→ natural/default order

Comparator
→ custom order
```

Project 0 mainly used `Comparator` for sorting/comparison.

---

# 27. Grouping by Category

The project groups expenses by category.

Conceptually:

```text
FOOD
 ├── Expense
 ├── Expense
 └── Expense

TRAVEL
 ├── Expense
 └── Expense

BILLS
 ├── Expense
 └── Expense
```

This was implemented using:

```java
Collectors.groupingBy(...)
```

Mental model:

```text
List<Expense>
      ↓
groupingBy(category)
      ↓
Map<Category, List<Expense>>
```

---

# 28. Map

Grouping produces a structure conceptually like:

```java
Map<Category, List<Expense>>
```

Example:

```text
FOOD → [Expense, Expense]
TRAVEL → [Expense]
BILLS → [Expense, Expense]
```

A `Map` stores:

```text
key → value
```

Here:

```text
Category → List<Expense>
```

---

# 29. Collectors.groupingBy()

Important stream collector:

```java
Collectors.groupingBy(...)
```

It groups elements according to a key.

Example mental model:

```text
Expenses
   ↓
getCategory()
   ↓
same category → same group
```

Result:

```text
Map<Category, List<Expense>>
```

---

# 30. Filtering by Category

Category filtering uses the enum value.

Concept:

```java
expense -> expense.getCategory() == category
```

Because `Category` is an enum, comparison with `==` is appropriate.

Mental flow:

```text
All expenses
    ↓
Category == FOOD?
    ↓
keep matching expenses
```

---

# 31. Exceptions

The CLI accepts user input, so invalid input can occur.

For example, if an integer is expected:

```java
scanner.nextInt();
```

but the user enters text, Java can throw:

```java
InputMismatchException
```

The application handles it using:

```java
try {
    // risky input operation
}
catch (InputMismatchException e) {
    // handle invalid input
}
```

---

# 32. try / catch Mental Model

```text
try
 ↓
Code that might fail
 ↓
Exception?
 ↓ yes
catch
 ↓
Handle problem
```

The application should not unexpectedly crash just because the user entered invalid input.

---

# 33. Variable Scope

One important debugging lesson from Project 0 was variable scope.

A variable declared inside a block:

```java
if (...) {
    int value = 10;
}
```

cannot normally be accessed outside that block.

Mental model:

```text
{
    variable exists here
}
```

When debugging:

> Check where the variable was declared and where you are trying to use it.

---

# 34. switch

The CLI menu uses `switch` to select an operation.

Concept:

```java
switch (choice) {
    case 1:
        // add
        break;

    case 2:
        // view
        break;

    default:
        // invalid choice
}
```

`break` prevents fall-through into the next case.

---

# 35. Maven

Maven manages the project build and dependencies.

Important project file:

```text
pom.xml
```

It contains information such as:

```text
groupId
artifactId
version
Java version
dependencies
plugins
```

---

# 36. Maven Coordinates

The project used:

```text
groupId    → com.expenseanalyzer
artifactId → expense-analyzer
version    → 1.0-SNAPSHOT
```

Mental model:

```text
groupId
   +
artifactId
   +
version
   ↓
Identifies the Maven project
```

---

# 37. Important Maven Commands

### Compile

```bash
mvn compile
```

### Clean build

```bash
mvn clean compile
```

### Run tests

```bash
mvn test
```

### Package

```bash
mvn package
```

The most useful development check used in the project was:

```bash
mvn clean compile
```

---

# 38. Project Architecture

Project 0 intentionally kept the architecture simple:

```text
Main
  ↓
ExpenseManager
  ↓
List<Expense>
```

Supporting model:

```text
Category
```

`Main` handles CLI interaction.

`ExpenseManager` handles expense operations.

`Expense` represents an expense.

`Category` represents valid categories.

---

# 39. Responsibility of Each Class

## Main

Responsible for:

```text
CLI
user input
menu
display
calling ExpenseManager
```

## Expense

Responsible for:

```text
representing one expense
```

## ExpenseManager

Responsible for:

```text
storing expenses
calculating totals
filtering
sorting
grouping
finding highest expense
```

## Category

Responsible for:

```text
valid expense categories
```

---

# 40. Complete Project Data Flow

The most important mental model:

```text
User
 ↓
Main
 ↓
ExpenseManager
 ↓
List<Expense>
 ↓
Streams / Collections
 ↓
Result
 ↓
Main
 ↓
CLI output
```

For example:

```text
User chooses "Total"
       ↓
Main
       ↓
ExpenseManager.calculateTotal()
       ↓
expenses.stream()
       ↓
map(amount)
       ↓
reduce()
       ↓
BigDecimal total
       ↓
Main displays total
```

---

# 41. Concept Map

Project 0 connected the Java concepts together:

```text
Classes
  +
Encapsulation
  +
Enums
  +
Collections
  +
Generics
  +
Lambda expressions
  +
Streams
  +
Optional
  +
Comparator
  +
BigDecimal
  +
LocalDate
  +
Exceptions
  +
Maven
```

This was the foundation for Project 1.

---

# 42. Common Stream Patterns

## Filter

```java
list.stream()
    .filter(...)
    .toList();
```

Meaning:

```text
Keep matching elements.
```

## Transform

```java
list.stream()
    .map(...)
```

Meaning:

```text
Convert each element into another value.
```

## Count

```java
list.stream()
    .filter(...)
    .count();
```

Meaning:

```text
Count matching elements.
```

## Maximum

```java
list.stream()
    .max(...)
```

Meaning:

```text
Find the largest element according to a comparison rule.
```

## Group

```java
list.stream()
    .collect(Collectors.groupingBy(...));
```

Meaning:

```text
Group elements by a key.
```

---

# 43. Debugging Checklist

When something goes wrong:

### 1. Does the project compile?

```bash
mvn clean compile
```

### 2. Is the data actually being added?

Check:

```java
expenses.size()
```

### 3. Is the correct object being passed?

Check method arguments.

### 4. Is the stream condition correct?

Inspect each:

```java
.filter(...)
```

### 5. Is the result empty?

Check:

```java
result.isEmpty()
```

### 6. Is the comparison correct?

For example:

```text
amount
date
category
```

### 7. Could there be no result?

Consider:

```java
Optional<Expense>
```

### 8. Could user input be invalid?

Consider:

```java
try/catch
```

### 9. Is the variable in scope?

Check where it was declared.

---

# 44. Things I Should Be Able to Explain Without Looking

Before revisiting Project 0, I should be able to explain:

- What a class is
- What encapsulation means
- Why fields are private
- What getters and setters do
- What an enum is
- Why `Category` is an enum
- What a `List` is
- What `ArrayList` is
- What generics are
- What `List<Expense>` means
- What a lambda expression is
- What `stream()` does
- What `filter()` does
- What `map()` does
- What `reduce()` does
- What `count()` does
- What `Optional` represents
- What `Comparator` does
- Difference between Comparable and Comparator
- What `groupingBy()` does
- What a `Map` is
- Why `BigDecimal` is used for money
- What `LocalDate` represents
- How `try/catch` works
- What `InputMismatchException` means
- What variable scope means
- What Maven does
- What `pom.xml` is

---

# 45. Revision Exercises

When revisiting Project 0, try implementing these without looking at the original code.

### Exercise 1 — Model

Create an `Expense` with:

```text
id
description
category
amount
date
```

### Exercise 2 — Collection

Create:

```java
List<Expense>
```

and add several expenses.

### Exercise 3 — Filter

Return only expenses belonging to:

```text
FOOD
```

### Exercise 4 — Total

Calculate the total amount using:

```text
stream
map
reduce
BigDecimal
```

### Exercise 5 — Highest Expense

Find the highest expense using:

```text
stream
max
Comparator
Optional
```

### Exercise 6 — Grouping

Group expenses by:

```text
Category
```

and produce:

```java
Map<Category, List<Expense>>
```

### Exercise 7 — Sorting

Sort expenses by:

```text
amount
```

Then try sorting by:

```text
date
```

### Exercise 8 — Date Filtering

Use `LocalDate` to filter expenses by date.

### Exercise 9 — Exception Handling

Make the CLI handle invalid integer input.

### Exercise 10 — Menu

Create a menu using:

```text
switch
```

---

# 46. Key Mental Models

### Object

```text
Class
 ↓
Object
```

A class defines the structure; an object is an instance of that class.

### Encapsulation

```text
private data
     ↓
controlled access
     ↓
methods
```

### Enum

```text
Fixed set of valid values
```

### Generic

```text
List<T>
   ↓
T = type
```

### Stream

```text
Collection
 ↓
stream
 ↓
operations
 ↓
result
```

### filter

```text
Keep matching elements
```

### map

```text
Transform elements
```

### reduce

```text
Many values → one value
```

### Optional

```text
Value may exist
or
value may be absent
```

### Comparator

```text
Defines how objects are compared
```

### Map

```text
key → value
```

### Exception

```text
Unexpected situation
 ↓
catch
 ↓
handle
```

### Maven

```text
pom.xml
 ↓
dependencies + build configuration
 ↓
Maven
 ↓
compile / test / package
```

---

# 47. Project 0 → Project 1 Connection

Project 0 taught the Java fundamentals.

Project 1 then applied several of them to an API.

```text
PROJECT 0
Java objects
Collections
Generics
Streams
Lambdas
Exceptions
Maven
        ↓
PROJECT 1
        ↓
HTTP
REST API
JSON
Jackson
        ↓
Java objects
Collections
Streams
Exceptions
Maven
```

The important progression was:

```text
Learn Java
   ↓
Use Java with external data
   ↓
Move toward backend development
```

---

# Project Status

## Project 0: COMPLETE ✅

Implemented and validated:

- Expense model
- Category enum
- In-memory `List<Expense>`
- Add expenses
- View expenses
- Calculate total
- Group by category
- Find highest expense
- Sort expenses
- Filter by category
- Filter by date
- Optional
- Comparator
- Streams
- Lambdas
- BigDecimal
- LocalDate
- Exception handling
- CLI menu
- Maven project

Next project in the roadmap:

**Project 1 — Public API Data Dashboard**

Project 1 is also complete.
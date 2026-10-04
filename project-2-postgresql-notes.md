# Project 2 — PostgreSQL Expense / Finance Database

## Purpose

This project introduces PostgreSQL through a practical expense database.

The database models users, expenses, and categories and builds the SQL foundation needed for later Spring and JPA work.

## Database Structure

```text
PostgreSQL Server
    ↓
expense_db
    ↓
public schema
    ↓
users
categories
expenses
```

Relationships:

```text
users
   │
   │ 1 → many
   ▼
expenses
   ▲
   │ many → 1
   │
categories
```

One user can have many expenses.

One category can contain many expenses.

## Users Table

The `users` table stores application users.

Main columns:

```text
id
name
email
```

Example definition:

```sql
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

Email uniqueness was added separately:

```sql
ALTER TABLE users
ADD CONSTRAINT set_email_unique UNIQUE (email);
```

### Concepts

- `BIGINT` stores large integer IDs.
- `GENERATED ALWAYS AS IDENTITY` generates IDs automatically.
- `PRIMARY KEY` uniquely identifies each user.
- `NOT NULL` requires a value.
- `UNIQUE` prevents duplicate email values.

## Categories Table

The `categories` table stores expense categories.

Main columns:

```text
id
name
```

Categories used:

```text
FOOD
TRAVEL
SHOPPING
BILLS
ENTERTAINMENT
OTHER
```

The ID is automatically generated and the category name is required and unique.

Example:

```sql
CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

ALTER TABLE categories
ALTER COLUMN id
ADD GENERATED ALWAYS AS IDENTITY;
```

Multiple rows can be inserted with one statement:

```sql
INSERT INTO categories (name)
VALUES
    ('FOOD'),
    ('TRAVEL'),
    ('SHOPPING'),
    ('BILLS'),
    ('ENTERTAINMENT'),
    ('OTHER');
```

## Expenses Table

The `expenses` table stores individual expenses.

Main columns:

```text
id
description
amount
expense_date
user_id
category_id
```

The original table was:

```sql
CREATE TABLE expenses(
    id INT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    expense_date DATE NOT NULL
);
```

The ID was later changed to an automatically generated identity:

```sql
ALTER TABLE expenses
ALTER COLUMN id
ADD GENERATED ALWAYS AS IDENTITY;
```

## Expense Amount Constraint

An expense amount was restricted using a `CHECK` constraint:

```sql
ALTER TABLE expenses
ADD CONSTRAINT amount CHECK (amount >= 0);
```

`NOT NULL` only prevents a missing value.

`CHECK` allows us to enforce a value rule.

For example:

```text
amount = NULL       → rejected by NOT NULL
amount = -500       → rejected by CHECK
amount = 250        → valid
```

## Foreign Keys

### Expense → User

The `user_id` column connects an expense to a user:

```sql
ALTER TABLE expenses
ADD COLUMN user_id BIGINT;
```

Foreign-key constraint:

```sql
ALTER TABLE expenses
ADD CONSTRAINT foreign_key_constraint
FOREIGN KEY (user_id)
REFERENCES users(id);
```

This enforces referential integrity.

If user `9999` does not exist, an expense cannot use:

```text
user_id = 9999
```

A foreign key does not need to be unique.

One user can have many expenses.

### Expense → Category

The category relationship was added using:

```sql
ALTER TABLE expenses
ADD COLUMN category_id BIGINT;
```

Then:

```sql
ALTER TABLE expenses
ADD CONSTRAINT fk_expenses_category_id
FOREIGN KEY (category_id)
REFERENCES categories(id);
```

## NULL

`NULL` represents the absence of a value.

When `user_id` and `category_id` were first added to the existing `expenses` table, existing rows had `NULL` because no relationship had been assigned yet.

A foreign key does not automatically populate a value.

A foreign-key column can contain `NULL` unless it also has a `NOT NULL` constraint.

## CRUD Operations

### INSERT

Adds new rows.

```sql
INSERT INTO expenses (description, amount, expense_date)
VALUES ('Lunch', 250.00, '2026-09-27');
```

The identity column does not need to be supplied.

### SELECT

Reads rows.

```sql
SELECT * FROM expenses;
```

Specific columns:

```sql
SELECT description, amount
FROM expenses;
```

Filtering:

```sql
SELECT *
FROM expenses
WHERE amount > 100;
```

### UPDATE

Changes existing rows.

```sql
UPDATE expenses
SET amount = 300.00
WHERE description = 'Lunch';
```

Using a primary key is safer when the exact row is known:

```sql
UPDATE expenses
SET amount = 300.00
WHERE id = 3;
```

### DELETE

Removes rows.

```sql
DELETE FROM expenses
WHERE id = 1;
```

The `WHERE` clause is important because:

```sql
DELETE FROM expenses;
```

can remove every row.

## ALTER TABLE

`ALTER TABLE` changes an existing table.

Examples used in the project:

```sql
ALTER TABLE expenses
ADD COLUMN user_id BIGINT;
```

```sql
ALTER TABLE expenses
ADD COLUMN category_id BIGINT;
```

```sql
ALTER TABLE expenses
ALTER COLUMN id
ADD GENERATED ALWAYS AS IDENTITY;
```

Constraints can also be added after table creation.

## SQL Quotes

String and date values use single quotes:

```sql
'Lunch'
'2026-09-27'
```

Double quotes are used for identifiers.

Recommended style:

```text
SQL keywords/types → uppercase
table/column names  → lowercase
values              → single quotes
```

Example:

```sql
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

Dates are preferably written using ISO format:

```text
YYYY-MM-DD
```

Example:

```text
'2026-09-27'
```

## JOIN

`JOIN` combines related rows from different tables.

Example:

```sql
SELECT expenses.description,
       expenses.amount,
       users.name
FROM expenses
JOIN users
ON expenses.user_id = users.id;
```

The important relationship is:

```text
expenses.user_id = users.id
```

Mental model:

```text
FROM → starting table
JOIN → bring another table
ON   → explain how the tables connect
SELECT → choose the output
```

## Aggregate Functions

Important aggregate functions:

```text
COUNT()
SUM()
AVG()
MIN()
MAX()
```

Examples:

```sql
SELECT COUNT(*)
FROM expenses;
```

```sql
SELECT SUM(amount)
FROM expenses;
```

```sql
SELECT AVG(amount)
FROM expenses;
```

```sql
SELECT MIN(amount)
FROM expenses;
```

```sql
SELECT MAX(amount)
FROM expenses;
```

## GROUP BY

`GROUP BY` creates groups of rows before applying aggregate functions.

Example:

```sql
SELECT categories.name,
       SUM(expenses.amount)
FROM expenses
JOIN categories
ON categories.id = expenses.category_id
GROUP BY categories.name;
```

Conceptually:

```text
FOOD
 ├── Lunch
 └── Coffee
       ↓
     SUM()

TRAVEL
 └── Bus
       ↓
     SUM()
```

The result gives the total expense for each category.

## Filtering

Basic filtering:

```sql
SELECT *
FROM expenses
WHERE amount >= 100;
```

Range:

```sql
SELECT *
FROM expenses
WHERE amount BETWEEN 100 AND 400;
```

Multiple values:

```sql
SELECT *
FROM expenses
WHERE description IN ('Lunch', 'Coffee');
```

Pattern matching:

```sql
SELECT *
FROM expenses
WHERE description LIKE '%oo%';
```

## Sorting

Descending amount:

```sql
SELECT *
FROM expenses
ORDER BY amount DESC;
```

Ascending date:

```sql
SELECT *
FROM expenses
ORDER BY expense_date ASC;
```

## RETURNING

PostgreSQL can return affected rows directly after an `INSERT`, `UPDATE`, or `DELETE`.

Example:

```sql
UPDATE expenses
SET amount = 350.00
WHERE description = 'Lunch'
RETURNING *;
```

This avoids needing a separate query just to see the updated row.

## Project Data

Sample user:

```text
Alice
alice@example.com
```

Sample categories:

```text
FOOD
TRAVEL
SHOPPING
BILLS
ENTERTAINMENT
OTHER
```

Sample expenses used during practice:

```text
Bus
80.00

Lunch
350.00

Coffee
120.00
```

The expenses were associated with the user and appropriate categories.

## Key Lessons

- PostgreSQL uses databases, schemas, tables, rows, and columns.
- Identity columns generate IDs automatically.
- A primary key uniquely identifies a row.
- `NOT NULL` requires a value.
- `UNIQUE` prevents duplicate values.
- `CHECK` enforces a condition.
- Foreign keys enforce referential integrity.
- Foreign keys do not make values unique.
- One user can have many expenses.
- One category can have many expenses.
- `INSERT` adds data.
- `SELECT` reads data.
- `UPDATE` changes data.
- `DELETE` removes data.
- `ALTER TABLE` modifies table definitions.
- `JOIN` combines related tables.
- Aggregate functions calculate values across rows.
- `GROUP BY` calculates aggregates per group.
- `WHERE` filters rows.
- `ORDER BY` sorts rows.
- `RETURNING` displays affected rows.
- `NULL` means the value is absent/unknown.
- Single quotes are used for string/date values.
- IDs should be verified before `UPDATE` or `DELETE`.

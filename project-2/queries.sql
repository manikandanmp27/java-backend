-- Project 2 queries

SELECT * FROM expenses;

SELECT description, amount
FROM expenses;

SELECT *
FROM expenses
WHERE amount > 100;

INSERT INTO expenses (description, amount, expense_date)
VALUES ('Test', NULL, '2026-09-27');

INSERT INTO expenses (description, amount, expense_date)
VALUES ('Invalid Expense', -500.00, '2026-09-27');

INSERT INTO expenses
(description, amount, expense_date, user_id)
VALUES ('Invalid User Expense', 100.00, '2026-09-27', 9999);

INSERT INTO users (name, email)
VALUES ('Another Alice', 'alice@example.com');

UPDATE expenses
SET amount = 300.00
WHERE description = 'Lunch';

SELECT *
FROM expenses
WHERE id = 1;

DELETE FROM expenses
WHERE id = 1;

UPDATE expenses
SET amount = 350.00
WHERE description = 'Lunch'
RETURNING *;

SELECT expenses.description,
       expenses.amount,
       users.name
FROM expenses
JOIN users
ON expenses.user_id = users.id;

SELECT categories.name,
       SUM(expenses.amount)
FROM expenses
JOIN categories
ON categories.id = expenses.category_id
GROUP BY categories.name;

SELECT * FROM users;

SELECT * FROM categories;

SELECT * FROM expenses;

SELECT expenses.description,
       expenses.amount,
       expenses.expense_date,
       users.name AS user_name,
       categories.name AS category_name
FROM expenses
JOIN users
ON expenses.user_id = users.id
JOIN categories
ON expenses.category_id = categories.id;

SELECT COUNT(*)
FROM expenses;

SELECT SUM(amount)
FROM expenses;

SELECT AVG(amount)
FROM expenses;

SELECT MIN(amount)
FROM expenses;

SELECT MAX(amount)
FROM expenses;

SELECT *
FROM expenses
WHERE amount >= 100;

SELECT *
FROM expenses
WHERE amount BETWEEN 100 AND 400;

SELECT *
FROM expenses
WHERE description IN ('Lunch', 'Coffee');

SELECT *
FROM expenses
WHERE description LIKE '%oo%';

SELECT *
FROM expenses
ORDER BY amount DESC;

SELECT *
FROM expenses
ORDER BY expense_date ASC;

-- Project 2 seed data

INSERT INTO users (name, email)
VALUES ('Alice', 'alice@example.com');

INSERT INTO categories (name)
VALUES
    ('FOOD'),
    ('TRAVEL'),
    ('SHOPPING'),
    ('BILLS'),
    ('ENTERTAINMENT'),
    ('OTHER');

INSERT INTO expenses (description, amount, expense_date)
VALUES ('Lunch', 250.00, '2026-09-27');

INSERT INTO expenses (description, amount, expense_date)
VALUES ('Bus', 80.00, '2026-09-27');

UPDATE expenses
SET amount = 350.00
WHERE description = 'Lunch';

INSERT INTO expenses (description, amount, expense_date)
VALUES ('Coffee', 120.00, '2026-09-27');

UPDATE expenses
SET user_id = (
    SELECT id
    FROM users
    WHERE email = 'alice@example.com'
)
WHERE user_id IS NULL;

UPDATE expenses
SET category_id = (
    SELECT id
    FROM categories
    WHERE name = 'TRAVEL'
)
WHERE description = 'Bus';

UPDATE expenses
SET category_id = (
    SELECT id
    FROM categories
    WHERE name = 'FOOD'
)
WHERE description IN ('Lunch', 'Coffee');

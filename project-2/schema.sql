-- Project 2 schema

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);

ALTER TABLE users
ADD CONSTRAINT set_email_unique UNIQUE (email);

CREATE TABLE expenses (
    id INT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    expense_date DATE NOT NULL
);

ALTER TABLE expenses
ALTER COLUMN id
ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE expenses
ADD CONSTRAINT amount CHECK (amount >= 0);

ALTER TABLE expenses
ADD COLUMN user_id BIGINT;

ALTER TABLE expenses
ADD CONSTRAINT foreign_key_constraint
FOREIGN KEY (user_id)
REFERENCES users(id);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

ALTER TABLE categories
ALTER COLUMN id
ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE expenses
ADD COLUMN category_id BIGINT;

ALTER TABLE expenses
ADD CONSTRAINT fk_expenses_category_id
FOREIGN KEY (category_id)
REFERENCES categories(id);

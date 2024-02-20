CREATE TABLE employees (
    id INT PRIMARY Key,
    name VARCHAR(50),
    salary NUMERIC,
    shift_start TIME,
    shift_end TIME,
    manager BOOLEAN
);

CREATE TABLE menu_items (
    id INT PRIMARY Key,
    name VARCHAR(50),
    description TEXT,
    image_data BLOB,
    available BOOLEAN,
    price NUMERIC,
    Category VARCHAR(50)
);


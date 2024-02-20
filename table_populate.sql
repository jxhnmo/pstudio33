INSERT INTO employees (id,name,salary,shift_start,shift_end,manager)
VALUES
(1, 'Chee Surger', 31.5, '9:30:00', '23:30:00', true),
(2, 'Chic Ken', 14.5, '10:00:00', '23:30:00', false),
(3, 'Tenz Ders', 11.5, '10:00:00', '23:30:00', false),
(4, 'Man Ger', 10.0, '10:00:00', '23:30:00', false),
(5, 'Han Surger', 12.0, '10:00:00', '23:30:00', false);

COPY inventory_items FROM 'inventory_items.csv' CSV HEADER
COPY menu_items FROM 'menu_items.csv' CSV HEADER
COPY ingredients FROM 'ingredients.csv' CSV HEADER
COPY inventory_transactions FROM 'inventory_transactions.csv' CSV HEADER
COPY inventory_item_orders FROM 'inventory_item_orders.csv' CSV HEADER
COPY sales_transactions FROM 'sales_transactions.csv' CSV HEADER
COPY sales_items FROM 'sales_items.csv' CSV HEADER
COPY customizations FROM 'customizations.csv' CSV HEADER
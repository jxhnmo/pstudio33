-- 52 weeks of sales history
SELECT 
    DATE_TRUNC('week',purchase_time) as purchase_week,
    COUNT(*) as orders
FROM sales_transactions
GROUP BY
    DATE_TRUNC('week',purchase_time)
ORDER BY
    purchase_week;

-- realistic sales history
SELECT 
    EXTRACT(hour FROM purchase_time) as purchase_hour,
    COUNT(*) as orders,
    SUM(cost) as total_cost
FROM sales_transactions
GROUP BY
    EXTRACT(hour FROM purchase_time)
ORDER BY
    purchase_hour;

-- 2 peak days
SELECT 
    DATE_TRUNC('day',purchase_time) as purchase_day,
    SUM(cost) as total_cost
FROM sales_transactions
GROUP BY
    DATE_TRUNC('day',purchase_time)
ORDER BY
    total_cost DESC
LIMIT
    10;

-- inventory items for 20 menu items    
SELECT
    (SELECT name FROM menu_items WHERE id=menu_id) as menu_item,
    COUNT(*) as items
FROM
    ingredients
GROUP BY
    menu_id;

-- largest restock prices
SELECT
    transaction_date,
    price
FROM
    inventory_transactions
ORDER BY
    price DESC
LIMIT
    10;
    
-- average restock counts
SELECT
    (SELECT item_name FROM inventory_items WHERE id=item_id) as item_name,
    ROUND(AVG(stock),3) as refill_stock
FROM
    inventory_item_orders
GROUP BY
    item_id;
    
-- employee sales
-- we love nepotism, ham surger is incompetent, but is related to the manager, chee surger
SELECT 
    (SELECT name FROM employees WHERE id=employee_id) as employee_name,
    SUM(cost) as total_cost
FROM sales_transactions
GROUP BY
    employee_id
ORDER BY
    total_cost DESC;

-- total sales per item
SELECT
    (SELECT name FROM menu_items WHERE id=menu_id) as menu_item,
    SUM(cost)
FROM
    sales_transactions
    JOIN sales_items
    ON sales_transactions.id = sales_items.sales_id
GROUP BY
    menu_id;

-- average menu items sold per day
SELECT
    (SELECT name FROM menu_items WHERE id=menu_id) as menu_item,
    ROUND(AVG(daily_sales),3) as avg_daily_sales
FROM
    (SELECT
        menu_id,
        DATE_TRUNC('day',purchase_time) as purchase_day,
        SUM(cost) as daily_sales
    FROM
        sales_transactions 
        JOIN sales_items
        ON sales_transactions.id = sales_items.sales_id
    GROUP BY
        menu_id,DATE_TRUNC('day',purchase_time)
    ) as subquery
GROUP BY
    menu_id;

-- ingredient vs sale cost for menu items
SELECT
    (SELECT name FROM menu_items WHERE id=menu_id) as menu_item,
    SUM(price*num) as ingredient_cost,
    (SELECT price FROM menu_items WHERE id=menu_id) as sales_price
FROM
    inventory_items
    JOIN ingredients
    ON ingredients.item_id = inventory_items.id
GROUP BY
    menu_id;

-- how many items do people usually purchase
SELECT
    num_items,
    COUNT(*)
FROM
    (SELECT
        sales_id,
        COUNT(*) as num_items
    FROM
        sales_items
    GROUP BY
        sales_id
    ) as subquery
GROUP BY
    num_items
ORDER BY
    num_items;

-- monthly sales totals
SELECT 
    DATE_TRUNC('month',purchase_time) as purchase_month,
    COUNT(*) as num_orders,
    SUM(cost) as total_sales
FROM sales_transactions
GROUP BY
    DATE_TRUNC('month',purchase_time)
ORDER BY
    purchase_month;

-- Weekly sales distributions
SELECT 
    TO_CHAR(purchase_time,'Day') as day_of_week,
    COUNT(*) as num_orders,
    SUM(cost) as total_sales
FROM sales_transactions
GROUP BY
    TO_CHAR(purchase_time,'Day')
ORDER BY
    total_sales DESC;

-- total restock price per item
SELECT
    (SELECT item_name FROM inventory_items WHERE id=item_id) as item_name,
    ROUND(SUM(price),2)
FROM
    inventory_item_orders
GROUP BY
    item_id;

-- busiest transactions per day for Tenz Ders
SELECT
    DATE_TRUNC('day',purchase_time) as purchase_day,
    COUNT(*) as orders
FROM
    sales_transactions
WHERE
    employee_id = (SELECT id FROM employees WHERE name = 'Tenz Ders')
GROUP BY
    DATE_TRUNC('day',purchase_time)
ORDER BY
    orders DESC
LIMIT
    10;
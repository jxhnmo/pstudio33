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
SELECT 
    (SELECT name FROM employees WHERE id=employee_id) as employee_name,
    SUM(cost) as total_cost
FROM sales_transactions
GROUP BY
    employee_id
ORDER BY
    total_cost DESC;

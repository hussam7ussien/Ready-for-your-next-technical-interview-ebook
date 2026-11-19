-- PostgreSQL practice in temporary tables for a fresh session.
-- Queries use the Appendix C fixtures and resolved cursor parameters.


CREATE TEMP TABLE customers (id BIGINT PRIMARY KEY, name TEXT NOT NULL);
CREATE TEMP TABLE orders (
  id BIGINT PRIMARY KEY, customer_id BIGINT NOT NULL,
  total NUMERIC NOT NULL, status TEXT NOT NULL, created_at TIMESTAMPTZ NOT NULL
);
CREATE TEMP TABLE employees (
  id BIGINT PRIMARY KEY, name TEXT NOT NULL,
  department_id BIGINT NOT NULL, salary NUMERIC NOT NULL
);
CREATE TEMP TABLE users (id BIGINT PRIMARY KEY, email TEXT);
INSERT INTO customers VALUES
 (101,'Ahmed'),(102,'Sara'),(103,'Omar'),(104,'Mona'),(105,'Ali');
INSERT INTO orders VALUES
 (1,101,500,'paid','2026-11-17 10:00:00+00'),
 (2,102,300,'paid','2026-11-17 10:00:00+00'),
 (3,101,700,'paid','2026-11-17 11:00:00+00'),
 (4,103,900,'cancelled','2026-11-17 10:00:00+00'),
 (5,102,200,'paid','2026-11-17 11:00:00+00'),
 (6,103,400,'paid','2026-11-17 11:00:00+00'),
 (7,105,150,'paid','2026-11-17 10:00:00+00'),
 (8,105,200,'paid','2026-11-17 11:00:00+00'),
 (9,105,300,'paid','2026-11-17 11:00:00+00');
INSERT INTO employees VALUES
 (1,'Ahmed',10,10000),(2,'Sara',10,15000),
 (3,'Omar',20,12000),(4,'Mona',20,12000),(5,'Ali',20,8000);
INSERT INTO users VALUES
 (1,'ali@example.com'),(2,'sara@example.com'),(3,'ali@example.com');

-- Query 1 Expected rows [(101, 1200)]
SELECT customer_id, SUM(total) AS total_spent
FROM orders
WHERE status = 'paid'
GROUP BY customer_id
HAVING SUM(total) > 1000
ORDER BY total_spent DESC;


-- Query 2 Expected rows [(101, 'Ahmed', 2), (102, 'Sara', 2), (103, 'Omar', 1), (104, 'Mona', 0), (105, 'Ali', 3)]
SELECT c.id, c.name, COUNT(o.id) AS paid_orders
FROM customers c
LEFT JOIN orders o
    ON o.customer_id = c.id
   AND o.status = 'paid'
GROUP BY c.id, c.name;


-- Query 3 Expected rows [(12000,)]
SELECT MAX(salary) AS second_highest_salary
FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);


-- Query 4 Expected rows [(12000,)]
SELECT DISTINCT salary
FROM employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;


-- Query 5 Expected rows [('Sara', 10, 15000), ('Omar', 20, 12000), ('Mona', 20, 12000)]
SELECT e.name, e.department_id, e.salary
FROM employees e
WHERE e.salary = (
    SELECT MAX(other.salary)
    FROM employees other
    WHERE other.department_id = e.department_id
);


-- Query 6 Expected rows [('Sara', 10, 15000), ('Omar', 20, 12000), ('Mona', 20, 12000)]
SELECT e.name, e.department_id, e.salary
FROM employees e
JOIN (
    SELECT department_id, AVG(salary) AS avg_salary
    FROM employees
    GROUP BY department_id
) averages ON averages.department_id = e.department_id
WHERE e.salary > averages.avg_salary;


-- Query 7 Expected rows [(3, 101, '2026-11-17 11:00:00+00', 700), (5, 102, '2026-11-17 11:00:00+00', 200), (6, 103, '2026-11-17 11:00:00+00', 400), (9, 105, '2026-11-17 11:00:00+00', 300)]
SELECT id, customer_id, created_at, total
FROM (
    SELECT o.*,
           ROW_NUMBER() OVER (
               PARTITION BY customer_id
               ORDER BY created_at DESC, id DESC
           ) AS rn
    FROM orders o
) ranked
WHERE rn = 1;


-- Query 8 Expected rows [(3, 101, 700, 'paid', '2026-11-17 11:00:00+00'), (5, 102, 200, 'paid', '2026-11-17 11:00:00+00'), (6, 103, 400, 'paid', '2026-11-17 11:00:00+00'), (9, 105, 300, 'paid', '2026-11-17 11:00:00+00')]
SELECT o.*
FROM orders o
WHERE NOT EXISTS (
    SELECT 1
    FROM orders other
    WHERE other.customer_id = o.customer_id
      AND (
          other.created_at > o.created_at
          OR (other.created_at = o.created_at
              AND other.id > o.id)
      )
);


-- Query 9 Expected rows [('ali@example.com', 2)]
SELECT email, COUNT(*) AS occurrences
FROM users
GROUP BY email
HAVING COUNT(*) > 1;


-- Query 10 Expected rows [(105,)]
SELECT customer_id
FROM orders
GROUP BY customer_id
HAVING COUNT(*) >= 3
   AND MIN(total) > 100;


-- Query 11 Expected rows [(8, 200, '2026-11-17 11:00:00+00'), (7, 150, '2026-11-17 10:00:00+00')]
SELECT id, total, created_at
FROM orders
WHERE customer_id = 105
  AND (created_at, id) < ('2026-11-17 11:00:00+00', 9)
ORDER BY created_at DESC, id DESC
LIMIT 20;


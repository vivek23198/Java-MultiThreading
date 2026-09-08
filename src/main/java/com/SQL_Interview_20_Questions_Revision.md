# SQL Interview Revision — 20 Questions

**MySQL 8.0 • Interview-ready syntax • Patterns and quick memory notes**

---

## Q1. Find the 2nd highest salary

```sql
SELECT MAX(salary) AS second_highest_salary
FROM employees
WHERE salary < (
    SELECT MAX(salary)
    FROM employees
);
```

**Pattern:** Find the maximum salary below the overall maximum.

---

## Q2. Find the Nth highest distinct salary

```sql
SELECT salary
FROM (
    SELECT
        salary,
        DENSE_RANK() OVER (
            ORDER BY salary DESC
        ) AS rnk
    FROM employees
) v
WHERE rnk = 3;
```

**Pattern:** `DENSE_RANK()` is useful for distinct salary ranking.

---

## Q3. Find the highest salary in each department

```sql
SELECT
    department_id,
    MAX(salary) AS highest_salary
FROM employees
GROUP BY department_id;
```

**Pattern:** `GROUP BY department + MAX()`.

---

## Q4. Find the top 3 employees in each department

```sql
SELECT
    employee_id,
    first_name,
    department_id,
    salary,
    rnk
FROM (
    SELECT
        employee_id,
        first_name,
        department_id,
        salary,
        DENSE_RANK() OVER (
            PARTITION BY department_id
            ORDER BY salary DESC
        ) AS rnk
    FROM employees
) v
WHERE rnk <= 3;
```

**Pattern:** Top N per group = ranking function + `PARTITION BY`.

---

## Q5. Employees earning more than their department average

```sql
WITH dept_avg AS (
    SELECT
        department_id,
        AVG(salary) AS avg_salary
    FROM employees
    GROUP BY department_id
)
SELECT
    e.first_name,
    e.salary,
    e.department_id
FROM employees e
INNER JOIN dept_avg d
    ON e.department_id = d.department_id
WHERE e.salary > d.avg_salary;
```

**Pattern:** Aggregate per group in a CTE, then join it back to the original rows.

---

## Q6. Find duplicate emails

```sql
SELECT
    email,
    COUNT(*) AS email_count
FROM employees
GROUP BY email
HAVING COUNT(*) > 1;
```

**Pattern:** `GROUP BY` duplicate column + `HAVING COUNT(*) > 1`.

**Remember:** `WHERE` filters rows; `HAVING` filters groups/aggregates.

---

## Q7. Delete duplicate records but keep one

```sql
DELETE FROM employees
WHERE employee_id IN (
    SELECT employee_id
    FROM (
        SELECT
            employee_id,
            ROW_NUMBER() OVER (
                PARTITION BY email
                ORDER BY employee_id
            ) AS rnk
        FROM employees
    ) v
    WHERE rnk > 1
);
```

**Pattern:** `rnk = 1` → KEEP; `rnk > 1` → DELETE.

> Always test the inner SELECT before executing DELETE.

---

## Q8. Customers who never placed an order

```sql
SELECT
    c.customer_name
FROM customers c
LEFT JOIN orders o
    ON c.customer_id = o.customer_id
WHERE o.customer_id IS NULL;
```

**Pattern:** `LEFT JOIN + B.key IS NULL` finds records in A with no matching record in B.

---

## Q9. Products that were never sold

```sql
SELECT
    p.product_name
FROM products p
LEFT JOIN order_items oi
    ON p.product_id = oi.product_id
WHERE oi.product_id IS NULL;
```

**Pattern:** Same anti-join pattern as Q8.

---

## Q10. Employee and manager name — SELF JOIN

```sql
SELECT
    e.first_name AS employee_name,
    m.first_name AS manager_name
FROM employees e
INNER JOIN employees m
    ON e.manager_id = m.employee_id;
```

**Pattern:** `employee.manager_id → manager.employee_id`.

---

## Q11. Department with the highest total salary

```sql
WITH dept_salary AS (
    SELECT
        d.department_id,
        d.department_name,
        SUM(e.salary) AS total_salary
    FROM employees e
    INNER JOIN departments d
        ON e.department_id = d.department_id
    GROUP BY
        d.department_id,
        d.department_name
)
SELECT
    department_name,
    total_salary
FROM dept_salary
ORDER BY total_salary DESC
LIMIT 1;
```

**Pattern:** `GROUP BY → SUM → ORDER BY DESC → LIMIT 1`.

---

## Q12. Highest-paid employee(s) in each department

```sql
SELECT
    v.first_name,
    d.department_name,
    v.salary
FROM (
    SELECT
        first_name,
        department_id,
        salary,
        DENSE_RANK() OVER (
            PARTITION BY department_id
            ORDER BY salary DESC
        ) AS rnk
    FROM employees
) v
INNER JOIN departments d
    ON v.department_id = d.department_id
WHERE v.rnk = 1;
```

**Pattern:** `DENSE_RANK()` partitioned by department, ordered by salary descending.

---

## Q13. Running total of sales

```sql
SELECT
    sale_date,
    amount,
    SUM(amount) OVER (
        ORDER BY sale_date
    ) AS running_total
FROM sales;
```

**Pattern:** Running total = `SUM() OVER (ORDER BY date)`.

For a running total per customer:

```sql
SUM(amount) OVER (
    PARTITION BY customer_id
    ORDER BY sale_date
)
```

---

## Q14. Previous and next salary

```sql
SELECT
    first_name,
    salary,
    LAG(salary) OVER (
        ORDER BY salary
    ) AS previous_salary,
    LEAD(salary) OVER (
        ORDER BY salary
    ) AS next_salary
FROM employees;
```

**Pattern:** `LAG` → previous row; `LEAD` → next row.

---

## Q15. 3-day moving average

```sql
SELECT
    sale_date,
    amount,
    AVG(amount) OVER (
        ORDER BY sale_date
        ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
    ) AS moving_avg
FROM sales;
```

**Pattern:** `2 PRECEDING + CURRENT ROW = 3 rows`.

---

## Q16. Monthly revenue

```sql
SELECT
    DATE_FORMAT(sale_date, '%Y-%m') AS month,
    SUM(amount) AS revenue
FROM sales
GROUP BY DATE_FORMAT(sale_date, '%Y-%m');
```

**Pattern:** Convert date to year-month → `GROUP BY` → `SUM`.

> Use `%Y-%m`, not just `%m`, when multiple years may exist.

---

## Q17. Year-over-Year revenue

```sql
WITH monthly_sales AS (
    SELECT
        DATE_FORMAT(sale_date, '%Y-%m') AS month,
        SUM(amount) AS revenue
    FROM sales
    GROUP BY DATE_FORMAT(sale_date, '%Y-%m')
)
SELECT
    month,
    revenue,
    LAG(revenue, 12) OVER (
        ORDER BY month
    ) AS previous_year_revenue
FROM monthly_sales;
```

**Pattern:** Monthly aggregate first, then `LAG` 12 rows.

> This assumes one row exists for every month. If months are missing, use a calendar/month table for a robust solution.

### YoY growth

```text
(current revenue - previous year revenue)
------------------------------------------ × 100
       previous year revenue
```

---

## Q18. Customers ordering in every one of the last 3 complete calendar months

```sql
SELECT
    customer_id
FROM orders
WHERE order_date >= DATE_FORMAT(
          DATE_SUB(CURDATE(), INTERVAL 3 MONTH),
          '%Y-%m-01'
      )
  AND order_date < DATE_FORMAT(
          CURDATE(),
          '%Y-%m-01'
      )
GROUP BY customer_id
HAVING COUNT(
    DISTINCT DATE_FORMAT(order_date, '%Y-%m')
) = 3;
```

**Pattern:** Filter date range → GROUP BY customer → COUNT(DISTINCT month) → HAVING = required months.

> This means the previous 3 complete calendar months. Clarify the interpretation of “last 3 months” in an interview.

---

## Q19. Find the median salary

```sql
WITH ranked AS (
    SELECT
        salary,
        ROW_NUMBER() OVER (
            ORDER BY salary
        ) AS rn,
        COUNT(*) OVER () AS total
    FROM employees
)
SELECT
    AVG(salary) AS median_salary
FROM ranked
WHERE rn IN (
    FLOOR((total + 1) / 2),
    CEIL((total + 1) / 2)
);
```

**Pattern:** Sort → row number → total count → middle row(s) → AVG.

- Odd count → one middle row
- Even count → two middle rows

---

## Q20. Employee hierarchy using Recursive CTE

```sql
WITH RECURSIVE hierarchy AS (

    -- Anchor: top-level employees
    SELECT
        employee_id,
        first_name,
        manager_id,
        0 AS level
    FROM employees
    WHERE manager_id IS NULL

    UNION ALL

    -- Recursive part: find employees below each level
    SELECT
        e.employee_id,
        e.first_name,
        e.manager_id,
        h.level + 1
    FROM employees e
    INNER JOIN hierarchy h
        ON e.manager_id = h.employee_id
)
SELECT
    employee_id,
    first_name,
    manager_id,
    level
FROM hierarchy
ORDER BY level, employee_id;
```

**Pattern:** Recursive CTE = anchor/root + `UNION ALL` + recursive self-join.

---

# Last-Minute SQL Cheat Sheet

| Concept | Remember |
|---|---|
| WHERE | Filters rows |
| HAVING | Filters groups/aggregates |
| GROUP BY | One result per group |
| LEFT JOIN + IS NULL | Find A with no matching B |
| SELF JOIN | Same table joined to itself |
| ROW_NUMBER | Unique sequential number |
| RANK | Ties share rank; gaps occur |
| DENSE_RANK | Ties share rank; no gaps |
| PARTITION BY | Restarts window calculation per group |
| Running total | `SUM(amount) OVER (ORDER BY date)` |
| Moving average | `AVG(...) OVER (... ROWS BETWEEN ...)` |
| LAG | Previous row |
| LEAD | Next row |
| CTE | Common Table Expression |
| Recursive CTE | Hierarchical/tree data |
| Duplicates | `GROUP BY ... HAVING COUNT(*) > 1` |
| Top N per group | Ranking + `PARTITION BY` |
| Median | Middle position(s) + `AVG()` |
| Monthly revenue | `DATE_FORMAT()` + `GROUP BY` |
| YoY | Monthly aggregate + `LAG()` |

---

# CTE Full Form

**CTE = Common Table Expression**

Basic syntax:

```sql
WITH cte_name AS (
    SELECT ...
)
SELECT *
FROM cte_name;
```

A CTE is a named temporary result set available for the duration of a single SQL statement.

---

# Most Important Interview Patterns

### 1. Duplicate records

```sql
GROUP BY email
HAVING COUNT(*) > 1
```

### 2. No matching records

```sql
LEFT JOIN ...
WHERE right_table.id IS NULL
```

### 3. Top N per group

```sql
DENSE_RANK() OVER (
    PARTITION BY department_id
    ORDER BY salary DESC
)
```

### 4. Running total

```sql
SUM(amount) OVER (
    ORDER BY sale_date
)
```

### 5. Previous / next row

```sql
LAG(value)  OVER (ORDER BY ...)
LEAD(value) OVER (ORDER BY ...)
```

### 6. Moving average

```sql
AVG(value) OVER (
    ORDER BY date
    ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
)
```

### 7. Hierarchy

```sql
WITH RECURSIVE hierarchy AS (
    -- anchor
    ...
    UNION ALL
    -- recursive
    ...
)
```

---

# Final Revision Strategy

```text
Q1–Q6
Aggregation + GROUP BY + HAVING + Ranking

Q7–Q12
DELETE duplicates + JOINs + SELF JOIN + Top-per-group

Q13–Q17
Window functions + Running Total + LAG/LEAD + Dates

Q18–Q20
Advanced GROUP BY + Median + Recursive CTE
```

**Goal:** Don't memorize entire queries. Memorize the pattern, then build the query from the pattern.

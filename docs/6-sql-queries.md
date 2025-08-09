# 6. Sql Queries

Utilizei sintaxe do PostgreSQL

a. Returns the names of all Salesperson that don’t have any order with Samsonic.

```sql
SELECT Name
FROM Salesperson
WHERE ID NOT IN (
    SELECT o.salesperson_id
    FROM Orders o
             JOIN Customer c ON o.customer_id = c.ID
    WHERE c.Name = 'Samsonic'
);
```
b. Updates the names of Salesperson that have 2 or more orders. It’s necessary to add an ‘*’ in the end of the name.
```sql
UPDATE Salesperson
SET Name = Name || '*'
WHERE ID IN (
    SELECT salesperson_id
    FROM Orders
    GROUP BY salesperson_id
    HAVING COUNT(*) >= 2
);
```
c. Deletes all Ssalesperson that placed orders to the city of Jackson.
```sql
DELETE FROM Salesperson
WHERE ID IN (
    SELECT DISTINCT o.salesperson_id
    FROM Orders o
             JOIN Customer c ON o.customer_id = c.ID
    WHERE c.City = 'Jackson'
);
```
d. The total sales amount for each Salesperson. If the salesperson hasn’t sold anything, show zero.
```sql
SELECT s.Name, COALESCE(SUM(o.Amount), 0) AS "Valor Total de Vendas"
FROM Salesperson s
         LEFT JOIN Orders o ON s.ID = o.salesperson_id
GROUP BY s.ID, s.Name
ORDER BY s.Name;
```

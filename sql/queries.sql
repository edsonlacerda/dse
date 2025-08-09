SELECT Name
FROM Salesperson
WHERE ID NOT IN (
    SELECT o.salesperson_id
    FROM Orders o
             JOIN Customer c ON o.customer_id = c.ID
    WHERE c.Name = 'Samsonic'
);

UPDATE Salesperson
SET Name = Name || '*'
WHERE ID IN (
    SELECT salesperson_id
    FROM Orders
    GROUP BY salesperson_id
    HAVING COUNT(*) >= 2
);

DELETE FROM Salesperson
WHERE ID IN (
    SELECT DISTINCT o.salesperson_id
    FROM Orders o
             JOIN Customer c ON o.customer_id = c.ID
    WHERE c.City = 'Jackson'
);

SELECT s.Name, COALESCE(SUM(o.Amount), 0) AS "Valor Total de Vendas"
FROM Salesperson s
         LEFT JOIN Orders o ON s.ID = o.salesperson_id
GROUP BY s.ID, s.Name
ORDER BY s.Name;-- Respostas SQL do teste

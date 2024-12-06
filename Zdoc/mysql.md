# in和exist
IN适用于检查某个值是否在一组值中出现，适合处理固定的小范围数据；
而EXISTS用于判断子查询是否能返回至少一行数据，适合处理大数据集或需要优化性能的场景。通常，EXISTS在处理较大的数据集时具有更好的性能，因为它会在找到匹配项后立即停止查询，而IN则会扫描整个子查询的结果集。

假设我们有两个表：orders 和 customers，我们想要找到所有有订单的客户。
```sql
SELECT customer_id, customer_name 
FROM customers 
WHERE customer_id IN (SELECT customer_id FROM orders);
```

使用了exists
```sql
SELECT customer_id, customer_name 
FROM customers 
WHERE EXISTS (SELECT 1 FROM orders WHERE orders.customer_id = customers.customer_id);
```
这两个sql最终查询的结果都是一样的


- 存在性测试：
EXISTS 用于测试子查询是否返回至少一行数据。它不关心返回的数据是什么，只关心是否存在满足条件的记录。

- 性能优势
由于 EXISTS 子查询不需要返回具体列，它在匹配到第一个符合条件的行时，就会停止执行子查询以返回 TRUE，这常常比需要完整读取所有结果的 IN 更快。

- 返回值不重要：
在 EXISTS 子查询中，返回值可以是任意值（如常数 1），因为 EXISTS 子句只需要知道子查询是否能找到任何记录，因此只要有行返回，结果都是满足的。

- 关联子查询：
在使用 EXISTS 时，尤其在关联多个表的情况下，它可以利用外查询中的表，将其连接条件在子查询中使用（如 orders.customer_id = customers.customer_id），从表的行视角来判断某条件的存在性

关联条件：子查询 SELECT 1 FROM orders WHERE orders.customer_id = customers.customer_id 将 orders 表中的每一行与 customers 表当前行上的 customer_id 进行匹配。
返回控制：如果 orders 中存在一行与外层查询中的 customer_id 匹配，则 EXISTS 子查询返回 TRUE，外层的 WHERE 子句返回符合条件的 customer_id。
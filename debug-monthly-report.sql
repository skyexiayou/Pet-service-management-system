-- 调试月报查询问题

-- 1. 查看视图中所有数据
SELECT * FROM v_store_monthly_report;

-- 2. 查看用户"王哲贤"的信息
SELECT * FROM `user` WHERE UserName = '王哲贤';

-- 3. 查看员工"王哲贤"的信息
SELECT * FROM employee WHERE EmpName = '王哲贤';

-- 4. 查看门店1的2025-12月数据
SELECT * FROM v_store_monthly_report 
WHERE StoreID = 1 AND StatMonth = '2025-12';

-- 5. 查看所有门店的统计月份
SELECT DISTINCT StoreID, StatMonth FROM v_store_monthly_report ORDER BY StoreID, StatMonth;

-- 6. 检查是否有2025-12月的已支付订单
SELECT 
    a.StoreID,
    DATE_FORMAT(o.OrderCreateTime, '%Y-%m') as OrderMonth,
    COUNT(*) as OrderCount
FROM `order` o
INNER JOIN appointment a ON o.ApptID = a.ApptID
WHERE o.PayStatus = '已支付'
  AND DATE_FORMAT(o.OrderCreateTime, '%Y-%m') = '2025-12'
GROUP BY a.StoreID, DATE_FORMAT(o.OrderCreateTime, '%Y-%m');

-- 7. 查看所有已支付订单的月份分布
SELECT 
    a.StoreID,
    s.StoreName,
    DATE_FORMAT(o.OrderCreateTime, '%Y-%m') as OrderMonth,
    COUNT(*) as OrderCount
FROM `order` o
INNER JOIN appointment a ON o.ApptID = a.ApptID
INNER JOIN store s ON a.StoreID = s.StoreID
WHERE o.PayStatus = '已支付'
GROUP BY a.StoreID, s.StoreName, DATE_FORMAT(o.OrderCreateTime, '%Y-%m')
ORDER BY a.StoreID, OrderMonth;

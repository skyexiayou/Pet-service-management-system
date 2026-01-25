-- 门店月报功能快速修复脚本
-- 创建时间：2025-12-18
-- 说明：一键修复月报功能的所有问题

-- ============================================
-- 步骤1：删除旧视图
-- ============================================
DROP VIEW IF EXISTS v_store_monthly_report;

-- ============================================
-- 步骤2：创建新视图（修正版）
-- ============================================
CREATE VIEW v_store_monthly_report AS
SELECT 
    s.StoreID,
    s.StoreName,
    DATE_FORMAT(o.OrderCreateTime, '%Y-%m') AS StatMonth,
    
    -- 新增客户数：统计该月份内注册且在本门店有订单的客户
    COUNT(DISTINCT CASE 
        WHEN DATE_FORMAT(u.RegisterTime, '%Y-%m') = DATE_FORMAT(o.OrderCreateTime, '%Y-%m') 
        THEN u.UserID 
    END) AS NewUserCount,
    
    -- 总订单数
    COUNT(DISTINCT o.OrderID) AS TotalOrderCount,
    
    -- 美容服务订单数
    COUNT(DISTINCT CASE WHEN ab.ApptID IS NOT NULL THEN o.OrderID END) AS BeautyOrderCount,
    
    -- 寄养服务订单数
    COUNT(DISTINCT CASE WHEN af.ApptID IS NOT NULL THEN o.OrderID END) AS FosterOrderCount,
    
    -- 医疗服务订单数
    COUNT(DISTINCT CASE WHEN am.ApptID IS NOT NULL THEN o.OrderID END) AS MedicalOrderCount,
    
    -- 用品销售额
    IFNULL(SUM(op.Subtotal), 0) AS ProductSales,
    
    -- 美容营收
    IFNULL(SUM(CASE WHEN ab.BeautyID IS NOT NULL THEN b.Price ELSE 0 END), 0) AS BeautyRevenue,
    
    -- 寄养营收
    IFNULL(SUM(CASE WHEN af.FosterID IS NOT NULL THEN fr.FosterFee ELSE 0 END), 0) AS FosterRevenue,
    
    -- 医疗营收
    IFNULL(SUM(CASE WHEN am.MedicalID IS NOT NULL THEN mr.MedicalFee ELSE 0 END), 0) AS MedicalRevenue,
    
    -- 总营收
    IFNULL(SUM(op.Subtotal), 0) + 
    IFNULL(SUM(CASE WHEN ab.BeautyID IS NOT NULL THEN b.Price ELSE 0 END), 0) + 
    IFNULL(SUM(CASE WHEN af.FosterID IS NOT NULL THEN fr.FosterFee ELSE 0 END), 0) + 
    IFNULL(SUM(CASE WHEN am.MedicalID IS NOT NULL THEN mr.MedicalFee ELSE 0 END), 0) AS TotalRevenue,
    
    -- 热门服务类型：订单量最多的服务类型
    CASE 
        WHEN COUNT(DISTINCT CASE WHEN ab.ApptID IS NOT NULL THEN o.OrderID END) >= 
             COUNT(DISTINCT CASE WHEN af.ApptID IS NOT NULL THEN o.OrderID END) 
             AND COUNT(DISTINCT CASE WHEN ab.ApptID IS NOT NULL THEN o.OrderID END) >= 
             COUNT(DISTINCT CASE WHEN am.ApptID IS NOT NULL THEN o.OrderID END) 
        THEN '美容'
        WHEN COUNT(DISTINCT CASE WHEN af.ApptID IS NOT NULL THEN o.OrderID END) >= 
             COUNT(DISTINCT CASE WHEN am.ApptID IS NOT NULL THEN o.OrderID END) 
        THEN '寄养'
        ELSE '医疗'
    END AS HotServiceType
    
FROM `order` o
INNER JOIN appointment appt ON o.ApptID = appt.ApptID
INNER JOIN store s ON appt.StoreID = s.StoreID
INNER JOIN `user` u ON appt.UserID = u.UserID
LEFT JOIN orderProduct op ON o.OrderID = op.OrderID
LEFT JOIN apptBeauty ab ON appt.ApptID = ab.ApptID
LEFT JOIN beauty b ON ab.BeautyID = b.BeautyID
LEFT JOIN apptFoster af ON appt.ApptID = af.ApptID
LEFT JOIN fosterRecord fr ON af.FosterID = fr.FosterID
LEFT JOIN apptMedical am ON appt.ApptID = am.ApptID
LEFT JOIN medicalRecord mr ON am.MedicalID = mr.MedicalID
WHERE o.PayStatus = '已支付'
GROUP BY s.StoreID, s.StoreName, DATE_FORMAT(o.OrderCreateTime, '%Y-%m');

-- ============================================
-- 步骤3：验证视图创建成功
-- ============================================
SELECT '视图创建成功！' as Status;
SELECT '查看视图结构：' as Info;
DESCRIBE v_store_monthly_report;

-- ============================================
-- 步骤4：查看现有数据
-- ============================================
SELECT '查看视图中的所有数据：' as Info;
SELECT * FROM v_store_monthly_report ORDER BY StatMonth DESC LIMIT 10;

-- ============================================
-- 步骤5：检查是否需要插入测试数据
-- ============================================
SELECT '检查2025-12月是否有数据：' as Info;
SELECT 
    CASE 
        WHEN COUNT(*) > 0 THEN '已有数据，无需插入测试数据'
        ELSE '无数据，建议执行：SOURCE docs/月报测试数据-修正版.sql;'
    END as Suggestion
FROM v_store_monthly_report 
WHERE StatMonth = '2025-12';

-- ============================================
-- 完成提示
-- ============================================
SELECT '========================================' as '';
SELECT '月报功能修复完成！' as '';
SELECT '========================================' as '';
SELECT '下一步操作：' as '';
SELECT '1. 如果没有测试数据，执行：SOURCE docs/月报测试数据-修正版.sql;' as '';
SELECT '2. 启动项目：mvn spring-boot:run' as '';
SELECT '3. 访问API文档：http://localhost:8081/doc.html' as '';
SELECT '4. 测试月报接口：GET /api/admin/appointments/monthly-report' as '';
SELECT '========================================' as '';


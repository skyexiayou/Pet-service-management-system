-- 门店月报视图创建SQL
-- 创建时间：2025-12-18
-- 说明：统计门店每月的运营数据，包括订单量、营收、新增客户等

CREATE OR REPLACE VIEW v_store_monthly_report AS
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

-- 插入2025-12月的测试数据用于月报查询

-- 1. 确保有测试用户(如果没有)
INSERT IGNORE INTO `user` (UserID, UserName, Phone, Address, Email, RegisterTime) 
VALUES (100, '测试客户12月', '13800001201', '北京市朝阳区', 'test12@example.com', '2025-12-01 10:00:00');

-- 2. 确保有测试宠物(如果没有)
INSERT IGNORE INTO pet (PetID, UserID, PetName, PetType, Breed, Age, Gender)
VALUES (100, 100, '旺财12月', '狗', '金毛', 2, '公');

-- 3. 插入美容服务预约和订单(2025-12月)
INSERT INTO appointment (UserID, PetID, StoreID, ApptTime, ApptStatus, CreateTime)
VALUES (100, 100, 1, '2025-12-20 10:00:00', '已完成', '2025-12-15 09:00:00');

SET @appt_id_1 = LAST_INSERT_ID();

-- 假设BeautyID=1存在
INSERT INTO apptBeauty (ApptID, BeautyID)
VALUES (@appt_id_1, 1);

INSERT INTO `order` (ApptID, TotalAmount, PayStatus, PayMethod, PayTime, OrderCreateTime)
VALUES (@appt_id_1, 80.00, '已支付', 'WECHAT', '2025-12-15 10:00:00', '2025-12-15 09:30:00');

-- 4. 插入寄养服务预约和订单(2025-12月)
INSERT INTO appointment (UserID, PetID, StoreID, ApptTime, ApptStatus, CreateTime)
VALUES (100, 100, 1, '2025-12-18 14:00:00', '已完成', '2025-12-12 10:00:00');

SET @appt_id_2 = LAST_INSERT_ID();

-- 假设BoardingID=1存在
INSERT INTO apptFoster (ApptID, BoardingID, StartDate, EndDate)
VALUES (@appt_id_2, 1, '2025-12-18', '2025-12-25');

SET @foster_id = LAST_INSERT_ID();

INSERT INTO fosterRecord (FosterID, FosterFee, DailyStatus)
VALUES (@foster_id, 700.00, '2025-12-18：入住正常');

INSERT INTO `order` (ApptID, TotalAmount, PayStatus, PayMethod, PayTime, OrderCreateTime)
VALUES (@appt_id_2, 700.00, '已支付', 'ALIPAY', '2025-12-12 11:00:00', '2025-12-12 10:30:00');

-- 5. 插入医疗服务预约和订单(2025-12月)
INSERT INTO appointment (UserID, PetID, StoreID, ApptTime, ApptStatus, CreateTime)
VALUES (100, 100, 1, '2025-12-22 15:00:00', '已完成', '2025-12-16 14:00:00');

SET @appt_id_3 = LAST_INSERT_ID();

-- 假设MedicalID=1存在
INSERT INTO apptMedical (ApptID, MedicalID)
VALUES (@appt_id_3, 1);

SET @medical_id = LAST_INSERT_ID();

INSERT INTO medicalRecord (MedicalID, Diagnosis, Medication, MedicalFee, FollowUpAdvice)
VALUES (@medical_id, '健康状况良好', '无', 150.00, '半年后复查');

INSERT INTO `order` (ApptID, TotalAmount, PayStatus, PayMethod, PayTime, OrderCreateTime)
VALUES (@appt_id_3, 150.00, '已支付', 'WECHAT', '2025-12-16 15:00:00', '2025-12-16 14:30:00');

-- 6. 验证数据是否插入成功
SELECT '=== 2025-12月订单数据 ===' as Info;
SELECT 
    a.StoreID,
    DATE_FORMAT(o.OrderCreateTime, '%Y-%m') as OrderMonth,
    COUNT(*) as OrderCount,
    SUM(o.TotalAmount) as TotalAmount
FROM `order` o
INNER JOIN appointment a ON o.ApptID = a.ApptID
WHERE o.PayStatus = '已支付'
  AND DATE_FORMAT(o.OrderCreateTime, '%Y-%m') = '2025-12'
GROUP BY a.StoreID, DATE_FORMAT(o.OrderCreateTime, '%Y-%m');

-- 7. 查看视图中的2025-12月数据
SELECT '=== 视图中的2025-12月数据 ===' as Info;
SELECT * FROM v_store_monthly_report WHERE StatMonth = '2025-12';

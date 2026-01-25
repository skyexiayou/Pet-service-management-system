-- 请假记录测试数据
-- 执行前请确保employee表和store表中有数据

-- 查看现有员工
SELECT EmpID, EmpName, StoreID FROM employee LIMIT 10;

-- 查看现有门店
SELECT StoreID, StoreName FROM store LIMIT 10;

-- 插入测试请假记录（请根据实际的EmpID和StoreID修改）
-- 假设EmpID=1, StoreID=1存在

INSERT INTO leaveRecord (EmpID, StoreID, LeaveType, StartTime, EndTime, ApplyTime, ApproveStatus, LeaveReason)
VALUES 
(1, 1, '病假', '2025-12-20 09:00:00', '2025-12-21 18:00:00', NOW(), '待审批', '身体不适，需要休息'),
(1, 1, '事假', '2025-12-25 09:00:00', '2025-12-25 18:00:00', NOW(), '待审批', '家中有事需要处理'),
(1, 1, '年假', '2025-12-30 09:00:00', '2026-01-02 18:00:00', NOW(), '已通过', '元旦假期');

-- 如果有多个员工，可以添加更多记录
-- INSERT INTO leaveRecord (EmpID, StoreID, LeaveType, StartTime, EndTime, ApplyTime, ApproveStatus, LeaveReason)
-- VALUES (2, 1, '病假', '2025-12-22 09:00:00', '2025-12-23 18:00:00', NOW(), '待审批', '感冒发烧');

-- 验证插入结果
SELECT * FROM leaveRecord;

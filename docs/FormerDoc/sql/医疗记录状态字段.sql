-- 为医疗记录表添加状态字段
-- 执行此SQL为medicalRecord表添加Status字段

ALTER TABLE `medicalRecord` 
ADD COLUMN `Status` VARCHAR(20) NOT NULL DEFAULT '待就诊' 
COMMENT '状态（待就诊/已完成/已取消）' 
AFTER `FollowUpAdvice`;

-- 更新现有记录的状态为"已完成"（假设现有记录都是已完成的）
UPDATE `medicalRecord` SET `Status` = '已完成' WHERE `Status` = '待就诊';

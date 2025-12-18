-- 为用户表添加认证相关字段
-- 执行此脚本前请备份数据库

-- 添加账号字段（唯一）
ALTER TABLE `user` ADD COLUMN `Account` VARCHAR(20) NOT NULL COMMENT '账号：首字符C + 5-15位数字/字母' AFTER `UserID`;

-- 添加密码字段
ALTER TABLE `user` ADD COLUMN `Password` VARCHAR(100) NOT NULL COMMENT '加密后的密码' AFTER `Email`;

-- 添加管理员标识字段
ALTER TABLE `user` ADD COLUMN `IsAdmin` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否为管理员：0-普通用户，1-管理员' AFTER `Password`;

-- 添加封禁状态字段
ALTER TABLE `user` ADD COLUMN `IsBanned` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否被封禁：0-正常，1-已封禁' AFTER `IsAdmin`;

-- 为已存在的用户数据生成默认账号和密码（如果表中已有数据）
-- 注意：这里使用手机号后6位作为临时账号，密码为123456的BCrypt加密值
-- 实际使用时应该让用户重新设置密码
UPDATE `user` SET 
    `Account` = CONCAT('C', LPAD(UserID, 6, '0')),
    `Password` = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'  -- 123456的BCrypt加密值
WHERE `Account` IS NULL OR `Account` = '';

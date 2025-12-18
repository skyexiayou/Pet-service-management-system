-- 1. 用户表（User）- 1/17
CREATE TABLE `user` (
                        `UserID` INT(11) NOT NULL AUTO_INCREMENT,
                        `UserName` VARCHAR(50) NOT NULL COMMENT '主人姓名',
                        `Phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
                        `Address` VARCHAR(200) DEFAULT NULL COMMENT '居住地址',
                        `RegisterTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                        `Email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
                        PRIMARY KEY (`UserID`),
                        UNIQUE KEY `UK_User_Phone` (`Phone`),
                        UNIQUE KEY `UK_User_Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '客户信息表（1:N关联宠物）';

-- 2. 宠物表（Pet）- 2/17
CREATE TABLE `pet` (
                       `PetID` INT(11) NOT NULL AUTO_INCREMENT,
                       `UserID` INT(11) NOT NULL COMMENT '所属客户ID（关联User）',
                       `PetName` VARCHAR(50) NOT NULL COMMENT '宠物名',
                       `Breed` VARCHAR(50) DEFAULT NULL COMMENT '品种（如金毛/布偶）',
                       `Gender` CHAR(1) DEFAULT NULL CHECK (Gender IN ('M', 'F', 'U')) COMMENT '性别（M=男/F=女/U=未知）',
                       `BirthDate` DATE DEFAULT NULL COMMENT '出生日期',
                       `VaccineStatus` VARCHAR(200) DEFAULT NULL COMMENT '疫苗接种情况',
                       `Remarks` TEXT DEFAULT NULL COMMENT '备注（如过敏史）',
                       PRIMARY KEY (`PetID`),
                       CONSTRAINT `FK_Pet_User` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '宠物信息表（1:N关联预约/寄养/医疗）';

-- 3. 城市表（City）- 3/17
CREATE TABLE `city` (
                        `CityID` INT(11) NOT NULL AUTO_INCREMENT,
                        `CityName` VARCHAR(50) NOT NULL COMMENT '城市名（如北京）',
                        `Province` VARCHAR(50) NOT NULL COMMENT '省份（如北京市）',
                        `ZipCode` VARCHAR(6) DEFAULT NULL COMMENT '邮政编码（如100000）',
                        PRIMARY KEY (`CityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '城市信息表（1:N关联门店）';

-- 4. 门店表（Store）- 4/17
CREATE TABLE `store` (
                         `StoreID` INT(11) NOT NULL AUTO_INCREMENT,
                         `CityID` INT(11) NOT NULL COMMENT '所属城市ID（关联City）',
                         `StoreName` VARCHAR(100) NOT NULL COMMENT '门店名',
                         `StoreAddress` VARCHAR(200) NOT NULL COMMENT '门店地址',
                         `StorePhone` VARCHAR(20) NOT NULL COMMENT '门店电话',
                         `BusinessHours` VARCHAR(50) DEFAULT NULL COMMENT '营业时间（如09:00-21:00）',
                         PRIMARY KEY (`StoreID`),
                         UNIQUE KEY `UK_Store_Phone` (`StorePhone`),
                         CONSTRAINT `FK_Store_City` FOREIGN KEY (`CityID`) REFERENCES `City` (`CityID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '门店信息表（1:N关联员工/预约）';

-- 5. 员工表（Employee）- 5/17
CREATE TABLE `employee` (
                            `EmpID` INT(11) NOT NULL AUTO_INCREMENT,
                            `StoreID` INT(11) NOT NULL COMMENT '所属门店ID（关联Store）',
                            `EmpName` VARCHAR(50) NOT NULL COMMENT '员工姓名',
                            `Position` VARCHAR(50) NOT NULL COMMENT '岗位（医生/美容师）',
                            `EmpPhone` VARCHAR(20) NOT NULL COMMENT '员工电话',
                            `EntryTime` DATE DEFAULT NULL COMMENT '入职时间',
                            `Salary` DECIMAL(10,2) DEFAULT NULL COMMENT '薪资',
                            PRIMARY KEY (`EmpID`),
                            UNIQUE KEY `UK_Employee_Phone` (`EmpPhone`),
                            CONSTRAINT `FK_Employee_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工信息表（1:N关联预约/寄养/医疗）';

-- 6. 美容项目表（Beauty）- 6/17
CREATE TABLE `beauty` (
                          `BeautyID` INT(11) NOT NULL AUTO_INCREMENT,
                          `BeautyName` VARCHAR(100) NOT NULL COMMENT '服务名（如洗澡/修甲）',
                          `BeautyType` VARCHAR(50) NOT NULL COMMENT '类型（美容/医疗）',
                          `Price` DECIMAL(8,2) NOT NULL COMMENT '单价',
                          `Duration` INT(11) DEFAULT NULL COMMENT '时长（单位：分钟）',
                          `BeautyRemarks` TEXT DEFAULT NULL COMMENT '服务说明',
                          PRIMARY KEY (`BeautyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '美容项目表（M:N关联预约）';

-- 7. 预约记录表（Appointment）- 7/17
CREATE TABLE `appointment` (
                               `ApptID` INT(11) NOT NULL AUTO_INCREMENT,
                               `UserID` INT(11) NOT NULL COMMENT '预约客户ID（关联User）',
                               `PetID` INT(11) NOT NULL COMMENT '预约宠物ID（关联Pet）',
                               `StoreID` INT(11) NOT NULL COMMENT '承接门店ID（关联Store）',
                               `EmpID` INT(11) DEFAULT NULL COMMENT '服务员工ID（关联Employee，可空）',
                               `ApptTime` DATETIME NOT NULL COMMENT '预约服务时间',
                               `ApptStatus` VARCHAR(20) NOT NULL CHECK (ApptStatus IN ('待服务', '已完成', '已取消')) COMMENT '预约状态',
                               `CreateTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`ApptID`),
                               CONSTRAINT `FK_Appointment_User` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                               CONSTRAINT `FK_Appointment_Pet` FOREIGN KEY (`PetID`) REFERENCES `Pet` (`PetID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                               CONSTRAINT `FK_Appointment_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                               CONSTRAINT `FK_Appointment_Employee` FOREIGN KEY (`EmpID`) REFERENCES `Employee` (`EmpID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '预约记录表（1:1关联订单）';

-- 8. 预约-美容中间表（ApptBeauty）- 8/17
CREATE TABLE `apptBeauty` (
                              `RelID` INT(11) NOT NULL AUTO_INCREMENT,
                              `ApptID` INT(11) NOT NULL COMMENT '关联预约ID（关联Appointment）',
                              `BeautyID` INT(11) NOT NULL COMMENT '关联美容项目ID（关联Beauty）',
                              PRIMARY KEY (`RelID`),
                              UNIQUE KEY `UK_ApptBeauty_ApptBeauty` (`ApptID`, `BeautyID`),
                              CONSTRAINT `FK_ApptBeauty_Appointment` FOREIGN KEY (`ApptID`) REFERENCES `Appointment` (`ApptID`) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT `FK_ApptBeauty_Beauty` FOREIGN KEY (`BeautyID`) REFERENCES `Beauty` (`BeautyID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '预约-美容中间表（解耦M:N关系）';

-- 9. 预约-寄养中间表（ApptFoster）- 9/17
CREATE TABLE `apptFoster` (
                              `RelID` INT(11) NOT NULL AUTO_INCREMENT,
                              `ApptID` INT(11) NOT NULL COMMENT '关联预约ID（关联Appointment）',
                              `FosterID` INT(11) NOT NULL COMMENT '关联寄养记录ID（关联FosterRecord）',
                              PRIMARY KEY (`RelID`),
                              UNIQUE KEY `UK_ApptFoster_ApptFoster` (`ApptID`, `FosterID`),
                              CONSTRAINT `FK_ApptFoster_Appointment` FOREIGN KEY (`ApptID`) REFERENCES `Appointment` (`ApptID`) ON DELETE CASCADE ON UPDATE CASCADE,
                              CONSTRAINT `FK_ApptFoster_FosterRecord` FOREIGN KEY (`FosterID`) REFERENCES `FosterRecord` (`FosterID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '预约-寄养中间表（解耦M:N关系）';

-- 10. 预约-医疗中间表（ApptMedical）- 10/17
CREATE TABLE `apptMedical` (
                               `RelID` INT(11) NOT NULL AUTO_INCREMENT,
                               `ApptID` INT(11) NOT NULL COMMENT '关联预约ID（关联Appointment）',
                               `MedicalID` INT(11) NOT NULL COMMENT '关联医疗记录ID（关联MedicalRecord）',
                               PRIMARY KEY (`RelID`),
                               UNIQUE KEY `UK_ApptMedical_ApptMedical` (`ApptID`, `MedicalID`),
                               CONSTRAINT `FK_ApptMedical_Appointment` FOREIGN KEY (`ApptID`) REFERENCES `Appointment` (`ApptID`) ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT `FK_ApptMedical_MedicalRecord` FOREIGN KEY (`MedicalID`) REFERENCES `MedicalRecord` (`MedicalID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '预约-医疗中间表（解耦M:N关系）';

-- 11. 消费订单表（Order）- 11/17
CREATE TABLE `order` (
                         `OrderID` INT(11) NOT NULL AUTO_INCREMENT,
                         `ApptID` INT(11) NOT NULL COMMENT '关联预约ID（服务费用来源，1:1）',
                         `RelID` INT(11) DEFAULT NULL COMMENT '关联用品-门店中间表ID（用品费用关联）',
                         `TotalAmount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额（服务+用品）',
                         `PayStatus` VARCHAR(20) NOT NULL CHECK (PayStatus IN ('未支付', '已支付', '退款')) COMMENT '支付状态',
                         `PayMethod` VARCHAR(20) DEFAULT NULL COMMENT '支付方式（微信/支付宝/现金）',
                         `PayTime` DATETIME DEFAULT NULL COMMENT '支付时间',
                         `OrderCreateTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         PRIMARY KEY (`OrderID`),
                         UNIQUE KEY `UK_Order_Appt` (`ApptID`),
                         CONSTRAINT `FK_Order_Appointment` FOREIGN KEY (`ApptID`) REFERENCES `Appointment` (`ApptID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                         CONSTRAINT `FK_Order_PetProductStore` FOREIGN KEY (`RelID`) REFERENCES `PetProductStore` (`RelID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '消费订单表（1:N关联用品消费记录）';

-- 12. 寄养记录表（FosterRecord）- 12/17
CREATE TABLE `fosterRecord` (
                                `FosterID` INT(11) NOT NULL AUTO_INCREMENT,
                                `PetID` INT(11) NOT NULL COMMENT '寄养宠物ID（关联Pet）',
                                `StoreID` INT(11) NOT NULL COMMENT '承接门店ID（关联Store）',
                                `EmpID` INT(11) DEFAULT NULL COMMENT '负责员工ID（关联Employee，可空）',
                                `StartDate` DATETIME NOT NULL COMMENT '寄养开始时间',
                                `EndDate` DATETIME NOT NULL COMMENT '寄养结束时间',
                                `FosterFee` DECIMAL(10,2) NOT NULL COMMENT '寄养总费用',
                                `FosterStatus` VARCHAR(20) NOT NULL CHECK (FosterStatus IN ('进行中', '已结束', '已取消')) COMMENT '寄养状态',
                                `FosterRemarks` TEXT DEFAULT NULL COMMENT '寄养备注（如喂食要求）',
                                `DailyStatus` TEXT DEFAULT NULL COMMENT '寄养状态记录（如2025-12-01：轻微腹泻，已喂食益生菌）',
                                PRIMARY KEY (`FosterID`),
                                CONSTRAINT `FK_FosterRecord_Pet` FOREIGN KEY (`PetID`) REFERENCES `Pet` (`PetID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                CONSTRAINT `FK_FosterRecord_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                CONSTRAINT `FK_FosterRecord_Employee` FOREIGN KEY (`EmpID`) REFERENCES `Employee` (`EmpID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '寄养记录表';

-- 13. 医疗记录表（MedicalRecord）- 13/17
CREATE TABLE `medicalRecord` (
                                 `MedicalID` INT(11) NOT NULL AUTO_INCREMENT,
                                 `PetID` INT(11) NOT NULL COMMENT '就诊宠物ID（关联Pet）',
                                 `EmpID` INT(11) NOT NULL COMMENT '接诊医生ID（关联Employee，非空）',
                                 `StoreID` INT(11) NOT NULL COMMENT '就诊门店ID（关联Store）',
                                 `MedicalTime` DATETIME NOT NULL COMMENT '就诊时间',
                                 `Diagnosis` TEXT NOT NULL COMMENT '诊断结果',
                                 `Medication` TEXT DEFAULT NULL COMMENT '用药情况',
                                 `MedicalFee` DECIMAL(10,2) NOT NULL COMMENT '医疗费用',
                                 `FollowUpAdvice` TEXT DEFAULT NULL COMMENT '复诊建议',
                                 PRIMARY KEY (`MedicalID`),
                                 CONSTRAINT `FK_MedicalRecord_Pet` FOREIGN KEY (`PetID`) REFERENCES `Pet` (`PetID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 CONSTRAINT `FK_MedicalRecord_Employee` FOREIGN KEY (`EmpID`) REFERENCES `Employee` (`EmpID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 CONSTRAINT `FK_MedicalRecord_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '医疗记录表';

-- 14. 员工请假表（LeaveRecord）- 14/17
CREATE TABLE `leaveRecord` (
                               `LeaveID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '请假记录唯一标识',
                               `EmpID` INT(11) NOT NULL COMMENT '请假员工ID（关联Employee）',
                               `StoreID` INT(11) NOT NULL COMMENT '员工所属门店ID（关联Store，冗余查询）',
                               `LeaveType` VARCHAR(20) NOT NULL CHECK (LeaveType IN ('事假', '病假', '年假')) COMMENT '请假类型',
                               `StartTime` DATETIME NOT NULL COMMENT '请假开始时间',
                               `EndTime` DATETIME NOT NULL COMMENT '请假结束时间',
                               `ApplyTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
                               `ApproverID` INT(11) DEFAULT NULL COMMENT '审批人ID（关联Employee，可空）',
                               `ApproveStatus` VARCHAR(20) NOT NULL CHECK (ApproveStatus IN ('待审批', '已通过', '已驳回')) COMMENT '审批状态',
                               `ApproveTime` DATETIME DEFAULT NULL COMMENT '审批时间',
                               `LeaveReason` TEXT DEFAULT NULL COMMENT '请假理由',
                               PRIMARY KEY (`LeaveID`),
                               CONSTRAINT `FK_LeaveRecord_Employee` FOREIGN KEY (`EmpID`) REFERENCES `Employee` (`EmpID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                               CONSTRAINT `FK_LeaveRecord_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                               CONSTRAINT `FK_LeaveRecord_Approver` FOREIGN KEY (`ApproverID`) REFERENCES `Employee` (`EmpID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '员工请假表（1:N关联Employee）';

-- 15. 宠物用品表（PetProduct）- 15/17
CREATE TABLE `petProduct` (
                              `ProductID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用品唯一标识',
                              `ProductName` VARCHAR(100) NOT NULL COMMENT '用品名（如狗粮/猫砂）',
                              `ProductType` VARCHAR(50) NOT NULL COMMENT '类型（食品/玩具/洗护）',
                              `Supplier` VARCHAR(100) DEFAULT NULL COMMENT '供应商名称',
                              PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '宠物用品表（M:N关联门店）';

-- 16. 宠物用品-门店中间表（PetProductStore）- 16/17
CREATE TABLE `petProductStore` (
                                   `RelID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '关联记录唯一标识',
                                   `ProductID` INT(11) NOT NULL COMMENT '关联用品ID（关联PetProduct）',
                                   `StoreID` INT(11) NOT NULL COMMENT '关联门店ID（关联Store）',
                                   `Price` DECIMAL(8,2) NOT NULL COMMENT '门店用品单价',
                                   `StoreStock` INT(11) NOT NULL DEFAULT 0 COMMENT '门店库存数量',
                                   `ShelfStatus` VARCHAR(20) NOT NULL CHECK (ShelfStatus IN ('在售', '下架', '缺货')) COMMENT '上架状态',
                                   PRIMARY KEY (`RelID`),
                                   UNIQUE KEY `UK_PetProductStore_ProductStore` (`ProductID`, `StoreID`),
                                   CONSTRAINT `FK_PetProductStore_Product` FOREIGN KEY (`ProductID`) REFERENCES `PetProduct` (`ProductID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                   CONSTRAINT `FK_PetProductStore_Store` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '宠物用品-门店中间表（解耦M:N关系）';

-- 17. 宠物用品消费表（OrderProduct）- 17/17
CREATE TABLE `orderProduct` (
                                `OrderProductID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用品订单唯一标识',
                                `OrderID` INT(11) NOT NULL COMMENT '关联主订单ID（关联Order）',
                                `RelID` INT(11) NOT NULL COMMENT '关联门店-用品铺货记录ID（关联PetProductStore）',
                                `Quantity` INT(11) NOT NULL DEFAULT 1 COMMENT '购买数量（修正原拼写错误）',
                                `ActualPrice` DECIMAL(8,2) NOT NULL COMMENT '实际成交单价（取自PetProductStore.Price）',
                                `Subtotal` DECIMAL(8,2) NOT NULL COMMENT '用品小计（Quantity×ActualPrice）',
                                `CreateTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
                                PRIMARY KEY (`OrderProductID`),
                                CONSTRAINT `FK_OrderProduct_Order` FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                CONSTRAINT `FK_OrderProduct_PetProductStore` FOREIGN KEY (`RelID`) REFERENCES `PetProductStore` (`RelID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '宠物用品消费表（解耦订单与用品M:N关系）';
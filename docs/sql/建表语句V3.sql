create table city
(
    CityID   int auto_increment
        primary key,
    CityName varchar(50) not null comment '城市名（如北京）',
    Province varchar(50) not null comment '省份（如北京市）',
    ZipCode  varchar(6)  null comment '邮政编码（如100000）'
)
    comment '城市信息表（1:N关联门店）' charset = utf8mb4;

create table petdrug
(
    DrugID    int auto_increment
        primary key,
    DrugName  varchar(100)  not null comment '药品名称（如阿莫西林、驱虫药）',
    DrugType  varchar(50)   not null comment '药品类型（处方药/非处方药）',
    DrugSpec  varchar(100)  null comment '药品规格（如10mg/片、50ml/瓶）',
    Supplier  varchar(100)  null comment '药品供应商',
    UnitPrice decimal(8, 2) not null comment '药品单价',
    DrugUsage text          null comment '适用病症/用法用量',
    DrugTaboo text          null comment '用药禁忌/过敏提醒',
    constraint UK_Drug_NameSpec
        unique (DrugName, DrugSpec)
)
    comment '宠物药品信息表（M:N关联门店）' charset = utf8mb4;

create table store
(
    StoreID       int auto_increment
        primary key,
    CityID        int          not null comment '所属城市ID（关联City）',
    StoreName     varchar(100) not null comment '门店名',
    StoreAddress  varchar(200) not null comment '门店地址',
    StorePhone    varchar(20)  not null comment '门店电话',
    BusinessHours varchar(50)  null comment '营业时间（如09:00-21:00）',
    constraint UK_Store_Phone
        unique (StorePhone),
    constraint FK_Store_City
        foreign key (CityID) references city (CityID)
            on update cascade
)
    comment '门店信息表（1:N关联员工/预约）' charset = utf8mb4;

create table employee
(
    EmpID     int auto_increment
        primary key,
    StoreID   int            not null comment '所属门店ID（关联Store）',
    EmpName   varchar(50)    not null comment '员工姓名',
    Position  varchar(50)    not null comment '岗位（医生/美容师）',
    EmpPhone  varchar(20)    not null comment '员工电话',
    EntryTime date           null comment '入职时间',
    Salary    decimal(10, 2) null comment '薪资',
    constraint UK_Employee_Phone
        unique (EmpPhone),
    constraint FK_Employee_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade
)
    comment '员工信息表（1:N关联预约/寄养/医疗）' charset = utf8mb4;

create table petdrugstore
(
    RelID       int auto_increment comment '药品库存关联ID'
        primary key,
    DrugID      int           not null comment '关联药品ID（关联PetDrug）',
    StoreID     int           not null comment '关联门店ID（关联Store）',
    StorePrice  decimal(8, 2) not null comment '门店药品售价',
    StoreStock  int default 0 not null comment '门店药品库存数量',
    ShelfStatus varchar(20)   not null comment '上架状态',
    StockWarn   int default 5 not null comment '库存预警值，低于此数值标红提醒补货',
    constraint UK_PetDrugStore_DrugStore
        unique (DrugID, StoreID),
    constraint FK_PetDrugStore_Drug
        foreign key (DrugID) references petdrug (DrugID)
            on update cascade,
    constraint FK_PetDrugStore_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade
)
    comment '宠物药品-门店中间表（解耦M:N关系）' charset = utf8mb4;

create table user
(
    UserID       int auto_increment
        primary key,
    Account      varchar(20)                          not null comment '账号：首字符C + 5-15位数字/字母',
    UserName     varchar(50)                          not null comment '主人姓名',
    Phone        varchar(20)                          not null comment '联系电话',
    Address      varchar(200)                         null comment '居住地址',
    RegisterTime datetime   default CURRENT_TIMESTAMP not null comment '注册时间',
    Email        varchar(100)                         null comment '邮箱',
    Password     varchar(100)                         not null comment '加密后的密码',
    IsAdmin      tinyint(1) default 0                 not null comment '是否为管理员：0-普通用户，1-管理员',
    IsBanned     tinyint(1) default 0                 not null comment '是否被封禁：0-正常，1-已封禁',
    constraint UK_User_Email
        unique (Email),
    constraint UK_User_Phone
        unique (Phone)
)
    comment '客户信息表（1:N关联宠物）' charset = utf8mb4;

create table message
(
    MessageID      int auto_increment comment '消息ID'
        primary key,
    ReceiveUserID  int                                   not null comment '接收消息的用户ID（关联user表）',
    MessageType    varchar(50)                           not null comment '消息类型（预约提醒/订单支付/处方到期/库存预警等）',
    BusinessType   varchar(50)                           not null comment '关联业务类型（appointment/order/prescription/stock）',
    BusinessID     int                                   not null comment '关联业务ID（预约ID/订单ID/处方ID等）',
    MessageContent text                                  not null comment '消息内容',
    SendTime       datetime    default CURRENT_TIMESTAMP not null comment '消息发送时间',
    ReadStatus     varchar(20) default '未读'            not null comment '阅读状态（未读/已读）',
    IsDeleted      tinyint(1)  default 0                 not null comment '是否删除：0-否，1-是',
    constraint FK_Message_User
        foreign key (ReceiveUserID) references user (UserID)
            on update cascade
)
    comment '系统消息表（存储预约/订单/处方等业务的通知消息）' charset = utf8mb4;

create index idx_business
    on message (BusinessType, BusinessID);

create index idx_read_status
    on message (ReadStatus);

create index idx_receive_user
    on message (ReceiveUserID);

create table pet
(
    PetID          int auto_increment
        primary key,
    UserID         int           not null comment '所属客户ID（关联User）',
    PetName        varchar(50)   not null comment '宠物名',
    Breed          varchar(50)   null comment '品种（如金毛/布偶）',
    Gender         char          null comment '性别（M=男/F=女/U=未知）',
    BirthDate      date          null comment '出生日期',
    VaccineStatus  varchar(200)  null comment '疫苗接种情况',
    Remarks        text          null comment '备注（如过敏史）',
    AllergyDrug    varchar(500)  null comment '宠物过敏药物',
    MedicalHistory varchar(1000) null comment '宠物既往病史',
    constraint FK_Pet_User
        foreign key (UserID) references user (UserID)
            on update cascade
)
    comment '宠物信息表（1:N关联预约/寄养/医疗）' charset = utf8mb4;

create table appointment
(
    ApptID         int auto_increment
        primary key,
    UserID         int                                   not null comment '预约客户ID（关联User）',
    PetID          int                                   not null comment '预约宠物ID（关联Pet）',
    StoreID        int                                   not null comment '承接门店ID（关联Store）',
    EmpID          int                                   null comment '服务员工ID（关联Employee，可空）',
    ApptTime       datetime                              not null comment '预约服务时间',
    ApptStatus     varchar(20)                           not null comment '预约状态',
    DiagnoseStatus varchar(20) default '待诊断'          not null comment '诊断状态：待诊断/已诊断/无需诊断',
    DiagnoseDesc   text                                  null comment '医生诊断简述，关联处方诊断结果',
    CreateTime     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint FK_Appointment_Employee
        foreign key (EmpID) references employee (EmpID)
            on update cascade on delete set null,
    constraint FK_Appointment_Pet
        foreign key (PetID) references pet (PetID)
            on update cascade,
    constraint FK_Appointment_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade,
    constraint FK_Appointment_User
        foreign key (UserID) references user (UserID)
            on update cascade
)
    comment '预约记录表（1:1关联订单）' charset = utf8mb4;

create table petmedicalprescription
(
    PrescriptionID int auto_increment
        primary key,
    PetID          int                                   not null comment '就诊宠物ID（关联Pet）',
    UserID         int                                   not null comment '宠物主人ID（关联User）',
    EmpID          int                                   not null comment '开方医生ID（关联Employee）',
    StoreID        int                                   not null comment '开方门店ID（关联Store）',
    PrescriptionNo varchar(50)                           not null comment '处方编号（唯一）',
    Diagnosis      text                                  not null comment '诊断结果',
    CreateTime     datetime    default CURRENT_TIMESTAMP not null comment '开方时间',
    ValidTime      datetime                              not null comment '处方有效期（默认7天）',
    PresStatus     varchar(20) default '已开具'          not null comment '处方状态：已开具/已生成订单/已失效',
    constraint UK_Prescription_No
        unique (PrescriptionNo),
    constraint FK_Prescription_Employee
        foreign key (EmpID) references employee (EmpID)
            on update cascade,
    constraint FK_Prescription_Pet
        foreign key (PetID) references pet (PetID)
            on update cascade,
    constraint FK_Prescription_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade,
    constraint FK_Prescription_User
        foreign key (UserID) references user (UserID)
            on update cascade
)
    comment '宠物医疗处方表（处方药必备，关联宠物/医生/门店）' charset = utf8mb4;

create table medicalrecord
(
    MedicalID      int auto_increment
        primary key,
    PetID          int            not null comment '就诊宠物ID（关联Pet）',
    EmpID          int            not null comment '接诊医生ID（关联Employee，非空）',
    StoreID        int            not null comment '就诊门店ID（关联Store）',
    MedicalTime    datetime       not null comment '就诊时间',
    Diagnosis      text           not null comment '诊断结果',
    Medication     text           null comment '用药情况',
    MedicalFee     decimal(10, 2) not null comment '医疗费用',
    FollowUpAdvice text           null comment '复诊建议',
    PrescriptionID int            null comment '关联处方ID，关联petmedicalprescription表',
    constraint FK_MedicalRecord_Employee
        foreign key (EmpID) references employee (EmpID)
            on update cascade,
    constraint FK_MedicalRecord_Pet
        foreign key (PetID) references pet (PetID)
            on update cascade,
    constraint FK_MedicalRecord_Prescription
        foreign key (PrescriptionID) references petmedicalprescription (PrescriptionID)
            on update cascade on delete set null,
    constraint FK_MedicalRecord_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade
)
    comment '医疗记录表' charset = utf8mb4;

create table apptmedical
(
    RelID     int auto_increment
        primary key,
    ApptID    int not null comment '关联预约ID（关联Appointment）',
    MedicalID int not null comment '关联医疗记录ID（关联MedicalRecord）',
    constraint UK_ApptMedical_ApptMedical
        unique (ApptID, MedicalID),
    constraint FK_ApptMedical_Appointment
        foreign key (ApptID) references appointment (ApptID)
            on update cascade on delete cascade,
    constraint FK_ApptMedical_MedicalRecord
        foreign key (MedicalID) references medicalrecord (MedicalID)
            on update cascade
)
    comment '预约-医疗中间表（解耦M:N关系）' charset = utf8mb4;

create table pet_order
(
    OrderID           int auto_increment comment '订单ID'
        primary key,
    UserID            int                                not null comment '所属用户ID（关联User）',
    PetID             int                                not null comment '关联宠物ID（关联Pet）',
    StoreID           int                                not null comment '服务门店ID（关联Store）',
    OrderNo           varchar(50)                        not null comment '订单编号（唯一，如ORD20240520001）',
    OrderType         varchar(20)                        not null comment '订单类型（药品购买/医疗服务等）',
    TotalAmount       decimal(10, 2)                     not null comment '订单总金额',
    OrderStatus       varchar(20)                        not null comment '订单状态（待支付/已支付/已取消/已完成）',
    prescription_id   int                                null comment '关联处方ID（处方药订单必选，关联PetMedicalPrescription）',
    compliance_status tinyint  default 0                 not null comment '合规状态（0-未审核/1-合规/2-不合规）',
    CreateTime        datetime default CURRENT_TIMESTAMP not null comment '订单创建时间',
    UpdateTime        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '订单更新时间',
    Remark            text                               null comment '订单备注',
    constraint UK_Order_No
        unique (OrderNo),
    constraint FK_Order_Pet
        foreign key (PetID) references pet (PetID)
            on update cascade,
    constraint FK_Order_Prescription
        foreign key (prescription_id) references petmedicalprescription (PrescriptionID)
            on delete set null,
    constraint FK_Order_Store
        foreign key (StoreID) references store (StoreID)
            on update cascade,
    constraint FK_Order_User
        foreign key (UserID) references user (UserID)
            on update cascade
)
    comment '用户订单表（关联用户、宠物、处方）' charset = utf8mb4;

create table order_drug
(
    RelID     int auto_increment comment '订单药品关联ID'
        primary key,
    OrderID   int            not null comment '关联订单ID（关联pet_order）',
    DrugID    int            not null comment '关联药品ID（关联petdrug）',
    DrugNum   int            not null comment '购买数量',
    UnitPrice decimal(8, 2)  not null comment '购买时单价（冗余，避免后续价格变动）',
    Subtotal  decimal(10, 2) not null comment '小计金额（DrugNum*UnitPrice）',
    constraint UK_OrderDrug_OrderDrug
        unique (OrderID, DrugID),
    constraint FK_OrderDrug_Drug
        foreign key (DrugID) references petdrug (DrugID)
            on update cascade,
    constraint FK_OrderDrug_Order
        foreign key (OrderID) references pet_order (OrderID)
            on delete cascade
)
    comment '订单药品明细表（记录订单包含的药品）' charset = utf8mb4;

create table payment_record
(
    PayID        int auto_increment comment '支付记录ID'
        primary key,
    OrderID      int            not null comment '关联订单ID（关联pet_order）',
    PayNo        varchar(50)    not null comment '支付流水号（第三方支付返回，如微信/支付宝单号）',
    PayType      varchar(20)    not null comment '支付方式（微信/支付宝/现金）',
    PayAmount    decimal(10, 2) not null comment '支付金额',
    PayStatus    varchar(20)    not null comment '支付状态（未支付/已支付/退款中/已退款）',
    PayTime      datetime       null comment '支付时间（支付成功时记录）',
    RefundTime   datetime       null comment '退款时间（退款成功时记录）',
    RefundReason text           null comment '退款原因',
    constraint UK_Payment_PayNo
        unique (PayNo),
    constraint FK_Payment_Order
        foreign key (OrderID) references pet_order (OrderID)
            on delete cascade
)
    comment '支付记录表（关联订单）' charset = utf8mb4;

create definer = root@localhost trigger trg_deduct_drug_stock_after_pay
    after update
    on payment_record
    for each row
BEGIN
    -- 仅当支付状态从【未支付】改为【已支付】时执行扣库存
    IF NEW.PayStatus = '已支付' AND OLD.PayStatus != '已支付' THEN
        UPDATE petdrugstore s
            INNER JOIN order_drug od ON s.DrugID = od.DrugID
            INNER JOIN pet_order o ON od.OrderID = o.OrderID
        SET s.StoreStock = s.StoreStock - od.DrugNum
        WHERE o.OrderID = NEW.OrderID AND s.StoreID = o.StoreID;
    END IF;
END;

create definer = root@localhost trigger trg_update_pres_status_after_order
    after insert
    on pet_order
    for each row
BEGIN
    -- 仅当订单关联了处方ID时，更新处方状态
    IF NEW.prescription_id IS NOT NULL THEN
        UPDATE petmedicalprescription SET PresStatus = '已生成订单' WHERE PrescriptionID = NEW.prescription_id;
    END IF;
END;

create table prescriptiondrug
(
    RelID          int auto_increment
        primary key,
    PrescriptionID int          not null comment '关联处方ID（关联PetMedicalPrescription）',
    DrugID         int          not null comment '关联药品ID（关联PetDrug）',
    DrugNum        int          not null comment '药品数量',
    DrugDosage     varchar(200) not null comment '用药剂量（如1片/次，2次/天）',
    DrugCycle      varchar(100) null comment '用药周期（如连用7天）',
    constraint UK_PrescriptionDrug_PresDrug
        unique (PrescriptionID, DrugID),
    constraint FK_PrescriptionDrug_Drug
        foreign key (DrugID) references petdrug (DrugID)
            on update cascade,
    constraint FK_PrescriptionDrug_Prescription
        foreign key (PrescriptionID) references petmedicalprescription (PrescriptionID)
            on update cascade on delete cascade
)
    comment '处方药品明细表（解耦处方与药品的M:N关系）' charset = utf8mb4;

create definer = root@localhost view v_store_monthly_report as
select `s`.`StoreID`                                   AS `StoreID`,
       `s`.`StoreName`                                 AS `StoreName`,
       date_format(`appt`.`CreateTime`, '%Y-%m')       AS `StatMonth`,
       count(distinct (case
                           when (date_format(`u`.`RegisterTime`, '%Y-%m') = date_format(`appt`.`CreateTime`, '%Y-%m'))
                               then `u`.`UserID` end)) AS `NewUserCount`,
       count(distinct `appt`.`ApptID`)                 AS `TotalOrderCount`,
       count(distinct `am`.`ApptID`)                   AS `MedicalOrderCount`,
       ifnull(sum(`mr`.`MedicalFee`), 0)               AS `MedicalRevenue`,
       ifnull(sum(`mr`.`MedicalFee`), 0)               AS `TotalRevenue`,
       '医疗'                                          AS `HotServiceType`
from ((((`pet_service_management`.`appointment` `appt` join `pet_service_management`.`store` `s`
         on ((`appt`.`StoreID` = `s`.`StoreID`))) join `pet_service_management`.`user` `u`
        on ((`appt`.`UserID` = `u`.`UserID`))) left join `pet_service_management`.`apptmedical` `am`
       on ((`appt`.`ApptID` = `am`.`ApptID`))) left join `pet_service_management`.`medicalrecord` `mr`
      on ((`am`.`MedicalID` = `mr`.`MedicalID`)))
where (`appt`.`ApptStatus` = '已完成')
group by `s`.`StoreID`, `s`.`StoreName`, date_format(`appt`.`CreateTime`, '%Y-%m');

-- comment on column v_store_monthly_report.StoreName not supported: 门店名


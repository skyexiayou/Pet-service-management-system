# 宠物服务管理系统 (Pet Service Management System)

## 项目简介

宠物服务管理系统是一个基于 Spring Boot 3.1.2 的综合性宠物服务平台，为宠物主人和宠物服务机构提供全方位的服务管理解决方案。系统采用前后端分离架构，实现了客户管理、宠物档案、预约服务、订单支付、门店管理、数据统计等核心功能。

## 技术栈

### 后端技术
- **框架**: Spring Boot 3.1.2
- **ORM**: MyBatis-Plus 3.5.3.2 + MyBatis 3.0.3
- **数据库**: MySQL 8.0
- **JDK**: 17
- **API文档**: Knife4j 4.4.0 (OpenAPI 3)
- **验证框架**: Jakarta Bean Validation
- **构建工具**: Maven 3.6+

### 数据库设计
- 采用第三范式 (3NF) 设计
- 17张核心业务表
- 完整的外键约束和索引优化
- 支持事务一致性保证

## 核心功能模块

### 1. 客户端功能

#### 1.1 账号与个人信息管理
- 用户注册（账号+手机号+密码）
- 用户登录（账号/手机号登录）
- 密码重置（手机号验证）
- 个人信息修改（姓名、性别、年龄、邮箱、地址）
- 个人信息查询（数据脱敏：手机号、邮箱）

#### 1.2 宠物档案管理
- 创建宠物档案（名称、品种、性别、出生日期、体重等）
- 查询宠物档案（支持照片路径脱敏）
- 更新宠物档案（体重、疫苗状态等）
- 查询宠物服务记录（美容、医疗、寄养历史）

#### 1.3 服务与用品查询
- 美容服务查询（分页）
- 医疗服务查询（分页）
- 寄养服务查询（分页）
- 门店查询（按城市筛选，分页）
- 宠物用品查询（按门店、类型筛选，分页）


#### 1.4 预约管理
- **多服务组合预约**: 一次性预约美容、寄养、医疗等多种服务
- **预约列表查询**: 查看所有宠物的预约记录
- **预约详情查询**: 查看预约的完整信息（包含服务明细）
- **预约取消**: 支持24小时前取消预约

**预约业务规则**:
- 预约时间必须在门店营业时间内
- 员工在预约时间段内不能请假
- 寄养结束日期必须晚于开始日期
- 医疗服务必须填写症状描述
- 所有操作在单个事务中执行，确保原子性

#### 1.5 专项服务管理

**美容服务**:
- 查询用户的美容服务列表
- 查询美容服务详情（包含服务项目、价格、时长）

**寄养服务**:
- 查询用户的寄养服务列表
- 查询寄养服务详情（包含每日状态记录）
- 接领确认（结束寄养服务）

**医疗服务**:
- 查询用户的医疗服务列表
- 查询医疗服务详情（包含诊断、用药、复诊建议）

#### 1.6 订单与支付管理
- **生成关联预约的订单**: 根据预约生成订单（服务费用+可选用品费用）
- **生成纯用品购买订单**: 不关联预约，直接购买宠物用品
- **查询订单列表**: 查看用户所有订单记录
- **查询订单详情**: 查看订单完整信息（服务明细+用品明细）
- **在线支付**: 支持微信、支付宝、现金支付
- **支付凭证生成**: 自动生成唯一支付凭证号

**订单业务规则**:
- 订单总金额 = 美容费用 + 寄养费用 + 医疗费用 + 用品费用
- 所有金额使用 BigDecimal 计算，保留2位小数
- 纯用品订单自动验证库存并扣减
- 使用数据库行锁防止并发库存问题


### 2. 管理员端功能

#### 2.1 身份验证机制
- 通过用户名或手机号验证管理员身份
- 自动获取管理员所属门店ID
- 所有管理员接口自动添加门店权限过滤
- 仅能操作本门店数据

#### 2.2 基础信息管理

**城市管理**:
- 查询所有城市
- 创建城市（省份+城市名唯一性校验）
- 更新城市信息
- 删除城市（检查门店关联）

**员工管理**:
- 分页查询门店员工列表
- 创建员工（手机号唯一性校验）
- 更新员工信息（薪资、岗位等）
- 删除员工
- 支持岗位：医生、美容师、管理员

#### 2.3 服务与用品管理

**美容服务管理**:
- 分页查询美容服务列表
- 创建美容服务（名称、类型、价格、时长）
- 更新美容服务（价格调整仅影响新订单）
- 删除美容服务

**宠物用品管理**:
- 分页查询用品列表
- 创建用品（全局共享，不区分门店）
- 更新用品信息
- 用品不可删除，仅可下架

**门店铺货管理**:
- 分页查询门店铺货列表
- 创建或更新门店铺货（同一用品在同一门店只能有一条记录）
- 调整库存（补货/减库存）
- 库存为0时自动更新为"缺货"
- 库存≥1时自动更新为"在售"


#### 2.4 预约与订单管理

**预约审核**:
- 查询待审核预约列表
- 审核预约（通过/驳回）
- 审核通过可选分配员工
- 审核驳回更新预约状态为"已取消"

**订单管理**:
- 分页查询门店订单列表
- 订单退款（仅已支付订单）
- 退款自动恢复用品库存
- 退款后预约状态同步更新为"已取消"

#### 2.5 专项服务管理

**美容服务**:
- 完成美容服务（标记为已完成）

**寄养服务**:
- 更新寄养每日状态（追加记录）
- 状态格式：日期：状态描述

**医疗服务**:
- 创建医疗记录（诊断、用药、费用）
- 补充医疗记录备注（追加到复诊建议）
- 每个预约只能创建一次医疗记录

#### 2.6 数据统计（门店月报）

**月报查询**:
- 基于数据库视图 `v_store_monthly_report` 实现
- 统计维度：
  - 新增客户数
  - 总订单数
  - 美容/寄养/医疗订单数
  - 用品销售额
  - 美容/寄养/医疗营收
  - 总营收
  - 热门服务类型
- 只统计已支付订单
- 自动过滤当前管理员所属门店数据


## 数据库设计

### 核心表结构 (17张表)

1. **user** - 客户信息表
2. **pet** - 宠物信息表
3. **city** - 城市信息表
4. **store** - 门店信息表
5. **employee** - 员工信息表
6. **beauty** - 美容项目表
7. **appointment** - 预约记录表
8. **apptBeauty** - 预约-美容中间表
9. **apptFoster** - 预约-寄养中间表
10. **apptMedical** - 预约-医疗中间表
11. **order** - 消费订单表
12. **fosterRecord** - 寄养记录表
13. **medicalRecord** - 医疗记录表
14. **leaveRecord** - 员工请假表
15. **petProduct** - 宠物用品表
16. **petProductStore** - 宠物用品-门店中间表
17. **orderProduct** - 宠物用品消费表

### 数据库视图

- **v_store_monthly_report** - 门店月报视图（统计门店月度运营数据）

### 设计特点

- 采用第三范式 (3NF) 设计，避免数据冗余
- 使用中间表解耦多对多关系
- 完整的外键约束保证数据一致性
- 唯一索引防止重复数据
- CHECK 约束限制枚举值
- 支持级联更新和删除

## 项目结构

```
PetServiceManagement/
├── src/main/java/cn/edu/xaut/
│   ├── config/                      # 配置类
│   │   ├── Knife4jConfig.java      # API文档配置
│   │   └── WebConfig.java          # Web配置
│   ├── controller/                  # 控制器层
│   │   ├── admin/                  # 管理员控制器
│   │   │   ├── AdminAppointmentController.java
│   │   │   ├── AdminBeautyController.java
│   │   │   ├── AdminCityController.java
│   │   │   ├── AdminEmployeeController.java
│   │   │   ├── AdminProductController.java
│   │   │   ├── AdminProductStoreController.java
│   │   │   └── AdminServiceController.java
│   │   ├── AppointmentController.java
│   │   ├── BeautyController.java
│   │   ├── BeautyServiceController.java
│   │   ├── BoardingController.java
│   │   ├── FosterServiceController.java
│   │   ├── MedicalController.java
│   │   ├── MedicalServiceController.java
│   │   ├── OrderController.java
│   │   ├── PetController.java
│   │   ├── PetProductController.java
│   │   ├── StoreController.java
│   │   └── UserController.java
│   ├── domain/                      # 领域模型
│   │   ├── dto/                    # 数据传输对象
│   │   │   ├── admin/              # 管理员DTO
│   │   │   ├── appointment/        # 预约DTO
│   │   │   ├── beauty/             # 美容DTO
│   │   │   ├── boarding/           # 寄养DTO
│   │   │   ├── medical/            # 医疗DTO
│   │   │   ├── order/              # 订单DTO
│   │   │   ├── pet/                # 宠物DTO
│   │   │   ├── serviceitem/        # 服务项DTO
│   │   │   ├── store/              # 门店DTO
│   │   │   └── user/               # 用户DTO
│   │   ├── entity/                 # 实体类
│   │   │   ├── appointment/
│   │   │   ├── apptbeauty/
│   │   │   ├── apptfoster/
│   │   │   ├── apptmedical/
│   │   │   ├── beauty/
│   │   │   ├── boarding/
│   │   │   ├── city/
│   │   │   ├── employee/
│   │   │   ├── fosterrecord/
│   │   │   ├── leaverecord/
│   │   │   ├── medical/
│   │   │   ├── medicalrecord/
│   │   │   ├── order/
│   │   │   ├── pet/
│   │   │   ├── petproduct/
│   │   │   ├── petproductstore/
│   │   │   ├── store/
│   │   │   └── user/
│   │   └── vo/                     # 视图对象
│   │       ├── admin/
│   │       ├── appointment/
│   │       ├── beauty/
│   │       ├── foster/
│   │       ├── medical/
│   │       ├── order/
│   │       ├── pet/
│   │       ├── petproduct/
│   │       └── user/
│   ├── exception/                   # 异常处理
│   │   ├── BusinessException.java
│   │   └── GlobalExceptionHandler.java
│   ├── mapper/                      # 数据访问层
│   │   ├── AdminAppointmentMapper.java
│   │   ├── AppointmentMapper.java
│   │   ├── ApptBeautyMapper.java
│   │   ├── ApptFosterMapper.java
│   │   ├── ApptMedicalMapper.java
│   │   ├── BeautyMapper.java
│   │   ├── BoardingMapper.java
│   │   ├── CityMapper.java
│   │   ├── EmployeeMapper.java
│   │   ├── FosterRecordMapper.java
│   │   ├── LeaveRecordMapper.java
│   │   ├── MedicalMapper.java
│   │   ├── MedicalRecordMapper.java
│   │   ├── OrderMapper.java
│   │   ├── OrderProductMapper.java
│   │   ├── PetMapper.java
│   │   ├── PetProductMapper.java
│   │   ├── PetProductStoreMapper.java
│   │   ├── StoreMapper.java
│   │   └── UserMapper.java
│   ├── service/                     # 业务逻辑层
│   │   ├── admin/                  # 管理员服务
│   │   ├── appointment/            # 预约服务
│   │   ├── beauty/                 # 美容服务
│   │   ├── boarding/               # 寄养服务
│   │   ├── city/                   # 城市服务
│   │   ├── employee/               # 员工服务
│   │   ├── foster/                 # 寄养服务
│   │   ├── medical/                # 医疗服务
│   │   ├── order/                  # 订单服务
│   │   ├── orderproduct/           # 订单用品服务
│   │   ├── pet/                    # 宠物服务
│   │   ├── petproduct/             # 宠物用品服务
│   │   ├── petproductstore/        # 门店铺货服务
│   │   ├── store/                  # 门店服务
│   │   └── user/                   # 用户服务
│   ├── utils/                       # 工具类
│   │   ├── AdminAuthUtil.java      # 管理员身份验证
│   │   ├── AmountCalculationUtil.java  # 金额计算
│   │   ├── CommonUtils.java        # 通用工具
│   │   ├── PaymentVoucherUtil.java # 支付凭证生成
│   │   └── TimeValidationUtil.java # 时间校验
│   └── PetServiceManagementApplication.java  # 启动类
├── src/main/resources/
│   ├── mapper/                      # MyBatis XML映射文件
│   └── application.yml              # 应用配置
├── docs/                            # 文档目录
│   ├── 建表语句.sql
│   ├── 插入测试数据.sql
│   ├── 门店月报视图.sql
│   ├── 第一阶段客户账号-个人信息管理功能接口说明文档.md
│   ├── 第二阶段注册客户 - 宠物档案管理 + 服务与用品查询接口文档.md
│   ├── 第三阶段注册客户 - 预约管理 + 专项服务（美容 + 寄养 + 医疗）.md
│   ├── 第四阶段注册客户 - 订单与支付管理接口文档.md
│   ├── 第五阶段门店管理员-基础信息+服务与用品管理.md
│   ├── 第六阶段门店管理员-预约订单管理+数据统计（含门店月报）.md
│   └── README.md                    # 本文档
└── pom.xml                          # Maven配置
```


## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- IDE: IntelliJ IDEA / Eclipse (推荐 IntelliJ IDEA)

### 安装步骤

#### 1. 克隆项目

```bash
git clone <repository-url>
cd PetServiceManagement
```

#### 2. 创建数据库

```bash
# 登录MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE pet_service_management CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# 使用数据库
USE pet_service_management;

# 执行建表语句
SOURCE docs/建表语句.sql;

# 执行门店月报视图创建
SOURCE docs/门店月报视图.sql;

# （可选）插入测试数据
SOURCE docs/插入测试数据.sql;
```

#### 3. 配置数据库连接

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/pet_service_management?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: your_password  # 修改为你的MySQL密码
```

#### 4. 编译项目

```bash
mvn clean compile
```

#### 5. 运行项目

```bash
mvn spring-boot:run
```

或者在 IDE 中直接运行 `PetServiceManagementApplication.java`

#### 6. 访问API文档

启动成功后，访问：

```
http://localhost:8081/doc.html
```

## API文档

### 访问方式

项目集成了 Knife4j，提供了美观的 API 文档界面。

- **文档地址**: http://localhost:8081/doc.html
- **OpenAPI规范**: OpenAPI 3.0
- **支持功能**: 
  - 在线测试接口
  - 参数说明
  - 响应示例
  - 模型定义
  - 接口分组

### API分组

1. **用户管理**: 注册、登录、个人信息管理
2. **宠物管理**: 宠物档案CRUD、服务记录查询
3. **预约管理**: 多服务组合预约、预约查询、取消预约
4. **美容服务管理**: 美容服务查询、详情查看
5. **寄养服务管理**: 寄养服务查询、接领确认
6. **医疗服务管理**: 医疗服务查询、详情查看
7. **订单管理**: 订单生成、查询、支付
8. **门店与用品查询**: 门店查询、用品查询
9. **管理员-城市管理**: 城市CRUD
10. **管理员-员工管理**: 员工CRUD
11. **管理员-美容服务管理**: 美容服务CRUD
12. **管理员-用品管理**: 用品CRUD、门店铺货管理
13. **管理员-预约订单管理**: 预约审核、订单退款
14. **管理员-专项服务管理**: 美容/寄养/医疗服务管理
15. **管理员-数据统计**: 门店月报查询


## 核心业务流程

### 1. 客户预约服务流程

```
1. 客户注册/登录
2. 创建宠物档案
3. 查询门店和服务
4. 提交多服务组合预约（美容+寄养+医疗）
5. 管理员审核预约
6. 审核通过后生成订单
7. 客户支付订单
8. 服务执行
9. 服务完成
```

### 2. 订单支付流程

```
1. 客户提交预约或购买用品
2. 系统生成订单（计算总金额）
3. 客户选择支付方式（微信/支付宝/现金）
4. 系统生成支付凭证
5. 订单状态更新为"已支付"
6. 如果包含用品，自动扣减库存
```

### 3. 管理员审核流程

```
1. 管理员登录（身份验证）
2. 查询待审核预约列表
3. 审核预约：
   - 通过：可选分配员工，自动生成订单
   - 驳回：预约状态更新为"已取消"
4. 管理服务执行过程
5. 完成服务
```

### 4. 门店月报统计流程

```
1. 管理员登录
2. 选择统计月份
3. 系统查询数据库视图 v_store_monthly_report
4. 返回月报数据：
   - 新增客户数
   - 订单量统计
   - 营收统计
   - 热门服务类型
```

## 数据安全

### 1. 密码安全
- 密码使用 MD5+盐值 加密存储
- 不明文存储密码

### 2. 数据脱敏
- **手机号**: 仅显示前3位和后4位（如：138****5678）
- **邮箱**: 仅显示首字符（如：z****@example.com）
- **照片路径**: 部分脱敏处理

### 3. 权限控制
- **客户端**: 用户只能访问和操作自己的数据
- **管理员端**: 管理员只能操作本门店的数据
- 通过 UserID 和 StoreID 进行权限过滤

### 4. 事务控制
- 所有涉及多表操作的接口都使用 `@Transactional` 注解
- 确保数据一致性，要么全部成功，要么全部回滚

### 5. 并发控制
- 库存扣减使用数据库行锁（SELECT ... FOR UPDATE）
- 防止超卖问题


## 统一响应格式

所有接口均采用统一的响应格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

### 状态码说明

| 状态码 | 说明 | 示例 |
|--------|------|------|
| 200 | 操作成功 | 请求成功 |
| 400 | 参数错误 | 参数校验失败、业务规则校验失败 |
| 401 | 未授权 | 用户未登录或身份验证失败 |
| 403 | 无权限 | 无权限操作其他用户/门店的数据 |
| 404 | 资源不存在 | 预约不存在、订单不存在等 |
| 500 | 系统错误 | 服务器内部错误 |

## 测试指南

### 1. 使用 Knife4j 测试

1. 启动项目
2. 访问 http://localhost:8081/doc.html
3. 选择接口分组
4. 点击具体接口
5. 填写请求参数
6. 点击"执行"按钮
7. 查看响应结果

### 2. 使用 Postman 测试

1. 导入 API 文档（OpenAPI 3.0 格式）
2. 配置环境变量（如 baseUrl）
3. 按照接口文档填写请求参数
4. 发送请求
5. 查看响应结果

### 3. 测试数据准备

项目提供了测试数据脚本：

```bash
# 插入测试数据
mysql -u root -p pet_service_management < docs/插入测试数据.sql
```

测试数据包括：
- 测试用户
- 测试宠物
- 测试门店
- 测试员工
- 测试美容项目
- 测试用品

### 4. 测试场景示例

#### 场景1：客户完整预约流程

```
1. POST /api/users/register - 注册用户
2. POST /api/users/login - 登录
3. POST /api/pet - 创建宠物档案
4. GET /api/store - 查询门店
5. GET /api/beauty - 查询美容服务
6. POST /api/appointments - 提交预约
7. GET /api/appointments/user/{userId} - 查询预约列表
8. GET /api/appointments/{apptId} - 查询预约详情
```

#### 场景2：管理员审核订单流程

```
1. GET /api/admin/appointments/pending - 查询待审核预约
2. PUT /api/admin/appointments/{apptId}/review - 审核预约
3. GET /api/admin/appointments/orders - 查询订单列表
4. PUT /api/admin/services/beauty/{apptId}/complete - 完成美容服务
```

#### 场景3：订单支付流程

```
1. POST /api/orders/appointment - 生成关联预约的订单
2. GET /api/orders/{orderId} - 查询订单详情
3. PUT /api/orders/{orderId}/pay - 在线支付
4. GET /api/orders/user/{userId} - 查询订单列表
```


## 常见问题 (FAQ)

### Q1: 如何创建管理员账户？

A: 需要在数据库中创建两条记录：
1. 在 `user` 表中创建用户记录
2. 在 `employee` 表中创建员工记录，确保员工的姓名或手机号与用户匹配

```sql
-- 插入测试用户
INSERT INTO user (UserName, Phone, Address, Email) 
VALUES ('管理员张三', '13900000001', '北京市朝阳区', 'admin@example.com');

-- 插入测试员工（与用户匹配）
INSERT INTO employee (StoreID, EmpName, Position, EmpPhone, EntryTime, Salary) 
VALUES (1, '管理员张三', 'ADMIN', '13900000001', '2022-01-01', 10000.00);
```

### Q2: 为什么预约创建失败？

A: 请检查以下几点：
- 预约时间是否在门店营业时间内
- 如果分配了员工，员工是否在请假
- 寄养服务的日期是否有效（结束日期必须晚于开始日期）
- 医疗服务是否填写了症状描述

### Q3: 为什么无法取消预约？

A: 取消预约需要满足两个条件：
- 预约状态必须是"待服务"
- 距离预约时间必须大于等于24小时

### Q4: 订单总金额如何计算？

A: 订单总金额 = 美容服务费用 + 寄养费用 + 医疗费用 + 用品费用总和，所有计算使用 BigDecimal 保留2位小数。

### Q5: 库存不足时会发生什么？

A: 系统会拒绝订单创建，并返回错误信息，包含当前库存数量。已执行的数据库操作会自动回滚。

### Q6: 如何查看门店月报？

A: 
1. 使用管理员账号登录
2. 调用 `GET /api/admin/appointments/monthly-report` 接口
3. 传入统计月份（格式：YYYY-MM）
4. 系统返回月报数据

### Q7: 支付凭证号的格式是什么？

A: 支付凭证号格式为 `PAY_YYYYMMDDXXXX`，其中 YYYYMMDD 为支付日期，XXXX 为4位序列号。

### Q8: 如何调整门店用品库存？

A: 
1. 使用管理员账号登录
2. 调用 `PUT /api/admin/product-store/{relId}/stock` 接口
3. 传入调整类型（ADD=补货，REDUCE=减库存）和数量
4. 系统自动更新库存和上架状态

## 注意事项

1. **时间格式**: 所有时间字段使用 ISO 8601 格式，时区为 GMT+8
2. **金额精度**: 所有金额使用 BigDecimal 类型，保留2位小数
3. **事务回滚**: 订单创建或支付过程中任何步骤失败，所有数据库更改都会回滚
4. **库存管理**: 使用行锁防止并发问题，确保库存数据准确
5. **数据权限**: 用户只能查询和操作自己的数据，管理员只能操作本门店的数据
6. **营业时间**: 门店营业时间格式为 "HH:mm-HH:mm"，如 "09:00-21:00"
7. **后端计算**: 所有金额在后端计算，前端不应修改金额


## 部署指南

### 开发环境部署

1. 按照"快速开始"章节配置环境
2. 使用 `mvn spring-boot:run` 启动项目
3. 访问 http://localhost:8081/doc.html 测试接口

### 生产环境部署

#### 1. 打包项目

```bash
mvn clean package -DskipTests
```

生成的 jar 包位于 `target/PetServiceManagement-0.0.1-SNAPSHOT.jar`

#### 2. 配置外部配置文件

创建 `application-prod.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://your-production-host:3306/pet_service_management?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true
    username: your_username
    password: your_password

server:
  port: 8081

mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.nologging.NoLoggingImpl  # 生产环境关闭SQL日志

knife4j:
  enable: false  # 生产环境建议关闭API文档
```

#### 3. 运行项目

```bash
java -jar target/PetServiceManagement-0.0.1-SNAPSHOT.jar --spring.config.location=file:./application-prod.yml --spring.profiles.active=prod
```

#### 4. 使用 systemd 管理服务（Linux）

创建 `/etc/systemd/system/pet-service.service`：

```ini
[Unit]
Description=Pet Service Management System
After=network.target

[Service]
Type=simple
User=your_user
WorkingDirectory=/path/to/app
ExecStart=/usr/bin/java -jar /path/to/app/PetServiceManagement-0.0.1-SNAPSHOT.jar --spring.config.location=file:/path/to/app/application-prod.yml --spring.profiles.active=prod
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务：

```bash
sudo systemctl daemon-reload
sudo systemctl start pet-service
sudo systemctl enable pet-service
sudo systemctl status pet-service
```

#### 5. 使用 Docker 部署

创建 `Dockerfile`：

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/PetServiceManagement-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

构建镜像：

```bash
docker build -t pet-service:1.0 .
```

运行容器：

```bash
docker run -d \
  --name pet-service \
  -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/pet_service_management \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  pet-service:1.0
```

## 性能优化建议

### 1. 数据库优化

- 为常用查询字段添加索引
- 定期分析慢查询日志
- 使用连接池（HikariCP）
- 定期清理历史数据

### 2. 应用优化

- 启用 Redis 缓存热点数据
- 使用分页查询避免大数据量查询
- 异步处理耗时操作
- 使用 CDN 加速静态资源

### 3. 监控与日志

- 集成 Spring Boot Actuator 监控应用健康状态
- 使用 ELK 栈收集和分析日志
- 配置告警机制


## 开发规范

### 1. 代码规范

- 遵循阿里巴巴 Java 开发手册
- 使用 Lombok 简化代码
- 统一使用 UTF-8 编码
- 类名使用大驼峰命名法
- 方法名和变量名使用小驼峰命名法
- 常量使用全大写+下划线命名

### 2. 注释规范

- 所有公共类和方法必须添加 Javadoc 注释
- 复杂业务逻辑必须添加行内注释
- 数据库表和字段必须添加 COMMENT

### 3. 异常处理

- 使用统一的异常处理机制（GlobalExceptionHandler）
- 业务异常使用 BusinessException
- 不要捕获 Exception，应捕获具体异常类型
- 异常信息要清晰明确

### 4. 日志规范

- 使用 SLF4J + Logback
- 日志级别：DEBUG < INFO < WARN < ERROR
- 生产环境使用 INFO 级别
- 关键业务操作必须记录日志

### 5. 数据库规范

- 表名和字段名使用大驼峰命名法
- 主键统一使用 ID 后缀
- 外键统一使用 FK_ 前缀
- 唯一索引统一使用 UK_ 前缀
- 所有表必须有主键
- 所有表必须有创建时间字段

## 版本历史

### v1.0.0 (2025-12-18)

**第一阶段 - 客户账号与个人信息管理**
- ? 用户注册
- ? 用户登录
- ? 密码重置
- ? 个人信息修改
- ? 个人信息查询
- ? 数据脱敏（手机号、邮箱）

**第二阶段 - 宠物档案管理 + 服务与用品查询**
- ? 宠物档案 CRUD
- ? 宠物服务记录查询
- ? 美容服务查询（分页）
- ? 医疗服务查询（分页）
- ? 寄养服务查询（分页）
- ? 门店查询（按城市筛选，分页）
- ? 宠物用品查询（按门店、类型筛选，分页）

**第三阶段 - 预约管理 + 专项服务**
- ? 多服务组合预约（美容+寄养+医疗）
- ? 预约列表查询
- ? 预约详情查询
- ? 预约取消（24小时限制）
- ? 美容服务管理
- ? 寄养服务管理（接领确认）
- ? 医疗服务管理

**第四阶段 - 订单与支付管理**
- ? 生成关联预约的订单
- ? 生成纯用品购买订单
- ? 查询订单列表
- ? 查询订单详情
- ? 在线支付（微信/支付宝/现金）
- ? 支付凭证生成
- ? 库存管理（行锁防并发）

**第五阶段 - 门店管理员基础信息 + 服务与用品管理**
- ? 管理员身份验证
- ? 城市管理（CRUD）
- ? 员工管理（CRUD + 权限控制）
- ? 美容服务管理（CRUD）
- ? 宠物用品管理（CRUD）
- ? 门店铺货管理（创建/更新）
- ? 库存调整（补货/减库存）

**第六阶段 - 门店管理员预约订单管理 + 数据统计**
- ? 预约审核（通过/驳回）
- ? 订单退款（含库存恢复）
- ? 完成美容服务
- ? 更新寄养每日状态
- ? 创建医疗记录
- ? 补充医疗记录备注
- ? 门店月报查询（基于视图）

## 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 Apache License 2.0 许可证。详见 [LICENSE](../LICENSE) 文件。

## 联系方式

- **项目团队**: 数据库冲冲冲团队
- **邮箱**: 845103811@qq.com
- **项目地址**: http://localhost:8080
- **API文档**: http://localhost:8080/doc.html

## 致谢

感谢以下开源项目：

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis-Plus](https://baomidou.com/)
- [Knife4j](https://doc.xiaominfo.com/)
- [MySQL](https://www.mysql.com/)
- [Lombok](https://projectlombok.org/)

---

**文档版本**: v1.0.0  
**更新时间**: 2025-12-18  
**编写人**: 王鹤霖


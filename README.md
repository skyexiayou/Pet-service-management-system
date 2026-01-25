# 宠物合规处方用药及医药服务管理系统

## 项目概述

宠物合规处方用药及医药服务管理系统是一个基于Spring Boot开发的后台管理系统，主要用于管理宠物药品、处方用药及相关医药服务。该系统遵循合规性要求，确保宠物用药安全，提供完整的处方管理、药品库存管理、医疗记录管理等功能。

## 技术栈

- **Spring Boot 3.1.2**：后端框架
- **MyBatis-Plus 3.5.3.2**：ORM框架
- **MySQL 8.0**：数据库
- **JDK 17**：开发环境
- **Maven**：项目构建工具
- **Knife4j 4.4.0**：API文档
- **Lombok**：代码简化工具

## 系统架构

采用标准的MVC架构：
- **Controller**：处理HTTP请求，返回响应
- **Service**：业务逻辑层，处理核心业务
- **Mapper**：数据访问层，与数据库交互
- **Domain**：领域模型，包括实体类、DTO、VO等

## 核心功能

### 1. 宠物管理
- 宠物基本信息管理
- 宠物过敏药物记录
- 宠物既往病史管理

### 2. 员工管理
- 医生和管理员用户管理
- 医生执业资质管理
- 医生专业领域管理

### 3. 医疗预约管理
- 医疗预约创建
- 预约详情查询
- 预约取消

### 4. 医疗记录管理
- 病历创建
- 诊断结果记录
- 处方关联
- 用药说明和禁忌管理

### 5. 药品管理
- 药品基本信息管理
- 药品库存管理
- 药品剂量计算

### 6. 处方管理
- 处方创建
- 处方详情查询
- 处方有效性验证
- 处方药品明细管理

## 项目结构

```
├── src/main/java/cn/edu/xaut/
│   ├── config/          # 配置类
│   ├── controller/      # 控制器层
│   ├── domain/          # 领域模型
│   │   ├── dto/         # 数据传输对象
│   │   ├── entity/      # 实体类
│   │   └── vo/          # 视图对象
│   ├── exception/       # 异常处理
│   ├── mapper/          # 数据访问层
│   ├── service/         # 业务逻辑层
│   └── utils/           # 工具类
├── src/main/resources/  # 资源文件
│   ├── mapper/          # MyBatis映射文件
│   └── application.yml  # 应用配置
└── pom.xml              # Maven配置
```

## 核心实体类

### 1. PetDO（宠物表）
- `allergyDrug`：过敏药物
- `medicalHistory`：既往病史

### 2. EmployeeDO（员工表）
- `position`：职位（医生/管理员）
- `doctorQualification`：执业资质
- `professionalField`：专业领域

### 3. MedicalRecordDO（医疗记录表）
- `prescriptionId`：处方ID
- `drugTotalPrice`：药品总价格
- `drugUsage`：用药说明
- `drugTaboo`：用药禁忌

### 4. PetDrugDO（宠物药品表）
- 药品基本信息
- 适应症、用法用量、禁忌等

### 5. PetMedicalPrescriptionDO（宠物医疗处方表）
- 处方基本信息
- 处方状态、有效期等

## 核心工具类

### DrugDosageUtil（药物剂量计算工具）
- 提供宠物药物剂量计算功能
- 根据宠物体重、药物浓度等参数计算合适剂量

## API文档

系统集成了Knife4j API文档，启动项目后可通过以下地址访问：
```
http://localhost:8081/doc.html
```

## 启动项目

### 1. 配置数据库

在`application.yml`中配置数据库连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/pet_medicine?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: password
```

### 2. 编译项目

```bash
mvn compile
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

## 项目特点

1. **合规性**：严格遵循宠物用药合规要求
2. **安全性**：实现了完整的权限管理和数据验证
3. **可扩展性**：模块化设计，便于后续功能扩展
4. **易用性**：提供了详细的API文档，便于前端集成
5. **高性能**：采用MyBatis-Plus优化数据库访问
6. **可靠性**：实现了事务管理和异常处理

## 开发规范

1. **代码风格**：使用Lombok简化代码，遵循阿里巴巴Java开发规范
2. **命名规范**：采用驼峰命名法，见名知意
3. **注释规范**：关键代码添加注释，便于维护
4. **事务管理**：关键业务操作添加事务控制
5. **异常处理**：统一的异常处理机制
6. **数据验证**：使用Jakarta Bean Validation进行数据验证

## 未来规划

1. 增加宠物用药提醒功能
2. 实现药品过期预警
3. 增加处方模板功能
4. 实现药品追溯功能
5. 增加数据分析和报表功能

## 贡献

欢迎提交Issue和Pull Request！

## 许可证

MIT License

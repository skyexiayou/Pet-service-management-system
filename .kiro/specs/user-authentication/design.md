# 用户认证功能设计文档

## 概述

本文档描述了宠物服务管理系统的用户认证功能设计，包括用户注册、登录、验证码生成验证和JWT令牌管理。该功能基于Spring Boot框架，使用MyBatis进行数据访问，采用BCrypt进行密码加密，使用JWT进行会话管理。

设计遵循现有项目的架构模式：
- Controller层：处理HTTP请求和响应
- Service层：实现业务逻辑
- Mapper层：数据访问
- DTO/VO：数据传输对象
- 统一异常处理和响应格式

## 架构

### 分层架构

```
┌─────────────────────────────────────┐
│   Controller Layer                  │
│  - LoginController                  │
│  - RegisterController               │
│  - CaptchaController                │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Service Layer                     │
│  - LoginService                     │
│  - RegisterService                  │
│  - CaptchaService                   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Mapper Layer                      │
│  - UserMapper (已存在)              │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Database                          │
│  - user表 (已存在)                  │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│   Utility Layer                     │
│  - JwtUtils                         │
│  - PasswordEncoder                  │
└─────────────────────────────────────┘

┌─────────────────────────────────────┐
│   Cache Layer                       │
│  - CaptchaCache (内存Map)           │
└─────────────────────────────────────┘
```

### 请求流程

**注册流程：**
```
用户 → RegisterController → RegisterService → UserMapper → Database
                                  ↓
                          PasswordEncoder (加密密码)
```

**登录流程：**
```
用户 → LoginController → CaptchaService (验证验证码)
           ↓
       LoginService → UserMapper → Database
           ↓
       PasswordEncoder (验证密码)
           ↓
       JwtUtils (生成令牌)
           ↓
       返回JWT令牌
```

**验证码流程：**
```
用户 → CaptchaController → CaptchaService
                              ↓
                        生成验证码图片
                              ↓
                        存储到CaptchaCache
                              ↓
                        返回图片和令牌
```

## 组件和接口

### 1. Controller层

#### 1.1 RegisterController

**路径：** `src/main/java/cn/edu/xaut/controller/register/RegisterController.java`

**职责：** 处理用户注册请求

**接口：**
```java
@PostMapping("/api/register")
public ResponseVO<RegisterSuccessVO> register(@Valid @RequestBody UserRegisterDTO registerDTO)
```

**输入：** UserRegisterDTO（已存在）
- account: 账号
- userName: 用户名
- phone: 手机号
- email: 邮箱（可选）
- password: 密码
- address: 地址（可选）

**输出：** ResponseVO<RegisterSuccessVO>
- RegisterSuccessVO包含：id, account, userName, phone, email, createTime

**错误码：**
- 11: 账号已存在
- 12: 手机号已被注册
- 13: 邮箱已被注册

#### 1.2 LoginController

**路径：** `src/main/java/cn/edu/xaut/controller/login/LoginController.java`

**职责：** 处理用户登录请求

**接口：**
```java
@PostMapping("/api/login")
public ResponseVO<LoginSuccessVO> login(@Valid @RequestBody UserLoginDTO loginDTO)
```

**输入：** UserLoginDTO（需扩展）
- account: 账号
- password: 密码
- captchaToken: 验证码令牌
- captchaCode: 验证码输入

**输出：** ResponseVO<LoginSuccessVO>
- LoginSuccessVO包含：token (JWT令牌字符串)

**错误码：**
- 4: 请先获取验证码
- 5: 验证码错误
- 7: 账号或密码错误
- 8: 用户账户已被封禁

#### 1.3 CaptchaController

**路径：** `src/main/java/cn/edu/xaut/controller/captcha/CaptchaController.java`

**职责：** 处理验证码生成请求

**接口：**
```java
@GetMapping("/api/captcha")
public ResponseVO<CaptchaVO> generateCaptcha()
```

**输出：** ResponseVO<CaptchaVO>
- CaptchaVO包含：
  - captchaToken: 验证码令牌
  - captchaImage: Base64编码的验证码图片

### 2. Service层

#### 2.1 RegisterService

**路径：** `src/main/java/cn/edu/xaut/service/register/RegisterService.java`

**方法：**
```java
UserDO register(UserRegisterDTO registerDTO)
```

**职责：**
1. 验证账号是否已存在
2. 验证手机号是否已存在
3. 验证邮箱是否已存在（如果提供）
4. 使用BCrypt加密密码
5. 创建用户记录
6. 返回用户信息

#### 2.2 LoginService

**路径：** `src/main/java/cn/edu/xaut/service/login/LoginService.java`

**方法：**
```java
UserDO authenticate(String account, String password)
String generateToken(UserDO user)
```

**职责：**
1. 根据账号查询用户
2. 验证密码
3. 检查用户是否被封禁
4. 生成JWT令牌

#### 2.3 CaptchaService

**路径：** `src/main/java/cn/edu/xaut/service/captcha/CaptchaService.java`

**方法：**
```java
CaptchaVO generateCaptcha()
boolean verifyCaptcha(String captchaToken, String captchaCode)
```

**职责：**
1. 生成4位随机验证码
2. 生成验证码图片
3. 生成唯一令牌
4. 存储验证码到缓存
5. 验证验证码输入

### 3. 工具类

#### 3.1 JwtUtils

**路径：** `src/main/java/cn/edu/xaut/utils/JwtUtils.java`

**方法：**
```java
static String generateToken(Map<String, Object> claims)
static Claims parseToken(String token)
static boolean validateToken(String token)
```

**配置：**
- 密钥：从application.yml读取
- 过期时间：7天

**令牌声明：**
- username: 用户名
- isAdmin: 管理员标识
- iat: 签发时间
- exp: 过期时间

#### 3.2 PasswordEncoder

**路径：** `src/main/java/cn/edu/xaut/utils/PasswordEncoder.java`

**方法：**
```java
static String encode(String rawPassword)
static boolean matches(String rawPassword, String encodedPassword)
```

**实现：** 使用Spring Security的BCryptPasswordEncoder

### 4. 数据模型

#### 4.1 UserDO（已存在）

需要确认包含以下字段：
- id: 用户ID
- account: 账号
- userName: 用户名
- phone: 手机号
- email: 邮箱
- password: 加密后的密码
- address: 地址
- isAdmin: 管理员标识
- isBanned: 封禁状态
- createTime: 创建时间
- updateTime: 更新时间

#### 4.2 DTO扩展

**UserLoginDTO** 需要添加：
- captchaToken: String
- captchaCode: String

**新增VO：**

**RegisterSuccessVO:**
```java
{
  "id": Long,
  "account": String,
  "userName": String,
  "phone": String,
  "email": String,
  "createTime": LocalDateTime
}
```

**LoginSuccessVO:**
```java
{
  "token": String
}
```

**CaptchaVO:**
```java
{
  "captchaToken": String,
  "captchaImage": String  // Base64编码
}
```

### 5. 缓存设计

#### CaptchaCache

**实现：** ConcurrentHashMap<String, CaptchaEntry>

**CaptchaEntry:**
```java
{
  "code": String,           // 验证码文本
  "createTime": long        // 创建时间戳
}
```

**过期策略：** 5分钟后自动清理

**清理机制：** 定时任务每分钟清理过期验证码

## 数据模型

### 用户表（已存在）

需要确认user表包含以下字段：

```sql
CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account VARCHAR(20) UNIQUE NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  phone VARCHAR(11) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE,
  password VARCHAR(100) NOT NULL,
  address VARCHAR(200),
  is_admin TINYINT DEFAULT 0,
  is_banned TINYINT DEFAULT 0,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 正确性属性

*属性是一个特征或行为，应该在系统的所有有效执行中保持为真——本质上是关于系统应该做什么的正式声明。属性作为人类可读规范和机器可验证正确性保证之间的桥梁。*

### 注册相关属性

**属性 1：账号格式验证**
*对于任何* 提交的账号字符串，系统应该接受格式为"C"开头加5-15位数字或字母的账号，并拒绝其他格式
**验证需求：1.1**

**属性 2：手机号格式验证**
*对于任何* 提交的手机号字符串，系统应该接受11位以1开头的数字，并拒绝其他格式
**验证需求：1.2**

**属性 3：密码长度验证**
*对于任何* 提交的密码字符串，系统应该接受长度在6-20位之间的密码，并拒绝其他长度
**验证需求：1.3**

**属性 4：注册成功响应完整性**
*对于任何* 成功的注册请求，返回的响应应该包含用户ID、账号、用户名、手机号、邮箱和创建时间
**验证需求：1.7**

**属性 5：密码加密存储**
*对于任何* 注册的用户，数据库中存储的密码应该与原始密码不同且符合BCrypt格式
**验证需求：1.8**

### 登录相关属性

**属性 6：验证码验证**
*对于任何* 有效的验证码令牌和正确的验证码输入，验证应该成功；对于错误的输入，验证应该失败
**验证需求：2.1**

**属性 7：JWT令牌包含用户信息**
*对于任何* 成功登录生成的JWT令牌，解析后应该包含用户名和管理员标识声明
**验证需求：2.6**

**属性 8：成功登录返回令牌**
*对于任何* 有效的登录请求（正确的账号、密码和验证码），系统应该返回非空的JWT令牌字符串
**验证需求：2.7**

### 验证码相关属性

**属性 9：验证码格式**
*对于任何* 验证码生成请求，生成的验证码应该是4位数字或字母
**验证需求：3.1**

**属性 10：验证码令牌唯一性**
*对于任何* 多次验证码生成请求，每次生成的令牌应该是唯一的
**验证需求：3.2**

**属性 11：验证码响应完整性**
*对于任何* 验证码生成请求，响应应该包含验证码令牌和Base64编码的图片
**验证需求：3.3**

**属性 12：验证码大小写不敏感**
*对于任何* 验证码，使用不同大小写的输入应该得到相同的验证结果
**验证需求：3.4**

**属性 13：验证码一次性使用**
*对于任何* 验证码令牌，验证成功后再次使用相同令牌应该失败
**验证需求：3.5**

### JWT令牌相关属性

**属性 14：JWT包含必需声明**
*对于任何* 生成的JWT令牌，解析后应该包含用户名、管理员标识和过期时间声明
**验证需求：4.1, 4.2, 4.4**

**属性 15：JWT签名验证**
*对于任何* 使用正确密钥生成的JWT令牌，使用相同密钥验证应该成功；对于被篡改的令牌，验证应该失败
**验证需求：4.3, 4.5**

### 密码安全相关属性

**属性 16：BCrypt密码加密**
*对于任何* 注册或修改的密码，加密后的结果应该符合BCrypt格式（以$2a$或$2b$开头）
**验证需求：5.1**

**属性 17：BCrypt密码验证**
*对于任何* 正确的密码，BCrypt验证应该成功；对于错误的密码，验证应该失败
**验证需求：5.2**

**属性 18：密码不明文存储**
*对于任何* 存储在数据库中的密码，应该与原始密码不同
**验证需求：5.3**

### 错误处理相关属性

**属性 19：业务异常响应格式**
*对于任何* 业务异常，返回的响应应该包含错误码和错误消息字段
**验证需求：6.1**

## 错误处理

### 异常类型

1. **BusinessException**（已存在）
   - 用于所有业务逻辑错误
   - 包含错误码和错误消息

2. **新增错误码常量：**
```java
public static final int ACCOUNT_EXISTS = 11;
public static final int PHONE_EXISTS = 12;
public static final int EMAIL_EXISTS = 13;
public static final int CAPTCHA_NOT_FOUND = 4;
public static final int CAPTCHA_INVALID = 5;
public static final int AUTH_FAILED = 7;
public static final int USER_BANNED = 8;
```

### 全局异常处理

使用现有的GlobalExceptionHandler，添加：
- JWT令牌验证失败处理
- 参数验证失败处理
- 业务异常统一处理

### 日志记录

- 注册成功/失败
- 登录成功/失败
- 验证码生成和验证
- JWT令牌生成和验证
- 所有异常情况

## 测试策略

### 单元测试

**RegisterService测试：**
- 测试账号重复检查
- 测试手机号重复检查
- 测试邮箱重复检查
- 测试密码加密

**LoginService测试：**
- 测试账号不存在情况
- 测试密码错误情况
- 测试用户被封禁情况
- 测试JWT令牌生成

**CaptchaService测试：**
- 测试验证码生成
- 测试验证码验证
- 测试验证码过期清理

**JwtUtils测试：**
- 测试令牌生成
- 测试令牌解析
- 测试令牌验证
- 测试过期令牌

**PasswordEncoder测试：**
- 测试密码加密
- 测试密码验证

### 属性测试

使用JUnit-Quickcheck进行属性测试：

**属性测试 1：账号格式验证**
- **属性 1：账号格式验证**
- **验证需求：1.1**
- 生成随机账号字符串，验证系统正确接受/拒绝

**属性测试 2：手机号格式验证**
- **属性 2：手机号格式验证**
- **验证需求：1.2**
- 生成随机手机号字符串，验证系统正确接受/拒绝

**属性测试 3：密码长度验证**
- **属性 3：密码长度验证**
- **验证需求：1.3**
- 生成不同长度的密码，验证系统正确接受/拒绝

**属性测试 4：注册响应完整性**
- **属性 4：注册成功响应完整性**
- **验证需求：1.7**
- 生成随机有效注册数据，验证响应包含所有必需字段

**属性测试 5：密码加密**
- **属性 5：密码加密存储**
- **验证需求：1.8**
- 生成随机密码，验证加密后与原始密码不同

**属性测试 6：验证码验证**
- **属性 6：验证码验证**
- **验证需求：2.1**
- 生成验证码，测试正确和错误输入

**属性测试 7：JWT包含用户信息**
- **属性 7：JWT令牌包含用户信息**
- **验证需求：2.6**
- 生成随机用户数据，验证JWT包含正确声明

**属性测试 8：登录返回令牌**
- **属性 8：成功登录返回令牌**
- **验证需求：2.7**
- 生成有效登录请求，验证返回非空令牌

**属性测试 9：验证码格式**
- **属性 9：验证码格式**
- **验证需求：3.1**
- 多次生成验证码，验证都是4位字符

**属性测试 10：验证码令牌唯一性**
- **属性 10：验证码令牌唯一性**
- **验证需求：3.2**
- 多次生成验证码，验证令牌都不同

**属性测试 11：验证码响应完整性**
- **属性 11：验证码响应完整性**
- **验证需求：3.3**
- 生成验证码，验证响应包含必需字段

**属性测试 12：验证码大小写不敏感**
- **属性 12：验证码大小写不敏感**
- **验证需求：3.4**
- 生成验证码，用不同大小写测试

**属性测试 13：验证码一次性使用**
- **属性 13：验证码一次性使用**
- **验证需求：3.5**
- 验证成功后再次使用应该失败

**属性测试 14：JWT包含必需声明**
- **属性 14：JWT包含必需声明**
- **验证需求：4.1, 4.2, 4.4**
- 生成JWT，验证包含所有必需声明

**属性测试 15：JWT签名验证**
- **属性 15：JWT签名验证**
- **验证需求：4.3, 4.5**
- 生成JWT，验证签名；篡改后验证应该失败

**属性测试 16：BCrypt格式**
- **属性 16：BCrypt密码加密**
- **验证需求：5.1**
- 生成随机密码，验证加密结果符合BCrypt格式

**属性测试 17：BCrypt验证**
- **属性 17：BCrypt密码验证**
- **验证需求：5.2**
- 生成密码，验证正确密码通过、错误密码失败

**属性测试 18：密码不明文存储**
- **属性 18：密码不明文存储**
- **验证需求：5.3**
- 验证存储的密码与原始密码不同

**属性测试 19：异常响应格式**
- **属性 19：业务异常响应格式**
- **验证需求：6.1**
- 触发各种异常，验证响应格式

### 集成测试

- 完整注册流程测试
- 完整登录流程测试（包括验证码）
- JWT令牌在受保护接口的使用测试
- 错误场景端到端测试

### 测试配置

- 使用H2内存数据库进行测试
- 使用测试专用的JWT密钥
- 模拟验证码缓存
- 每个测试方法前清理数据

## 安全考虑

1. **密码安全**
   - 使用BCrypt加密，自动加盐
   - 密码强度验证（6-20位）
   - 永不返回密码字段

2. **JWT安全**
   - 使用强密钥（至少256位）
   - 设置合理的过期时间
   - 在敏感操作时验证令牌

3. **验证码安全**
   - 5分钟过期
   - 一次性使用
   - 大小写不敏感提升用户体验

4. **防止暴力破解**
   - 验证码机制
   - 可扩展：添加登录失败次数限制

5. **SQL注入防护**
   - 使用MyBatis参数化查询
   - 不拼接SQL字符串

6. **XSS防护**
   - 输入验证
   - 输出编码（由前端处理）

## 配置

### application.yml 新增配置

```yaml
jwt:
  secret: your-256-bit-secret-key-here-change-in-production
  expiration: 604800000  # 7天（毫秒）

captcha:
  length: 4
  expiration: 300000  # 5分钟（毫秒）
  width: 120
  height: 40
```

## 依赖

### 需要添加的Maven依赖

```xml
<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- BCrypt (Spring Security Crypto) -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>

<!-- 验证码生成 -->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>

<!-- 属性测试 -->
<dependency>
    <groupId>com.pholser</groupId>
    <artifactId>junit-quickcheck-core</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.pholser</groupId>
    <artifactId>junit-quickcheck-generators</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
```

## 实现注意事项

1. **复用现有代码**
   - 使用现有的UserMapper和UserDO
   - 使用现有的ResponseVO
   - 使用现有的BusinessException
   - 遵循现有的包结构和命名规范

2. **扩展现有类**
   - UserLoginDTO需要添加验证码相关字段
   - 确认UserDO包含所有必需字段

3. **日志记录**
   - 使用@Slf4j注解
   - 记录关键操作和异常

4. **参数验证**
   - 使用@Valid和Jakarta Validation注解
   - 在DTO中定义验证规则

5. **事务管理**
   - 注册操作使用@Transactional

6. **代码风格**
   - 遵循现有项目的代码风格
   - 使用Lombok简化代码
   - 添加适当的注释和文档

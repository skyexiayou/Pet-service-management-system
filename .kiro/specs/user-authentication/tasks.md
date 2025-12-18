# 用户认证功能实现任务列表

- [x] 1. 添加项目依赖和配置


  - 在pom.xml中添加JWT、BCrypt和验证码生成依赖
  - 在application.yml中添加JWT和验证码配置
  - _需求：所有功能的基础_



- [ ] 2. 创建工具类
- [ ] 2.1 实现PasswordEncoder工具类
  - 创建src/main/java/cn/edu/xaut/utils/PasswordEncoder.java
  - 实现encode()方法使用BCrypt加密密码
  - 实现matches()方法验证密码
  - _需求：1.8, 5.1, 5.2, 5.3_

- [ ]* 2.2 编写PasswordEncoder属性测试
  - **属性 16：BCrypt密码加密**
  - **属性 17：BCrypt密码验证**


  - **属性 18：密码不明文存储**
  - **验证需求：5.1, 5.2, 5.3**

- [ ] 2.3 实现JwtUtils工具类
  - 创建src/main/java/cn/edu/xaut/utils/JwtUtils.java
  - 实现generateToken()方法生成JWT令牌
  - 实现parseToken()方法解析JWT令牌
  - 实现validateToken()方法验证JWT令牌
  - 从配置文件读取密钥和过期时间
  - _需求：2.6, 2.7, 4.1, 4.2, 4.3, 4.4, 4.5, 4.6_

- [x]* 2.4 编写JwtUtils属性测试


  - **属性 14：JWT包含必需声明**
  - **属性 15：JWT签名验证**
  - **验证需求：4.1, 4.2, 4.3, 4.4, 4.5**



- [ ] 3. 扩展现有DTO和创建新VO
- [ ] 3.1 扩展UserLoginDTO
  - 在UserLoginDTO中添加captchaToken和captchaCode字段
  - 添加相应的验证注解


  - _需求：2.1, 2.2, 2.3_

- [ ] 3.2 创建响应VO类
  - 创建RegisterSuccessVO（包含id, account, userName, phone, email, createTime）
  - 创建LoginSuccessVO（包含token）
  - 创建CaptchaVO（包含captchaToken, captchaImage）
  - _需求：1.7, 2.7, 3.3_

- [ ] 4. 实现验证码功能
- [ ] 4.1 创建CaptchaService
  - 创建src/main/java/cn/edu/xaut/service/captcha/CaptchaService.java和实现类
  - 实现generateCaptcha()方法生成4位验证码和图片
  - 实现verifyCaptcha()方法验证验证码（不区分大小写）
  - 使用ConcurrentHashMap作为缓存存储验证码


  - 实现定时任务清理过期验证码（5分钟）
  - _需求：3.1, 3.2, 3.3, 3.4, 3.5_

- [ ]* 4.2 编写CaptchaService属性测试
  - **属性 9：验证码格式**
  - **属性 10：验证码令牌唯一性**


  - **属性 11：验证码响应完整性**
  - **属性 12：验证码大小写不敏感**
  - **属性 13：验证码一次性使用**
  - **验证需求：3.1, 3.2, 3.3, 3.4, 3.5**

- [ ] 4.3 创建CaptchaController
  - 创建src/main/java/cn/edu/xaut/controller/captcha/CaptchaController.java
  - 实现GET /api/captcha接口
  - 返回验证码图片和令牌
  - 添加Swagger文档注解
  - _需求：3.1, 3.2, 3.3_

- [ ] 5. 实现用户注册功能
- [ ] 5.1 创建RegisterService
  - 创建src/main/java/cn/edu/xaut/service/register/RegisterService.java和实现类
  - 实现register()方法
  - 检查账号是否已存在（错误码11）
  - 检查手机号是否已存在（错误码12）
  - 检查邮箱是否已存在（错误码13）
  - 使用PasswordEncoder加密密码
  - 调用UserMapper创建用户
  - _需求：1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8_



- [ ]* 5.2 编写RegisterService属性测试
  - **属性 1：账号格式验证**
  - **属性 2：手机号格式验证**
  - **属性 3：密码长度验证**
  - **属性 4：注册成功响应完整性**
  - **属性 5：密码加密存储**
  - **验证需求：1.1, 1.2, 1.3, 1.7, 1.8**


- [-]* 5.3 编写RegisterService单元测试

  - 测试账号重复场景（错误码11）
  - 测试手机号重复场景（错误码12）
  - 测试邮箱重复场景（错误码13）
  - _需求：1.4, 1.5, 1.6_

- [ ] 5.4 创建RegisterController
  - 创建src/main/java/cn/edu/xaut/controller/register/RegisterController.java
  - 实现POST /api/register接口
  - 使用@Valid验证UserRegisterDTO
  - 调用RegisterService.register()
  - 返回RegisterSuccessVO
  - 添加Swagger文档注解
  - 添加日志记录
  - _需求：1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8_

- [ ] 6. 实现用户登录功能
- [x] 6.1 创建LoginService

  - 创建src/main/java/cn/edu/xaut/service/login/LoginService.java和实现类

  - 实现authenticate()方法验证账号和密码
  - 检查用户是否存在（错误码7）
  - 使用PasswordEncoder验证密码（错误码7）
  - 检查用户是否被封禁（错误码8）
  - 实现generateToken()方法生成JWT令牌
  - _需求：2.4, 2.5, 2.6, 2.7_

- [ ]* 6.2 编写LoginService属性测试
  - **属性 7：JWT令牌包含用户信息**
  - **属性 8：成功登录返回令牌**
  - **验证需求：2.6, 2.7**

- [ ]* 6.3 编写LoginService单元测试
  - 测试账号不存在场景（错误码7）
  - 测试密码错误场景（错误码7）

  - 测试用户被封禁场景（错误码8）

  - _需求：2.4, 2.5_

- [ ] 6.4 创建LoginController
  - 创建src/main/java/cn/edu/xaut/controller/login/LoginController.java
  - 实现POST /api/login接口
  - 使用@Valid验证UserLoginDTO
  - 调用CaptchaService验证验证码（错误码4, 5）

  - 调用LoginService.authenticate()
  - 调用LoginService.generateToken()
  - 返回LoginSuccessVO
  - 添加Swagger文档注解
  - 添加日志记录
  - _需求：2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7_

- [ ]* 6.5 编写LoginController单元测试
  - 测试验证码令牌不存在场景（错误码4）
  - 测试验证码错误场景（错误码5）
  - _需求：2.2, 2.3_


- [ ] 7. 扩展异常处理
- [ ] 7.1 在BusinessException中添加新错误码常量
  - 添加ACCOUNT_EXISTS = 11


  - 添加PHONE_EXISTS = 12
  - 添加EMAIL_EXISTS = 13
  - 添加CAPTCHA_NOT_FOUND = 4
  - 添加CAPTCHA_INVALID = 5
  - 添加AUTH_FAILED = 7
  - 添加USER_BANNED = 8
  - _需求：1.4, 1.5, 1.6, 2.2, 2.3, 2.4, 2.5_

- [ ] 7.2 更新GlobalExceptionHandler
  - 确保BusinessException被正确处理
  - 确保参数验证异常返回400状态码
  - 添加JWT验证失败处理（返回401）
  - _需求：6.1, 6.2, 6.3_

- [ ]* 7.3 编写异常处理属性测试
  - **属性 19：业务异常响应格式**
  - **验证需求：6.1**

- [ ] 8. 验证UserDO和UserMapper
- [ ] 8.1 检查UserDO实体类
  - 确认包含所有必需字段（id, account, userName, phone, email, password, address, isAdmin, isBanned, createTime, updateTime）
  - 如果缺少字段则添加
  - _需求：所有功能的基础_



- [x] 8.2 检查UserMapper

  - 确认存在根据account查询用户的方法
  - 确认存在根据phone查询用户的方法
  - 确认存在根据email查询用户的方法
  - 如果缺少则添加相应的方法和XML映射
  - _需求：1.4, 1.5, 1.6, 2.4_



- [ ] 9. 集成测试
- [ ]* 9.1 编写完整注册流程集成测试
  - 测试成功注册流程
  - 测试各种验证失败场景
  - 测试重复注册场景
  - _需求：需求1所有验收标准_

- [ ]* 9.2 编写完整登录流程集成测试
  - 测试成功登录流程（包括验证码）
  - 测试各种失败场景
  - 测试JWT令牌的生成和验证
  - _需求：需求2所有验收标准_

- [ ]* 9.3 编写验证码功能集成测试
  - 测试验证码生成和验证流程
  - 测试验证码过期
  - 测试验证码一次性使用
  - _需求：需求3所有验收标准_

- [ ] 10. 检查点 - 确保所有测试通过
  - 确保所有测试通过，如有问题请询问用户

- [ ] 11. 文档和最终检查
- [ ] 11.1 添加API文档
  - 确保所有Controller方法都有完整的Swagger注解
  - 包含请求示例和响应示例
  - 包含错误码说明
  - _需求：所有接口_

- [ ] 11.2 代码审查
  - 检查代码风格是否符合项目规范
  - 检查是否有适当的日志记录
  - 检查是否有适当的注释
  - 检查是否有安全漏洞
  - _需求：所有功能_

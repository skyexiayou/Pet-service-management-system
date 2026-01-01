package cn.edu.xaut.controller.login;

import cn.edu.xaut.domain.dto.user.UserLoginDTO;
import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.auth.LoginSuccessVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.EmployeeMapper;
import cn.edu.xaut.service.captcha.CaptchaService;
import cn.edu.xaut.service.login.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "用户登录", description = "用户登录相关接口")
public class LoginController {

    private final LoginService loginService;
    private final CaptchaService captchaService;
    private final EmployeeMapper employeeMapper;

    @Operation(summary = "用户登录", description = "用户登录接口。错误码：4-请先获取验证码，5-验证码错误，7-账号或密码错误，8-用户账户已被封禁")
    @PostMapping("/login")
    public ResponseVO<LoginSuccessVO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        log.info("登录请求，账号: {}", loginDTO.getAccount());

        try {
            // 1. 验证验证码
            if (loginDTO.getCaptchaToken() == null || loginDTO.getCaptchaToken().isEmpty()) {
                log.warn("验证码令牌为空");
                throw new BusinessException(4, "请先获取验证码");
            }

            boolean captchaValid = captchaService.verifyCaptcha(
                    loginDTO.getCaptchaToken(), 
                    loginDTO.getCaptchaCode()
            );

            if (!captchaValid) {
                log.warn("验证码验证失败，账号: {}", loginDTO.getAccount());
                throw new BusinessException(5, "验证码错误");
            }

            log.info("验证码验证成功");

            // 2. 用户认证
            UserDO user = loginService.authenticate(loginDTO.getAccount(), loginDTO.getPassword());

            // 3. 生成JWT令牌
            String token = loginService.generateToken(user);

            // 4. 如果是管理员，尝试查找对应的员工ID
            Integer empId = null;
            if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                try {
                    // 通过用户名或手机号查找员工
                    QueryWrapper<EmployeeDO> empQuery = new QueryWrapper<>();
                    empQuery.and(wrapper -> wrapper
                            .eq("EmpName", user.getUserName())
                            .or()
                            .eq("EmpPhone", user.getPhone())
                    );
                    EmployeeDO employee = employeeMapper.selectOne(empQuery);
                    if (employee != null) {
                        empId = employee.getEmpId();
                        log.info("管理员 {} 关联员工ID: {}", user.getUserName(), empId);
                    }
                } catch (Exception e) {
                    log.warn("查找员工ID失败: {}", e.getMessage());
                }
            }

            // 5. 构建响应
            LoginSuccessVO successVO = LoginSuccessVO.builder()
                    .token(token)
                    .userId(user.getUserId())
                    .isAdmin(user.getIsAdmin() != null ? user.getIsAdmin() : 0)
                    .userName(user.getUserName())
                    .empId(empId)
                    .build();

            log.info("用户 {} 登录成功", loginDTO.getAccount());
            return ResponseVO.success(successVO);
        } catch (Exception e) {
            log.error("用户 {} 登录失败", loginDTO.getAccount(), e);
            throw e;
        }
    }

    @GetMapping("/test-password")
    public ResponseVO<Boolean> testPassword(@RequestParam String rawPassword, @RequestParam String encodedPassword) {
        log.info("测试密码验证，原始密码: {}, 加密密码: {}", rawPassword, encodedPassword);
        boolean matches = cn.edu.xaut.utils.PasswordEncoder.matches(rawPassword, encodedPassword);
        log.info("密码验证结果: {}", matches);
        return ResponseVO.success(matches);
    }

    @GetMapping("/generate-password")
    public ResponseVO<String> generatePassword(@RequestParam String rawPassword) {
        log.info("生成密码哈希，原始密码: {}", rawPassword);
        String encodedPassword = cn.edu.xaut.utils.PasswordEncoder.encode(rawPassword);
        log.info("生成的密码哈希: {}", encodedPassword);
        return ResponseVO.success(encodedPassword);
    }
}

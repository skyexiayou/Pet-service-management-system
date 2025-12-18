package cn.edu.xaut.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户注册DTO")
public class UserRegisterDTO {

    @Schema(description = "账号：首字符C + 6-16位数字/字母", required = true, example = "C123456")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^C[0-9a-zA-Z]{5,15}$", message = "账号格式错误：首字符C + 6-16位数字/字母")
    private String account;

    @Schema(description = "用户名", required = true, example = "张三")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Schema(description = "手机号", required = true, example = "13800138000")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    @Email(message = "邮箱格式错误")
    private String email;

    @Schema(description = "密码：6-20位", required = true, example = "password123")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;

    @Schema(description = "地址", example = "北京市朝阳区")
    private String address;
}

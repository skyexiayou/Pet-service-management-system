package cn.edu.xaut.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户登录DTO")
public class UserLoginDTO {

    @Schema(description = "账号", required = true, example = "C123456")
    @NotBlank(message = "账号不能为空")
    private String account;

    @Schema(description = "密码", required = true, example = "password123")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码令牌", required = true, example = "uuid-token-123")
    @NotBlank(message = "验证码令牌不能为空")
    private String captchaToken;

    @Schema(description = "验证码", required = true, example = "AB12")
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;
}

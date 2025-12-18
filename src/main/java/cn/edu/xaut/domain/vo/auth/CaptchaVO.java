package cn.edu.xaut.domain.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码返回VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "验证码信息")
public class CaptchaVO {

    @Schema(description = "验证码令牌", example = "uuid-token-123")
    private String captchaToken;

    @Schema(description = "Base64编码的验证码图片", example = "data:image/png;base64,iVBORw0KGgo...")
    private String captchaImage;
}

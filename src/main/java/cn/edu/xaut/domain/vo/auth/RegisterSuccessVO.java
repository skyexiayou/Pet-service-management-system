package cn.edu.xaut.domain.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 注册成功返回VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "注册成功返回信息")
public class RegisterSuccessVO {

    @Schema(description = "用户ID", example = "1")
    private Integer id;

    @Schema(description = "账号", example = "C123456")
    private String account;

    @Schema(description = "用户名", example = "张三")
    private String userName;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}

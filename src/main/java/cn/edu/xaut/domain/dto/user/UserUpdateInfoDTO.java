package cn.edu.xaut.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息修改DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息修改DTO")
public class UserUpdateInfoDTO {

    @Schema(description = "用户名", example = "张三")
    private String userName;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    @Email(message = "邮箱格式错误")
    private String email;

    @Schema(description = "地址", example = "北京市朝阳区")
    private String address;
}

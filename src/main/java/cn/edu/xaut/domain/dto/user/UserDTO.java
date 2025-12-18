package cn.edu.xaut.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * 用户信息DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息DTO")
public class UserDTO {

    @Schema(description = "用户名", required = true, example = "user123")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "地址", example = "北京市朝阳区")
    private String address;

    @Schema(description = "邮箱", example = "user@example.com")
    private String email;
}
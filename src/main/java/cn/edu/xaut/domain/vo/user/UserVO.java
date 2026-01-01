package cn.edu.xaut.domain.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息VO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息VO")
public class UserVO {

    @Schema(description = "用户ID", example = "1")
    private Integer userId;

    @Schema(description = "账号", example = "user123")
    private String account;

    @Schema(description = "用户名", example = "张三")
    private String userName;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "地址", example = "北京市朝阳区")
    private String address;

    @Schema(description = "注册时间")
    private Date registerTime;

    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    @Schema(description = "是否为管理员：0-普通用户，1-管理员", example = "0")
    private Integer isAdmin;

    @Schema(description = "是否被封禁：0-正常，1-已封禁", example = "0")
    private Integer isBanned;
}
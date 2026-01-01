package cn.edu.xaut.domain.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功返回VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "登录成功返回信息")
public class LoginSuccessVO {

    @Schema(description = "JWT令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "用户ID", example = "1")
    private Integer userId;
    
    @Schema(description = "是否为管理员：0-普通用户，1-管理员", example = "0")
    private Integer isAdmin;
    
    @Schema(description = "用户名", example = "张三")
    private String userName;
    
    @Schema(description = "员工ID（管理员专用）", example = "1")
    private Integer empId;
}

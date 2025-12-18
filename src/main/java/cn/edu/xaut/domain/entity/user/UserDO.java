package cn.edu.xaut.domain.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@ApiModel(value = "UserDO", description = "用户表实体类")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @TableId(value = "UserID", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID", dataType = "Integer", example = "1")
    private Integer userId;

    /** 用户名 */
    @TableField("UserName")
    @ApiModelProperty(value = "用户名", dataType = "String", example = "张三")
    private String userName;

    /** 手机号 */
    @TableField("Phone")
    @ApiModelProperty(value = "手机号", dataType = "String", example = "13800138000")
    private String phone;

    /** 地址 */
    @TableField("Address")
    @ApiModelProperty(value = "地址", dataType = "String", example = "北京市朝阳区")
    private String address;

    /** 注册时间 */
    @TableField("RegisterTime")
    @ApiModelProperty(value = "注册时间", dataType = "Date", example = "2023-01-01 10:00:00")
    private Date registerTime;

    /** 邮箱 */
    @TableField("Email")
    @ApiModelProperty(value = "邮箱", dataType = "String", example = "zhangsan@example.com")
    private String email;
}
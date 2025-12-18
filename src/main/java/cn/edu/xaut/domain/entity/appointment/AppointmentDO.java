package cn.edu.xaut.domain.entity.appointment;

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
 * 预约表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointment")
@ApiModel(value = "AppointmentDO", description = "预约表实体类")
public class AppointmentDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 预约ID */
    @TableId(value = "ApptID", type = IdType.AUTO)
    @ApiModelProperty(value = "预约ID", dataType = "Integer", example = "1")
    private Integer apptId;

    /** 用户ID */
    @TableField("UserID")
    @ApiModelProperty(value = "用户ID", dataType = "Integer", example = "1")
    private Integer userId;

    /** 宠物ID */
    @TableField("PetID")
    @ApiModelProperty(value = "宠物ID", dataType = "Integer", example = "1")
    private Integer petId;

    /** 门店ID */
    @TableField("StoreID")
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    /** 员工ID */
    @TableField("EmpID")
    @ApiModelProperty(value = "员工ID", dataType = "Integer", example = "1")
    private Integer empId;

    /** 预约时间 */
    @TableField("ApptTime")
    @ApiModelProperty(value = "预约时间", dataType = "Date", example = "2023-01-01 14:00:00")
    private Date apptTime;

    /** 预约状态 */
    @TableField("ApptStatus")
    @ApiModelProperty(value = "预约状态", dataType = "String", example = "已完成")
    private String apptStatus;

    /** 创建时间 */
    @TableField("CreateTime")
    @ApiModelProperty(value = "创建时间", dataType = "Date", example = "2023-01-01 10:00:00")
    private Date createTime;
}
package cn.edu.xaut.domain.entity.employee;

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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("employee")
@ApiModel(value = "EmployeeDO", description = "员工表实体类")
public class EmployeeDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 员工ID */
    @TableId(value = "EmpID", type = IdType.AUTO)
    @ApiModelProperty(value = "员工ID", dataType = "Integer", example = "1")
    private Integer empId;

    /** 门店ID */
    @TableField("StoreID")
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    /** 员工姓名 */
    @TableField("EmpName")
    @ApiModelProperty(value = "员工姓名", dataType = "String", example = "李四")
    private String empName;

    /** 职位 */
    @TableField("Position")
    @ApiModelProperty(value = "职位", dataType = "String", example = "美容师")
    private String position;

    /** 员工手机号 */
    @TableField("EmpPhone")
    @ApiModelProperty(value = "员工手机号", dataType = "String", example = "13800138001")
    private String empPhone;

    /** 入职时间 */
    @TableField("EntryTime")
    @ApiModelProperty(value = "入职时间", dataType = "Date", example = "2022-01-01")
    private Date entryTime;

    /** 薪资 */
    @TableField("Salary")
    @ApiModelProperty(value = "薪资", dataType = "BigDecimal", example = "5000.00")
    private BigDecimal salary;
}
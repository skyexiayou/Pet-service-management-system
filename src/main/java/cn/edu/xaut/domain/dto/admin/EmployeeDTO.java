package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工数据传输对象
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "员工DTO")
public class EmployeeDTO {

    @ApiModelProperty(value = "员工姓名", required = true, example = "李四")
    @NotBlank(message = "员工姓名不能为空")
    @Size(max = 50, message = "员工姓名长度不能超过50个字符")
    private String empName;

    @ApiModelProperty(value = "职位", required = true, example = "BEAUTICIAN", notes = "支持：DOCTOR（医生）/BEAUTICIAN（美容师）/ADMIN（管理员）")
    @NotBlank(message = "职位不能为空")
    @Pattern(regexp = "^(DOCTOR|BEAUTICIAN|ADMIN)$", message = "职位必须为DOCTOR、BEAUTICIAN或ADMIN")
    private String position;

    @ApiModelProperty(value = "员工手机号", required = true, example = "13800138001")
    @NotBlank(message = "员工手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String empPhone;

    @ApiModelProperty(value = "入职时间", example = "2022-01-01")
    private Date entryTime;

    @ApiModelProperty(value = "薪资", example = "5000.00")
    @DecimalMin(value = "0.00", message = "薪资不能为负数")
    private BigDecimal salary;
}
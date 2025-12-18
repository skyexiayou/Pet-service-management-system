package cn.edu.xaut.domain.entity.medicalrecord;

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
 * 医疗记录实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medicalRecord")
@ApiModel(value = "MedicalRecordDO", description = "病历记录实体类")
public class MedicalRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 病历ID */
    @TableId(value = "MedicalID", type = IdType.AUTO)
    @ApiModelProperty(value = "病历ID", dataType = "Integer", example = "1")
    private Integer medicalId;

    /** 宠物ID */
    @TableField("PetID")
    @ApiModelProperty(value = "宠物ID", dataType = "Integer", example = "1")
    private Integer petId;

    /** 员工ID */
    @TableField("EmpID")
    @ApiModelProperty(value = "员工ID", dataType = "Integer", example = "1")
    private Integer empId;

    /** 门店ID */
    @TableField("StoreID")
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    /** 诊疗时间 */
    @TableField("MedicalTime")
    @ApiModelProperty(value = "诊疗时间", dataType = "Date", example = "2023-01-01 10:00:00")
    private Date medicalTime;

    /** 诊断结果 */
    @TableField("Diagnosis")
    @ApiModelProperty(value = "诊断结果", dataType = "String", example = "感冒")
    private String diagnosis;

    /** 用药情况 */
    @TableField("Medication")
    @ApiModelProperty(value = "用药情况", dataType = "String", example = "阿莫西林")
    private String medication;

    /** 诊疗费用 */
    @TableField("MedicalFee")
    @ApiModelProperty(value = "诊疗费用", dataType = "BigDecimal", example = "200.00")
    private BigDecimal medicalFee;

    /** 复诊建议 */
    @TableField("FollowUpAdvice")
    @ApiModelProperty(value = "复诊建议", dataType = "String", example = "注意休息")
    private String followUpAdvice;
}
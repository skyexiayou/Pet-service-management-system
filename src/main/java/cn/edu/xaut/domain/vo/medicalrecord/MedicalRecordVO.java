package cn.edu.xaut.domain.vo.medicalrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗记录列表VO - 用于列表展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MedicalRecordVO", description = "医疗记录列表视图对象")
public class MedicalRecordVO {

    @ApiModelProperty(value = "医疗记录ID", example = "1")
    private Integer medicalId;

    @ApiModelProperty(value = "宠物ID", example = "1")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称", example = "小白")
    private String petName;

    @ApiModelProperty(value = "宠物品种", example = "金毛")
    private String breed;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "医生ID", example = "1")
    private Integer empId;

    @ApiModelProperty(value = "医生姓名", example = "李医生")
    private String empName;

    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称", example = "宠物医院总店")
    private String storeName;

    @ApiModelProperty(value = "就诊时间", example = "2025-01-15 10:00:00")
    private Date medicalTime;

    @ApiModelProperty(value = "诊断结果", example = "感冒发烧")
    private String diagnosis;

    @ApiModelProperty(value = "医疗费用", example = "200.00")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "状态", example = "已完成")
    private String status;
}

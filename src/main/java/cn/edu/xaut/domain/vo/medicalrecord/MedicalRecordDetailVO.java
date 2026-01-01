package cn.edu.xaut.domain.vo.medicalrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗记录详情VO - 用于详情展示
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MedicalRecordDetailVO", description = "医疗记录详情视图对象")
public class MedicalRecordDetailVO {

    @ApiModelProperty(value = "医疗记录ID", example = "1")
    private Integer medicalId;

    // 宠物信息
    @ApiModelProperty(value = "宠物ID", example = "1")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称", example = "小白")
    private String petName;

    @ApiModelProperty(value = "宠物品种", example = "金毛")
    private String breed;

    @ApiModelProperty(value = "宠物性别", example = "M")
    private String gender;

    @ApiModelProperty(value = "宠物出生日期", example = "2020-01-01")
    private Date birthDate;

    // 主人信息
    @ApiModelProperty(value = "用户ID", example = "1")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(value = "用户电话", example = "13800138000")
    private String userPhone;

    // 医生信息
    @ApiModelProperty(value = "医生ID", example = "1")
    private Integer empId;

    @ApiModelProperty(value = "医生姓名", example = "李医生")
    private String empName;

    @ApiModelProperty(value = "医生电话", example = "13900139000")
    private String empPhone;

    @ApiModelProperty(value = "医生职位", example = "兽医")
    private String empPosition;

    // 门店信息
    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称", example = "宠物医院总店")
    private String storeName;

    @ApiModelProperty(value = "门店地址", example = "北京市朝阳区xxx路xxx号")
    private String storeAddress;

    @ApiModelProperty(value = "门店电话", example = "010-12345678")
    private String storePhone;

    // 医疗信息
    @ApiModelProperty(value = "就诊时间", example = "2025-01-15 10:00:00")
    private Date medicalTime;

    @ApiModelProperty(value = "诊断结果", example = "感冒发烧")
    private String diagnosis;

    @ApiModelProperty(value = "用药情况", example = "阿莫西林 每日2次")
    private String medication;

    @ApiModelProperty(value = "医疗费用", example = "200.00")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "复诊建议", example = "一周后复查")
    private String followUpAdvice;

    @ApiModelProperty(value = "状态", example = "已完成")
    private String status;
}

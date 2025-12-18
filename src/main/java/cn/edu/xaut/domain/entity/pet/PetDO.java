package cn.edu.xaut.domain.entity.pet;

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
 * 宠物表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pet")
@ApiModel(value = "PetDO", description = "宠物表实体类")
public class PetDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 宠物ID */
    @TableId(value = "PetID", type = IdType.AUTO)
    @ApiModelProperty(value = "宠物ID", dataType = "Integer", example = "1")
    private Integer petId;

    /** 用户ID */
    @TableField("UserID")
    @ApiModelProperty(value = "用户ID", dataType = "Integer", example = "1")
    private Integer userId;

    /** 宠物姓名 */
    @TableField("PetName")
    @ApiModelProperty(value = "宠物姓名", dataType = "String", example = "小白")
    private String petName;

    /** 宠物品种 */
    @TableField("Breed")
    @ApiModelProperty(value = "宠物品种", dataType = "String", example = "金毛")
    private String breed;

    /** 性别 */
    @TableField("Gender")
    @ApiModelProperty(value = "性别", dataType = "String", example = "M")
    private String gender;

    /** 出生日期 */
    @TableField("BirthDate")
    @ApiModelProperty(value = "出生日期", dataType = "Date", example = "2020-01-01")
    private Date birthDate;

    /** 疫苗状态 */
    @TableField("VaccineStatus")
    @ApiModelProperty(value = "疫苗状态", dataType = "String", example = "已接种")
    private String vaccineStatus;

    /** 备注 */
    @TableField("Remarks")
    @ApiModelProperty(value = "备注", dataType = "String", example = "对花粉过敏")
    private String remarks;
}
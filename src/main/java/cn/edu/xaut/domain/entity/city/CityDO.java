package cn.edu.xaut.domain.entity.city;

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

/**
 * 城市表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("city")
@ApiModel(value = "CityDO", description = "城市表实体类")
public class CityDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 城市ID */
    @TableId(value = "CityID", type = IdType.AUTO)
    @ApiModelProperty(value = "城市ID", dataType = "Integer", example = "1")
    private Integer cityId;

    /** 省份 */
    @TableField("Province")
    @ApiModelProperty(value = "省份", dataType = "String", example = "北京市")
    private String province;

    /** 城市名称 */
    @TableField("CityName")
    @ApiModelProperty(value = "城市名称", dataType = "String", example = "北京市")
    private String cityName;

    /** 邮政编码 */
    @TableField("ZipCode")
    @ApiModelProperty(value = "邮政编码", dataType = "String", example = "100000")
    private String zipCode;
}
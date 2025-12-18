package cn.edu.xaut.domain.entity.petproduct;

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
 * 宠物商品表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("petProduct")
@ApiModel(value = "PetProductDO", description = "宠物商品表实体类")
public class PetProductDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    @TableId(value = "ProductID", type = IdType.AUTO)
    @ApiModelProperty(value = "商品ID", dataType = "Integer", example = "1")
    private Integer productId;

    /** 商品名称 */
    @TableField("ProductName")
    @ApiModelProperty(value = "商品名称", dataType = "String", example = "狗粮")
    private String productName;

    /** 商品类型 */
    @TableField("ProductType")
    @ApiModelProperty(value = "商品类型", dataType = "String", example = "食品")
    private String productType;

    /** 供应商 */
    @TableField("Supplier")
    @ApiModelProperty(value = "供应商", dataType = "String", example = "宠物用品公司")
    private String supplier;
}
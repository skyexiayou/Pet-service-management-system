package cn.edu.xaut.domain.entity.petproductstore;

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

/**
 * 商品-门店关联表实体类
 * 记录商品和门店的M:N关联关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("petProductStore")
@ApiModel(value = "PetProductStoreDO", description = "商品-门店关联表实体类")
public class PetProductStoreDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 关联ID */
    @TableId(value = "RelID", type = IdType.AUTO)
    @ApiModelProperty(value = "关联ID", dataType = "Integer", example = "1")
    private Integer relId;

    /** 商品ID */
    @TableField("ProductID")
    @ApiModelProperty(value = "商品ID", dataType = "Integer", example = "1")
    private Integer productId;

    /** 门店ID */
    @TableField("StoreID")
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    /** 价格 */
    @TableField("Price")
    @ApiModelProperty(value = "价格", dataType = "BigDecimal", example = "50.00")
    private BigDecimal price;

    /** 库存 */
    @TableField("StoreStock")
    @ApiModelProperty(value = "库存", dataType = "Integer", example = "100")
    private Integer storeStock;

    /** 上架状态 */
    @TableField("ShelfStatus")
    @ApiModelProperty(value = "上架状态", dataType = "String", example = "上架")
    private String shelfStatus;
}
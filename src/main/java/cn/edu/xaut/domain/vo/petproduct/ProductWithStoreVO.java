package cn.edu.xaut.domain.vo.petproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 带门店信息的商品VO
 * 用于前端商品列表展示，包含商品基础信息和门店关联信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "带门店信息的商品VO")
public class ProductWithStoreVO {
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Integer productId;
    
    @ApiModelProperty(value = "商品名称", example = "狗粮")
    private String productName;
    
    @ApiModelProperty(value = "商品类型", example = "食品")
    private String productType;
    
    @ApiModelProperty(value = "供应商", example = "宠物用品公司")
    private String supplier;
    
    @ApiModelProperty(value = "门店关联ID（用于购物车和订单）", example = "1")
    private Integer relId;
    
    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;
    
    @ApiModelProperty(value = "门店名称", example = "北京旗舰店")
    private String storeName;
    
    @ApiModelProperty(value = "价格", example = "99.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "库存数量", example = "100")
    private Integer storeStock;
    
    @ApiModelProperty(value = "上架状态（在售/下架/缺货）", example = "在售")
    private String shelfStatus;
}

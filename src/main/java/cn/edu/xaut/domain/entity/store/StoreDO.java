package cn.edu.xaut.domain.entity.store;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("store")
@ApiModel(value = "StoreDO", description = "门店表实体类")
public class StoreDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "StoreID", type = IdType.AUTO)
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    @TableField("CityID")
    @ApiModelProperty(value = "城市ID", dataType = "Integer", example = "1")
    private Integer cityId;

    @TableField("StoreName")
    @ApiModelProperty(value = "门店名称", dataType = "String", example = "宠宠之家")
    private String storeName;

    @TableField("StoreAddress")
    @ApiModelProperty(value = "门店地址", dataType = "String", example = "北京市朝阳区某某路1号")
    private String storeAddress;

    @TableField("StorePhone")
    @ApiModelProperty(value = "门店电话", dataType = "String", example = "010-12345678")
    private String storePhone;

    @TableField("BusinessHours")
    @ApiModelProperty(value = "营业时间", dataType = "String", example = "09:00-21:00")
    private String businessHours;
    
    @TableField(exist = false)
    @ApiModelProperty(value = "城市名称", dataType = "String", example = "北京市")
    private String cityName;
}
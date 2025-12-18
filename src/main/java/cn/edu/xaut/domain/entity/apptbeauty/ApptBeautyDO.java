package cn.edu.xaut.domain.entity.apptbeauty;

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
 * 预约-美容中间表实体类
 * 解耦预约与美容项目的M:N关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("apptBeauty")
@ApiModel(value = "ApptBeautyDO", description = "预约-美容中间表实体类")
public class ApptBeautyDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 关系ID */
    @TableId(value = "RelID", type = IdType.AUTO)
    @ApiModelProperty(value = "关系ID", dataType = "Integer", example = "1")
    private Integer relId;

    /** 关联预约ID */
    @TableField("ApptID")
    @ApiModelProperty(value = "关联预约ID", dataType = "Integer", example = "1")
    private Integer apptId;

    /** 关联美容项目ID */
    @TableField("BeautyID")
    @ApiModelProperty(value = "关联美容项目ID", dataType = "Integer", example = "1")
    private Integer beautyId;
}
package cn.edu.xaut.domain.entity.apptfoster;

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
 * 预约-寄养中间表实体类
 * 解耦预约与寄养记录的M:N关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("apptFoster")
@ApiModel(value = "ApptFosterDO", description = "预约-寄养中间表实体类")
public class ApptFosterDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 关系ID */
    @TableId(value = "RelID", type = IdType.AUTO)
    @ApiModelProperty(value = "关系ID", dataType = "Integer", example = "1")
    private Integer relId;

    /** 关联预约ID */
    @TableField("ApptID")
    @ApiModelProperty(value = "关联预约ID", dataType = "Integer", example = "1")
    private Integer apptId;

    /** 关联寄养记录ID */
    @TableField("FosterID")
    @ApiModelProperty(value = "关联寄养记录ID", dataType = "Integer", example = "1")
    private Integer fosterId;
}
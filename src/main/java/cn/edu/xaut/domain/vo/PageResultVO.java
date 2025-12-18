package cn.edu.xaut.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应VO
 * @param <T> 数据类型
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页响应VO")
public class PageResultVO<T> {

    @ApiModelProperty(value = "总记录数", example = "100")
    private Long total;

    @ApiModelProperty(value = "当前页数据列表")
    private List<T> list;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "每页记录数", example = "10")
    private Integer pageSize;
}
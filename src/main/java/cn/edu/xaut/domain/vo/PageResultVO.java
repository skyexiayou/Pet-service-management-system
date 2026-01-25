package cn.edu.xaut.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页结果VO")
public class PageResultVO<T> {

    @ApiModelProperty(value = "总记录数", example = "100")
    private Long total;

    @ApiModelProperty(value = "数据列表")
    private List<T> list;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize;

    public static <T> PageResultVO<T> builder() {
        return new PageResultVO<>();
    }

    public PageResultVO<T> total(Long total) {
        this.total = total;
        return this;
    }

    public PageResultVO<T> list(List<T> list) {
        this.list = list;
        return this;
    }

    public PageResultVO<T> pageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageResultVO<T> pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageResultVO<T> build() {
        return this;
    }
}
package cn.edu.xaut.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "统一返回结果")
public class ResponseVO<T> {

    @Schema(description = "状态码：200表示成功，其他表示失败", example = "200")
    private Integer code;

    @Schema(description = "返回消息", example = "操作成功")
    private String message;

    @Schema(description = "返回数据")
    private T data;

    /**
     * 成功返回
     */
    public static <T> ResponseVO<T> success(T data) {
        return ResponseVO.<T>builder()
                .code(200)
                .message("操作成功")
                .data(data)
                .build();
    }

    /**
     * 成功返回，无数据
     */
    public static <T> ResponseVO<T> success() {
        return ResponseVO.<T>builder()
                .code(200)
                .message("操作成功")
                .build();
    }

    /**
     * 失败返回
     */
    public static <T> ResponseVO<T> error(Integer code, String message) {
        return ResponseVO.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

    /**
     * 参数错误返回
     */
    public static <T> ResponseVO<T> paramError(String message) {
        return ResponseVO.<T>builder()
                .code(400)
                .message(message)
                .build();
    }

    /**
     * 未授权返回
     */
    public static <T> ResponseVO<T> unauthorized(String message) {
        return ResponseVO.<T>builder()
                .code(401)
                .message(message)
                .build();
    }

    /**
     * 业务错误返回
     */
    public static <T> ResponseVO<T> businessError(String message) {
        return ResponseVO.<T>builder()
                .code(500)
                .message(message)
                .build();
    }
}

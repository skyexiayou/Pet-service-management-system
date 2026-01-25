package cn.edu.xaut.exception;

/**
 * 库存业务异常类
 */
public class StockException extends BusinessException {

    // 库存相关错误码
    public static final int STOCK_INSUFFICIENT = 3001;
    public static final int STOCK_NOT_FOUND = 3002;
    public static final int STOCK_UPDATE_FAILED = 3003;
    public static final int STOCK_CHECK_FAILED = 3004;

    // 错误消息常量
    public static final String MSG_STOCK_INSUFFICIENT = "库存不足，无法完成支付";
    public static final String MSG_STOCK_NOT_FOUND = "药品库存信息不存在";
    public static final String MSG_STOCK_UPDATE_FAILED = "库存更新失败";
    public static final String MSG_STOCK_CHECK_FAILED = "库存校验失败";

    public StockException(String message) {
        super(message);
    }

    public StockException(Integer code, String message) {
        super(code, message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}

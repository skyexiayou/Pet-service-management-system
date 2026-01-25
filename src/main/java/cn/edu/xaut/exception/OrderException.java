package cn.edu.xaut.exception;

/**
 * 订单业务异常类
 */
public class OrderException extends BusinessException {

    // 订单相关错误码
    public static final int ORDER_NOT_FOUND = 1001;
    public static final int ORDER_STATUS_INVALID = 1002;
    public static final int ORDER_ALREADY_PAID = 1003;
    public static final int ORDER_ALREADY_CANCELLED = 1004;
    public static final int ORDER_CREATE_FAILED = 1005;
    public static final int PRESCRIPTION_REQUIRED = 1006;
    public static final int PRESCRIPTION_INVALID = 1007;
    public static final int PRESCRIPTION_EXPIRED = 1008;

    // 错误消息常量
    public static final String MSG_ORDER_NOT_FOUND = "订单不存在";
    public static final String MSG_ORDER_STATUS_INVALID = "订单状态无效，无法执行此操作";
    public static final String MSG_ORDER_ALREADY_PAID = "订单已支付，请勿重复支付";
    public static final String MSG_ORDER_ALREADY_CANCELLED = "订单已取消";
    public static final String MSG_ORDER_CREATE_FAILED = "订单创建失败";
    public static final String MSG_PRESCRIPTION_REQUIRED = "处方药订单必须关联有效处方";
    public static final String MSG_PRESCRIPTION_INVALID = "处方无效或不存在";
    public static final String MSG_PRESCRIPTION_EXPIRED = "处方已过期";
    public static final String MSG_PRESCRIPTION_ORDER_EXISTS = "该处方已生成订单，请勿重复生成";

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Integer code, String message) {
        super(code, message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

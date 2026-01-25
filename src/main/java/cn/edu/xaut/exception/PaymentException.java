package cn.edu.xaut.exception;

/**
 * Payment Business Exception
 */
public class PaymentException extends BusinessException {

    // Payment error codes
    public static final int PAYMENT_FAILED = 2001;
    public static final int PAYMENT_NOT_FOUND = 2002;
    public static final int PAYMENT_ALREADY_COMPLETED = 2003;
    public static final int PAYMENT_AMOUNT_MISMATCH = 2004;
    public static final int REFUND_FAILED = 2005;

    // Error message constants
    public static final String MSG_PAYMENT_FAILED = "Payment failed, please try again later";
    public static final String MSG_PAYMENT_NOT_FOUND = "Payment record not found";
    public static final String MSG_PAYMENT_ALREADY_COMPLETED = "Payment already completed, do not pay again";
    public static final String MSG_PAYMENT_AMOUNT_MISMATCH = "Payment amount does not match order amount";
    public static final String MSG_REFUND_FAILED = "Refund failed, please contact customer service";

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Integer code, String message) {
        super(code, message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}

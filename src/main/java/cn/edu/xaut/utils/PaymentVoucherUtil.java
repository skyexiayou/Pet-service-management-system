package cn.edu.xaut.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 支付凭证生成工具类
 */
public class PaymentVoucherUtil {

    private static final AtomicInteger sequence = new AtomicInteger(0);

    /**
     * 生成支付凭证号
     * 格式：PAY_YYYYMMDDXXXX
     * @return 支付凭证号
     */
    public static String generatePaymentVoucher() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int seq = sequence.incrementAndGet() % 10000;
        return String.format("PAY_%s%04d", date, seq);
    }
}

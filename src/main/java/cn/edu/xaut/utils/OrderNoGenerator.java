package cn.edu.xaut.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号/处方单号生成工具类
 * 生成规则：前缀 + 年月日（8位） + 3位自增序号
 * 每日序号从0重新开始，保证同一前缀下的序号独立
 */
public class OrderNoGenerator {

    private static final String PREFIX = "ORD";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // 存储不同前缀的日期（key=前缀，value=当前日期），解决多前缀共享日期的问题
    private static final Map<String, String> prefixDateMap = new HashMap<>();
    // 存储不同前缀的序号计数器（key=前缀，value=原子序号），解决多前缀共享序号的问题
    private static final Map<String, AtomicInteger> prefixSequenceMap = new HashMap<>();

    /**
     * 生成默认前缀（ORD）的订单号
     * @return 格式：ORD + 年月日 + 3位序号（如 ORD20260123001）
     */
    public static synchronized String generate() {
        return generate(PREFIX);
    }

    /**
     * 生成指定前缀的业务单号
     * @param prefix 自定义前缀（如 PRE-处方单、ORD-订单）
     * @return 格式：前缀 + 年月日 + 3位序号
     */
    public static synchronized String generate(String prefix) {
        // 初始化前缀对应的日期和序号（首次使用时）
        if (!prefixDateMap.containsKey(prefix)) {
            prefixDateMap.put(prefix, "");
            prefixSequenceMap.put(prefix, new AtomicInteger(0));
        }

        String today = LocalDate.now().format(DATE_FORMATTER);
        String currentDate = prefixDateMap.get(prefix);
        AtomicInteger sequence = prefixSequenceMap.get(prefix);

        // 跨天重置序号
        if (!today.equals(currentDate)) {
            prefixDateMap.put(prefix, today);
            sequence.set(0);
        }

        // 处理序号溢出（超过999则重置为1，避免格式错误）
        int seq = sequence.incrementAndGet();
        if (seq > 999) {
            sequence.set(1);
            seq = 1;
        }

        return String.format("%s%s%03d", prefix, today, seq);
    }

    /**
     * 生成处方单号（固定前缀PRE）
     * @return 格式：PRE + 年月日 + 3位序号（如 PRE20260123001）
     */
    public static synchronized String generatePrescriptionNo() {
        return generate("PRE");
    }
}
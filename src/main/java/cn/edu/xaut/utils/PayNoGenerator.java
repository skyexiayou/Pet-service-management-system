package cn.edu.xaut.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 支付流水号生成器
 */
public class PayNoGenerator {

    private static final String PREFIX = "PAY";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Random RANDOM = new Random();
    
    /**
     * 生成唯一支付流水号
     */
    public static String generate() {
        String today = LocalDate.now().format(DATE_FORMATTER);
        int randomNum = RANDOM.nextInt(90000000) + 10000000; // 8位随机数
        return String.format("%s%s%08d", PREFIX, today, randomNum);
    }
    
    /**
     * 生成带前缀的支付流水号
     */
    public static String generate(String prefix) {
        String today = LocalDate.now().format(DATE_FORMATTER);
        int randomNum = RANDOM.nextInt(90000000) + 10000000;
        return String.format("%s%s%08d", prefix, today, randomNum);
    }
}

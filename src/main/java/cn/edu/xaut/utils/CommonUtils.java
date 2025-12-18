package cn.edu.xaut.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 通用工具类
 */
public class CommonUtils {

    /**
     * 生成随机盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * MD5+盐值加密
     */
    public static String encryptPassword(String password, String salt) {
        try {
            // 使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 先将密码和盐值拼接
            String passwordWithSalt = password + salt;
            // 进行加密
            byte[] hash = md.digest(passwordWithSalt.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 手机号脱敏
     * 格式：138****1234
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 邮箱脱敏
     * 格式：z****@example.com
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        if (parts[0].length() <= 1) {
            return "*@" + parts[1];
        }
        return parts[0].substring(0, 1) + "****@" + parts[1];
    }

    /**
     * 验证手机号格式
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        return phone.matches("^1[3-9]\\d{9}$");
    }

    /**
     * 验证邮箱格式
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * 验证账号格式：首字符C + 6-16位数字/字母
     */
    public static boolean isValidAccount(String account) {
        if (account == null) {
            return false;
        }
        return account.matches("^C[0-9a-zA-Z]{5,15}$");
    }
}
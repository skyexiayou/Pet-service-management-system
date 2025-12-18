package cn.edu.xaut.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额计算工具类
 * 提供订单总金额、商品小计金额等金额相关的计算方法
 */
public class AmountCalculationUtil {

    /**
     * 计算订单总金额
     * 累加美容费、寄养费、医疗费、商品费，最终保留2位小数（四舍五入）
     *
     * @param beautyFee 美容费
     * @param fosterFee 寄养费
     * @param medicalFee 医疗费
     * @param productFee 商品费
     * @return 订单总金额（保留2位小数）
     */
    public static BigDecimal calculateTotalAmount(
            BigDecimal beautyFee,
            BigDecimal fosterFee,
            BigDecimal medicalFee,
            BigDecimal productFee
    ) {
        BigDecimal total = BigDecimal.ZERO;

        if (beautyFee != null) {
            total = total.add(beautyFee);
        }
        if (fosterFee != null) {
            total = total.add(fosterFee);
        }
        if (medicalFee != null) {
            total = total.add(medicalFee);
        }
        if (productFee != null) {
            total = total.add(productFee);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算商品小计金额
     * 单价 × 数量，最终保留2位小数（四舍五入）；参数为空时返回0
     *
     * @param quantity 商品数量
     * @param price 商品单价
     * @return 商品小计金额（保留2位小数）
     */
    public static BigDecimal calculateProductSubtotal(Integer quantity, BigDecimal price) {
        if (quantity == null || price == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}
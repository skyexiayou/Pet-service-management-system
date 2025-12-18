package cn.edu.xaut.utils;

import cn.edu.xaut.domain.entity.leaverecord.LeaveRecordDO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 时间校验工具类
 */
public class TimeValidationUtil {

    /**
     * 校验预约时间是否在营业时间内
     *
     * @param apptTime      预约时间
     * @param businessHours 营业时间（格式：09:00-21:00）
     * @return true=在营业时间内，false=不在营业时间内
     */
    public static boolean isWithinBusinessHours(Date apptTime, String businessHours) {
        if (apptTime == null || businessHours == null || businessHours.trim().isEmpty()) {
            return false;
        }

        try {
            // 解析营业时间
            String[] hours = businessHours.split("-");
            if (hours.length != 2) {
                return false;
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // 获取预约时间的日期部分
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateFormat.format(apptTime);

            // 构造当天的营业开始和结束时间
            Date startTime = dateTimeFormat.parse(dateStr + " " + hours[0].trim());
            Date endTime = dateTimeFormat.parse(dateStr + " " + hours[1].trim());

            // 判断预约时间是否在营业时间内
            return !apptTime.before(startTime) && !apptTime.after(endTime);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 校验员工是否在指定时间段内请假
     *
     * @param empId        员工ID
     * @param apptTime     预约时间
     * @param leaveRecords 请假记录列表
     * @return true=请假中，false=未请假
     */
    public static boolean isEmployeeOnLeave(Integer empId, Date apptTime, List<LeaveRecordDO> leaveRecords) {
        if (empId == null || apptTime == null || leaveRecords == null || leaveRecords.isEmpty()) {
            return false;
        }

        for (LeaveRecordDO record : leaveRecords) {
            if (record.getEmpId().equals(empId) &&
                    "已通过".equals(record.getApproveStatus()) &&
                    !apptTime.before(record.getStartTime()) &&
                    !apptTime.after(record.getEndTime())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 校验是否满足24小时取消限制
     *
     * @param apptTime 预约时间
     * @return true=可以取消，false=不能取消
     */
    public static boolean canCancelWithin24Hours(Date apptTime) {
        if (apptTime == null) {
            return false;
        }

        Date serverTime = getServerTime();
        long diff = apptTime.getTime() - serverTime.getTime();
        long hours = diff / (1000 * 60 * 60);

        // 距离预约时间大于等于24小时才能取消
        return hours >= 24;
    }

    /**
     * 获取服务器当前时间
     *
     * @return 当前时间
     */
    public static Date getServerTime() {
        return new Date(System.currentTimeMillis());
    }
}

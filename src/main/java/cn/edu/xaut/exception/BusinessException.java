package cn.edu.xaut.exception;

public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = 400;
    }

    public Integer getCode() {
        return code;
    }

    public static final String APPT_TIME_NOT_IN_BUSINESS_HOURS = "Appointment time is not within business hours";
    public static final String EMPLOYEE_ON_LEAVE = "Employee is on leave during appointment time";
    public static final String TIME_SLOT_CONFLICT = "Time slot is already booked";
    public static final String FOSTER_DATE_INVALID = "Foster end date must be after start date";
    public static final String MEDICAL_SYMPTOM_EMPTY = "Medical symptom cannot be empty";
    public static final String CANCEL_TIME_LIMIT = "Cannot cancel within 24 hours of appointment";
    public static final String APPT_STATUS_INVALID = "Appointment status is not pending";
    public static final String FOSTER_STATUS_INVALID = "Foster status is not in progress";
    public static final String PET_NOT_FOUND = "Pet not found";
    public static final String STORE_NOT_FOUND = "Store not found";
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
    public static final String FOSTER_NOT_FOUND = "Foster record not found";
    public static final String MEDICAL_NOT_FOUND = "Medical record not found";
}

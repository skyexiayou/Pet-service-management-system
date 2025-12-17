package cn.edu.xaut.domain.dto;

import lombok.Data;

@Data
public class StoreDTO {
    private Integer cityId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String businessHours;
}
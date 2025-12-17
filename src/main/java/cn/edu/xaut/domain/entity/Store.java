package cn.edu.xaut.domain.entity;

import lombok.Data;

@Data
public class Store {
    private Integer storeId;
    private Integer cityId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String businessHours;
}

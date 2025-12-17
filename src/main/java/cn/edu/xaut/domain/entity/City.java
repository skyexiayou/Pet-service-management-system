package cn.edu.xaut.domain.entity;

import lombok.Data;

@Data
public class City {
    private Integer cityId;
    private String province;
    private String cityName;
    private String zipCode;
}
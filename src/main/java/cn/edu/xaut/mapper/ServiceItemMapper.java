package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.ServiceItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceItemMapper {
    ServiceItem selectServiceItemById(@Param("serviceId") Integer serviceId);
    List<ServiceItem> selectServiceItemsByType(@Param("serviceType") String serviceType);
    List<ServiceItem> selectAllServiceItems();
    int insertServiceItem(ServiceItem serviceItem);
    int updateServiceItem(ServiceItem serviceItem);
    int deleteServiceItem(@Param("serviceId") Integer serviceId);
}
package cn.edu.xaut.service.serviceitem;

import cn.edu.xaut.domain.dto.ServiceItemDTO;
import cn.edu.xaut.domain.entity.ServiceItem;

import java.util.List;

public interface ServiceItemService {

    ServiceItem getServiceItemById(Integer serviceId);

    List<ServiceItem> getServiceItemsByType(String serviceType);

    List<ServiceItem> getAllServiceItems();

    Integer createServiceItem(ServiceItemDTO serviceItemDTO);

    Integer updateServiceItem(Integer serviceId, ServiceItemDTO serviceItemDTO);

    Integer deleteServiceItem(Integer serviceId);
}

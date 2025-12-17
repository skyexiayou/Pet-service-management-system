package cn.edu.xaut.service.serviceitem.impl;

import cn.edu.xaut.domain.dto.ServiceItemDTO;
import cn.edu.xaut.domain.entity.ServiceItem;
import cn.edu.xaut.mapper.ServiceItemMapper;
import cn.edu.xaut.service.serviceitem.ServiceItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    public ServiceItem getServiceItemById(Integer serviceId) {
        return serviceItemMapper.selectServiceItemById(serviceId);
    }

    @Override
    public List<ServiceItem> getServiceItemsByType(String serviceType) {
        return serviceItemMapper.selectServiceItemsByType(serviceType);
    }

    @Override
    public List<ServiceItem> getAllServiceItems() {
        return serviceItemMapper.selectAllServiceItems();
    }

    @Override
    public Integer createServiceItem(ServiceItemDTO serviceItemDTO) {
        ServiceItem serviceItem = new ServiceItem();
        BeanUtils.copyProperties(serviceItemDTO, serviceItem);
        return serviceItemMapper.insertServiceItem(serviceItem);
    }

    @Override
    public Integer updateServiceItem(Integer serviceId, ServiceItemDTO serviceItemDTO) {
        ServiceItem serviceItem = new ServiceItem();
        BeanUtils.copyProperties(serviceItemDTO, serviceItem);
        serviceItem.setServiceId(serviceId);
        return serviceItemMapper.updateServiceItem(serviceItem);
    }

    @Override
    public Integer deleteServiceItem(Integer serviceId) {
        return serviceItemMapper.deleteServiceItem(serviceId);
    }
}

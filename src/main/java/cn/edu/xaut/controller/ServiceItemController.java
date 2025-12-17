package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.ServiceItemDTO;
import cn.edu.xaut.domain.entity.ServiceItem;
import cn.edu.xaut.service.serviceitem.ServiceItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-items")
@Api(tags = "Service Item Controller")
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    @ApiOperation("Get all service items")
    @GetMapping
    public List<ServiceItem> getAllServiceItems() {
        return serviceItemService.getAllServiceItems();
    }

    @ApiOperation("Get service item by ID")
    @GetMapping("/{id}")
    public ServiceItem getServiceItemById(@PathVariable("id") Integer serviceId) {
        return serviceItemService.getServiceItemById(serviceId);
    }

    @ApiOperation("Get service items by type")
    @GetMapping("/type/{type}")
    public List<ServiceItem> getServiceItemsByType(@PathVariable("type") String serviceType) {
        return serviceItemService.getServiceItemsByType(serviceType);
    }

    @ApiOperation("Create service item")
    @PostMapping
    public Integer createServiceItem(@RequestBody ServiceItemDTO serviceItemDTO) {
        return serviceItemService.createServiceItem(serviceItemDTO);
    }

    @ApiOperation("Update service item")
    @PutMapping("/{id}")
    public Integer updateServiceItem(@PathVariable("id") Integer serviceId, @RequestBody ServiceItemDTO serviceItemDTO) {
        return serviceItemService.updateServiceItem(serviceId, serviceItemDTO);
    }

    @ApiOperation("Delete service item")
    @DeleteMapping("/{id}")
    public Integer deleteServiceItem(@PathVariable("id") Integer serviceId) {
        return serviceItemService.deleteServiceItem(serviceId);
    }
}
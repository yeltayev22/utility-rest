package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.model.dto.ServiceDto;
import kz.yeltayev.utility.model.entity.Service;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/services")
    public List<ServiceDto> fetchServices() {
        return serviceService.fetchServices();
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceDto> fetchServiceById(@PathVariable(value = "id") Long serviceId) throws ResourceNotFoundException {
        ServiceDto serviceDto = serviceService.fetchServiceById(serviceId);
        return ResponseEntity.ok().body(serviceDto);
    }

    @PostMapping("/services")
    public ServiceDto addService(@Valid @RequestBody Service service) {
        return serviceService.addService(service);
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceDto> updateService(@PathVariable(value = "id") Long serviceId,
                                                    @Valid @RequestBody Service serviceDetails) throws ResourceNotFoundException {
        ServiceDto updatedService = serviceService.updateService(serviceId, serviceDetails);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/services/{id}")
    public Map<String, Boolean> deleteService(@PathVariable(value = "id") Long serviceId)
            throws ResourceNotFoundException {
        return serviceService.deleteService(serviceId);
    }
}

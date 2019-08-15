package kz.yeltayev.utility.services;

import kz.yeltayev.utility.dto.ServiceDto;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public ServiceDto addService(kz.yeltayev.utility.entity.Service service) {
        return convertToDto(serviceRepository.save(service));
    }

    @Transactional
    public List<ServiceDto> fetchServices() {
        List<kz.yeltayev.utility.entity.Service> services = serviceRepository.findAll();
        return convertToListServiceDto(services);
    }

    @Transactional
    public ServiceDto fetchServiceById(Long serviceId) throws ResourceNotFoundException {
        return convertToDto(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id : " + serviceId)));
    }

    @Transactional
    public ServiceDto updateService(Long serviceId, kz.yeltayev.utility.entity.Service serviceDetails) throws ResourceNotFoundException {
        kz.yeltayev.utility.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id : " + serviceId));

        service.setServiceName(serviceDetails.getServiceName());

        kz.yeltayev.utility.entity.Service updatedService = serviceRepository.save(service);
        return convertToDto(updatedService);
    }

    @Transactional
    public Map<String, Boolean> deleteService(Long serviceId) throws ResourceNotFoundException {
        kz.yeltayev.utility.entity.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id : " + serviceId));

        serviceRepository.delete(service);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private List<ServiceDto> convertToListServiceDto(List<kz.yeltayev.utility.entity.Service> services) {
        if (services.isEmpty())
            return new ArrayList<>();
        return services.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ServiceDto convertToDto(kz.yeltayev.utility.entity.Service service) {
        ServiceDto serviceDto = modelMapper.map(service, ServiceDto.class);
        return serviceDto;
    }
}

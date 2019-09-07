package kz.yeltayev.utility.services;

import kz.yeltayev.utility.model.dto.ServiceDto;
import kz.yeltayev.utility.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    public List<ServiceDto> fetchServices() {
        List<kz.yeltayev.utility.model.entity.Service> services = serviceRepository.findAll();
        return convertToListServiceDto(services);
    }

    private List<ServiceDto> convertToListServiceDto(List<kz.yeltayev.utility.model.entity.Service> services) {
        if (services.isEmpty())
            return new ArrayList<>();
        return services.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ServiceDto convertToDto(kz.yeltayev.utility.model.entity.Service service) {
        ServiceDto serviceDto = modelMapper.map(service, ServiceDto.class);
        return serviceDto;
    }
}

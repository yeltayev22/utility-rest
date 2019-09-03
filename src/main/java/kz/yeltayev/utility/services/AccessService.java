package kz.yeltayev.utility.services;

import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.dto.AccessDto;
import kz.yeltayev.utility.model.entity.Access;
import kz.yeltayev.utility.model.entity.User;
import kz.yeltayev.utility.model.request.AccessRequest;
import kz.yeltayev.utility.repository.AccessRepository;
import kz.yeltayev.utility.repository.ServiceRepository;
import kz.yeltayev.utility.repository.StreetRepository;
import kz.yeltayev.utility.repository.UserRepository;
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
public class AccessService {

    private AccessRepository accessRepository;
    private UserRepository userRepository;
    private StreetRepository streetRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccessService(AccessRepository accessRepository,
                         UserRepository userRepository,
                         StreetRepository streetRepository,
                         ServiceRepository serviceRepository) {
        this.accessRepository = accessRepository;
        this.userRepository = userRepository;
        this.streetRepository = streetRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public void addAccesses(List<AccessRequest> accesses) throws ResourceNotFoundException {
        for (AccessRequest access : accesses) {
            List<Access> existingAccesses = accessRepository.findAccessesByUser_Id(access.getUserId());
            for (Access existingAccess : existingAccesses) {
                accessRepository.delete(existingAccess);
            }
        }
        for (AccessRequest access : accesses) {
            User user = userRepository.findById(access.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + access.getUserId()));
            kz.yeltayev.utility.model.entity.Service service = serviceRepository.findById(access.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id : " + access.getServiceId()));
            Access newAccess = new Access();

            newAccess.setUser(user);
            newAccess.setService(service);
            accessRepository.save(newAccess);
        }
    }

    @Transactional
    public List<AccessDto> fetchAccesses() {
        List<Access> accesses = accessRepository.findAll();
        return convertToListAccessDto(accesses);
    }

    @Transactional
    public AccessDto fetchAccessById(Long accessId) throws ResourceNotFoundException {
        return convertToDto(accessRepository.findById(accessId)
                .orElseThrow(() -> new ResourceNotFoundException("Access not found for this id : " + accessId)));
    }

    @Transactional
    public AccessDto updateAccess(Long accessId, Access accessDetails) throws ResourceNotFoundException {
        Access access = accessRepository.findById(accessId)
                .orElseThrow(() -> new ResourceNotFoundException("Access not found for this id : " + accessId));

        access.setUser(accessDetails.getUser());
//        access.setStreet(accessDetails.getStreet());
        access.setService(accessDetails.getService());

        Access updatedAccess = accessRepository.save(access);
        return convertToDto(updatedAccess);
    }

    @Transactional
    public Map<String, Boolean> deleteAccess(Long accessId) throws ResourceNotFoundException {
        Access access = accessRepository.findById(accessId)
                .orElseThrow(() -> new ResourceNotFoundException("Access not found for this id : " + accessId));

        accessRepository.delete(access);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private List<AccessDto> convertToListAccessDto(List<Access> accesses) {
        if (accesses.isEmpty())
            return new ArrayList<>();
        return accesses.stream().map(access -> {
                    try {
                        return convertToDto(access);
                    } catch (ResourceNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());
    }

    private AccessDto convertToDto(Access access) throws ResourceNotFoundException {
        AccessDto accessDto = modelMapper.map(access, AccessDto.class);

        User user = userRepository.findById(access.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + access.getUser().getId()));
//        Street street = streetRepository.findById(access.getStreet().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Street not found for this id : " + access.getStreet().getId()));
        kz.yeltayev.utility.model.entity.Service service = serviceRepository.findById(access.getService().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found for this id : " + access.getService().getClass()));

//        accessDto.setStreetId(street.getId());
//        accessDto.setStreetName(street.getStreetName());

        accessDto.setServiceId(service.getId());
        accessDto.setServiceName(service.getServiceName());

        return accessDto;
    }
}

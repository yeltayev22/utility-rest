package kz.yeltayev.utility.services;

import kz.yeltayev.utility.dto.AccountDto;
import kz.yeltayev.utility.dto.StreetDto;
import kz.yeltayev.utility.entity.Account;
import kz.yeltayev.utility.entity.Street;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.repository.StreetRepository;
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
public class StreetService {

    @Autowired
    private ModelMapper modelMapper;

    private StreetRepository streetRepository;

    @Autowired
    public StreetService(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    @Transactional
    public StreetDto addStreet(Street street) {
        return convertToDto(streetRepository.save(street));
    }

    @Transactional
    public List<StreetDto> fetchStreets() {
        List<Street> streets = streetRepository.findAll();
        return convertToListStreetDto(streets);
    }

    @Transactional
    public StreetDto fetchStreetById(Long streetId) throws ResourceNotFoundException {
        return convertToDto(streetRepository.findById(streetId)
                .orElseThrow(() -> new ResourceNotFoundException("Street not found for this id : " + streetId)));
    }

    @Transactional
    public StreetDto updateStreet(Long streetId, Street streetDetails) throws ResourceNotFoundException {
        Street street = streetRepository.findById(streetId)
                .orElseThrow(() -> new ResourceNotFoundException("Street not found for this id : " + streetId));

        street.setStreetName(streetDetails.getStreetName());
        street.setAccounts(streetDetails.getAccounts());

        Street updatedStreet = streetRepository.save(street);
        return convertToDto(updatedStreet);
    }

    @Transactional
    public Map<String, Boolean> deleteStreet(Long streetId) throws ResourceNotFoundException {
        Street street = streetRepository.findById(streetId)
                .orElseThrow(() -> new ResourceNotFoundException("Street not found for this id : " + streetId));

        streetRepository.delete(street);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private List<StreetDto> convertToListStreetDto(List<Street> streets) {
        if (streets.isEmpty())
            return new ArrayList<>();
        return streets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private StreetDto convertToDto(Street street) {
        StreetDto streetDto = modelMapper.map(street, StreetDto.class);
        streetDto.setAccounts(convertToListAccountDto(street.getAccounts()));
        return modelMapper.map(street, StreetDto.class);
    }

    private List<AccountDto> convertToListAccountDto(List<Account> accounts) {
        if (accounts.isEmpty())
            return new ArrayList<>();
        return accounts.stream().map(this::convertToAccountDto).collect(Collectors.toList());
    }

    private AccountDto convertToAccountDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }
}

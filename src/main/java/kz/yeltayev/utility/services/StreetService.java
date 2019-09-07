package kz.yeltayev.utility.services;

import kz.yeltayev.utility.model.dto.AccountDto;
import kz.yeltayev.utility.model.dto.StreetDto;
import kz.yeltayev.utility.model.entity.Account;
import kz.yeltayev.utility.model.entity.Street;
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
    public List<StreetDto> fetchStreets() {
        List<Street> streets = streetRepository.findAll();
        return convertToListStreetDto(streets);
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

package kz.yeltayev.utility.services;

import kz.yeltayev.utility.model.dto.AccountDetailDto;
import kz.yeltayev.utility.model.entity.AccountDetail;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.repository.AccountDetailRepository;
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
public class AccountDetailService {

    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountDetailService(AccountDetailRepository accountDetailRepository) {
        this.accountDetailRepository = accountDetailRepository;
    }

    @Transactional
    public AccountDetailDto addAccountDetail(AccountDetail accountDetail) {
        return convertToDto(accountDetailRepository.save(accountDetail));
    }

    @Transactional
    public List<AccountDetailDto> fetchAccountDetails() {
        List<AccountDetail> accountDetails = accountDetailRepository.findAll();
        return convertToListAccountDetailDto(accountDetails);
    }

    @Transactional
    public AccountDetailDto fetchAccountDetailById(Long accountDetailId) throws ResourceNotFoundException {
        return convertToDto(accountDetailRepository.findById(accountDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountDetailId)));
    }

    @Transactional
    public AccountDetailDto updateAccountDetail(Long accountDetailId, AccountDetail accountDetails) throws ResourceNotFoundException {
        AccountDetail accountDetail = accountDetailRepository.findById(accountDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountDetailId));

        accountDetail.setCounter(accountDetails.getCounter());

        AccountDetail updatedAccountDetail = accountDetailRepository.save(accountDetail);
        return convertToDto(updatedAccountDetail);
    }

    @Transactional
    public Map<String, Boolean> deleteAccountDetail(Long accountDetailId) throws ResourceNotFoundException {
        AccountDetail accountDetail = accountDetailRepository.findById(accountDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountDetailId));

        accountDetailRepository.delete(accountDetail);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private List<AccountDetailDto> convertToListAccountDetailDto(List<AccountDetail> accountDetails) {
        if (accountDetails.isEmpty())
            return new ArrayList<>();

        return accountDetails.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private AccountDetailDto convertToDto(AccountDetail accountDetail) {
        AccountDetailDto accountDetailDto = modelMapper.map(accountDetail, AccountDetailDto.class);

        accountDetailDto.setId(accountDetail.getId());
        accountDetailDto.setMonthNumber(accountDetail.getMonthNumber());
        accountDetailDto.setYear(accountDetail.getYear());
        accountDetailDto.setCounter(accountDetail.getCounter());

        return accountDetailDto;
    }
}
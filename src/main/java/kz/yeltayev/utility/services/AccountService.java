package kz.yeltayev.utility.services;

import kz.yeltayev.utility.dto.AccountDetailDto;
import kz.yeltayev.utility.dto.AccountDto;
import kz.yeltayev.utility.entity.AccountDetail;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.entity.Account;
import kz.yeltayev.utility.repository.AccountDetailRepository;
import kz.yeltayev.utility.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private AccountDetailRepository accountDetailRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountService(
            AccountRepository accountRepository,
            AccountDetailRepository accountDetailRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountDetailRepository = accountDetailRepository;
    }

    @Transactional
    public AccountDto addAccount(Account account) {
        return convertToDto(accountRepository.save(account));
    }

    @Transactional
    public List<AccountDto> fetchAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return convertToListAccountDto(accounts);
    }

    @Transactional
    public List<AccountDto> findAccounts(String query) {
        List<Account> accounts = accountRepository.findAccounts(query);
        return convertToListAccountDto(accounts);
    }

    @Transactional
    public AccountDto fetchAccountById(Long accountId) throws ResourceNotFoundException {
        return convertToDto(accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountId)));
    }

    @Transactional
    public AccountDto updateAccount(Long accountId, Account accountDetails) throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountId));

        account.setAccountName(accountDetails.getAccountName());
        account.setAccountNumber(accountDetails.getAccountNumber());
        account.setCounterNumber(accountDetails.getCounterNumber());
        account.setAddress(accountDetails.getAddress());
        account.setStreet(accountDetails.getStreet());

        Account updatedAccount = accountRepository.save(account);
        return convertToDto(updatedAccount);
    }

    @Transactional
    public Map<String, Boolean> deleteAccount(Long accountId) throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id : " + accountId));

        accountRepository.delete(account);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private List<AccountDto> convertToListAccountDto(List<Account> accounts) {
        if (accounts.isEmpty())
            return new ArrayList<>();
        return accounts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private AccountDto convertToDto(Account account) {
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        accountDto.setStreetId(account.getStreet().getId());
        accountDto.setStreetName(account.getStreet().getStreetName());

        accountDto.setServiceId(account.getService().getId());
        accountDto.setServiceName(account.getService().getServiceName());

        List<AccountDetail> details = accountDetailRepository.fetchDetailsForAccount(account.getAccountNumber());
        if (!details.isEmpty()) {
            details.sort(Comparator.comparing(AccountDetail::getYear).thenComparing(AccountDetail::getMonthNumber));
            Collections.reverse(details);
            accountDto.setAccountDetails(
                    details.stream().map(this::convertToAccountDetailDto).collect(Collectors.toList())
            );
        } else {
            accountDto.setAccountDetails(new ArrayList<>());
        }

        return accountDto;
    }

    private AccountDetailDto convertToAccountDetailDto(AccountDetail accountDetail) {
        AccountDetailDto accountDetailDto = modelMapper.map(accountDetail, AccountDetailDto.class);

        accountDetailDto.setId(accountDetail.getId());
        accountDetailDto.setMonthNumber(accountDetail.getMonthNumber());
        accountDetailDto.setYear(accountDetail.getYear());
        accountDetailDto.setCounter(accountDetail.getCounter());

        return accountDetailDto;
    }
}

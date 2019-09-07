package kz.yeltayev.utility.services;

import kz.yeltayev.utility.model.dto.AccountDto;
import kz.yeltayev.utility.model.dto.InvoiceDto;
import kz.yeltayev.utility.model.entity.Invoice;
import kz.yeltayev.utility.model.entity.Street;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.entity.Account;
import kz.yeltayev.utility.repository.AccountRepository;
import kz.yeltayev.utility.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountService(
            AccountRepository accountRepository,
            InvoiceRepository invoiceRepository
    ) {
        this.accountRepository = accountRepository;
        this.invoiceRepository = invoiceRepository;
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
    public List<AccountDto> findAccountsByServiceId(Long serviceId) {
        List<Account> accounts = accountRepository.findAccountsByService_Id(serviceId);
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

        Street street = account.getStreet();
        accountDto.setStreetId(street.getId());
        accountDto.setStreetName(street.getStreetName());

        kz.yeltayev.utility.model.entity.Service service = account.getService();
        accountDto.setServiceId(service.getId());
        accountDto.setServiceName(service.getServiceName());

        Optional<Invoice> invoice = invoiceRepository.findById(account.getCounterNumber());
        if (invoice.isPresent()) {
            accountDto.setInvoiceDto(convertToInvoiceDto(invoice.get()));
        } else {
            Invoice newInvoice = new Invoice();
            newInvoice.setCounterNumber(account.getCounterNumber());
            newInvoice.setYear(Calendar.getInstance().get(Calendar.YEAR));
            invoiceRepository.save(newInvoice);
            accountDto.setInvoiceDto(convertToInvoiceDto(newInvoice));
        }
        return accountDto;
    }

    private InvoiceDto convertToInvoiceDto(Invoice invoice) {
        InvoiceDto invoiceDto = modelMapper.map(invoice, InvoiceDto.class);
        return invoiceDto;
    }
}

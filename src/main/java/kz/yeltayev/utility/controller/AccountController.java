package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.model.dto.AccountDto;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.entity.Account;
import kz.yeltayev.utility.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<AccountDto> fetchAccounts() {
        return accountService.fetchAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> fetchAccountById(@PathVariable(value = "id") Long accountId) throws ResourceNotFoundException {
        AccountDto accountDto = accountService.fetchAccountById(accountId);
        return ResponseEntity.ok().body(accountDto);
    }

    @GetMapping("/accounts/search")
    public List<AccountDto> findAccounts(@RequestParam String query) {
        return accountService.findAccounts(query);
    }

    @GetMapping("/accounts/{serviceId}")
    public List<AccountDto> findAccountsByServiceId(@PathVariable(value = "serviceId") Long serviceId) {
        return accountService.findAccountsByServiceId(serviceId);
    }

    @PostMapping("/accounts")
    public AccountDto addAccount(@Valid @RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable(value = "id") Long accountId,
                                                    @Valid @RequestBody Account accountDetails) throws ResourceNotFoundException {
        AccountDto updatedAccount = accountService.updateAccount(accountId, accountDetails);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
        return accountService.deleteAccount(accountId);
    }
}

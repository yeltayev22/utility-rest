package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.dto.AccountDetailDto;
import kz.yeltayev.utility.entity.AccountDetail;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.services.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccountDetailController {

    private AccountDetailService accountDetailService;

    @Autowired
    public AccountDetailController(AccountDetailService accountDetailService) {
        this.accountDetailService = accountDetailService;
    }

    @GetMapping("/details")
    public List<AccountDetailDto> fetchAccountDetails() {
        return accountDetailService.fetchAccountDetails();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<AccountDetailDto> fetchAccountById(@PathVariable(value = "id") Long accountDetailId) throws ResourceNotFoundException {
        AccountDetailDto AccountDetailDto = accountDetailService.fetchAccountDetailById(accountDetailId);
        return ResponseEntity.ok().body(AccountDetailDto);
    }

    @PostMapping("/details")
    public AccountDetailDto addAccountDetail(@Valid @RequestBody AccountDetail accountDetail) throws ResourceNotFoundException {
        return accountDetailService.addAccountDetail(accountDetail);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<AccountDetailDto> updateAccountDetail(@PathVariable(value = "id") Long accountDetailId,
                                                                @Valid @RequestBody AccountDetail accountDetails) throws ResourceNotFoundException {
        AccountDetailDto updatedAccount = accountDetailService.updateAccountDetail(accountDetailId, accountDetails);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/details/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountDetailId)
            throws ResourceNotFoundException {
        return accountDetailService.deleteAccountDetail(accountDetailId);
    }
}

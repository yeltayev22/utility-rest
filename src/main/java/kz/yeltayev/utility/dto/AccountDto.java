package kz.yeltayev.utility.dto;

import kz.yeltayev.utility.entity.AccountDetail;
import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private Long accountNumber;
    private Long counterNumber;
    private String accountName;
    private String address;
    private String streetName;
    private List<AccountDetailDto> accountDetails;
}

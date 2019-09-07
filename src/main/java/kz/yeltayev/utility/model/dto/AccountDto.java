package kz.yeltayev.utility.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private Long accountNumber;
    private Long counterNumber;
    private String accountName;

    private String address;

    private Long streetId;
    private String streetName;

    private Long serviceId;
    private String serviceName;

    private InvoiceDto invoiceDto;
}

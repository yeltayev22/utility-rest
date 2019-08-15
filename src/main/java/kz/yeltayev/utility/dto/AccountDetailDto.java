package kz.yeltayev.utility.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailDto {
    private int monthNumber;
    private BigDecimal counter;
}
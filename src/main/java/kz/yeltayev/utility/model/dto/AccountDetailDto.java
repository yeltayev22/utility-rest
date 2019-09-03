package kz.yeltayev.utility.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailDto {
    private Long id;
    private int monthNumber;
    private int year;
    private BigDecimal counter;
}
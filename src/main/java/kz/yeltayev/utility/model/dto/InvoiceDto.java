package kz.yeltayev.utility.model.dto;

import kz.yeltayev.utility.model.entity.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDto {

    private Long counterNumber;

    private BigDecimal month1;
    private BigDecimal month2;
    private BigDecimal month3;
    private BigDecimal month4;
    private BigDecimal month5;
    private BigDecimal month6;
    private BigDecimal month7;
    private BigDecimal month8;
    private BigDecimal month9;
    private BigDecimal month10;
    private BigDecimal month11;
    private BigDecimal month12;

    private int year;
}

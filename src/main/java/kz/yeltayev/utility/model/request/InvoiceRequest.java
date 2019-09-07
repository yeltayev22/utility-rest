package kz.yeltayev.utility.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceRequest {
    private Long counterNumber;
    private int monthNumber;
    private BigDecimal counterValue;
}

package kz.yeltayev.utility.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Detail {
    private int monthNumber;
    private BigDecimal counter;

    public Detail(int monthNumber, BigDecimal counter){
        this.monthNumber = monthNumber;
        this.counter = counter;
    }
}

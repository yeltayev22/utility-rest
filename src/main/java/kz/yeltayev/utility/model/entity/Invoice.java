package kz.yeltayev.utility.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "counter_number")
    private Long counterNumber;

    @Column(name = "month_1")
    private BigDecimal month1;

    @Column(name = "month_2")
    private BigDecimal month2;

    @Column(name = "month_3")
    private BigDecimal month3;

    @Column(name = "month_4")
    private BigDecimal month4;

    @Column(name = "month_5")
    private BigDecimal month5;

    @Column(name = "month_6")
    private BigDecimal month6;

    @Column(name = "month_7")
    private BigDecimal month7;

    @Column(name = "month_8")
    private BigDecimal month8;

    @Column(name = "month_9")
    private BigDecimal month9;

    @Column(name = "month_10")
    private BigDecimal month10;

    @Column(name = "month_11")
    private BigDecimal month11;

    @Column(name = "month_12")
    private BigDecimal month12;

    @Column(name = "year")
    private int year;
}

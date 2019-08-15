package kz.yeltayev.utility.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Account_Detail")
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month_number")
    private int monthNumber;

    @Column(name = "counter")
    private BigDecimal counter;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
}

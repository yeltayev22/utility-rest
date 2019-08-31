package kz.yeltayev.utility.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "counter_number", nullable = false)
    private Long counterNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "street_id", nullable = false)
    private Street street;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToMany(mappedBy = "account", targetEntity = AccountDetail.class)
    private List<AccountDetail> accountDetails;
}

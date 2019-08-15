package kz.yeltayev.utility.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Street")
@Data
public class Street {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @OneToMany(mappedBy = "street", targetEntity = Account.class)
    private List<Account> accounts;

    @OneToMany(mappedBy = "street")
    private List<Access> accesses;
}

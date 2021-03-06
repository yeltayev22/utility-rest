package kz.yeltayev.utility.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @OneToMany(mappedBy = "service")
    private List<Access> accesses;

    @OneToMany(mappedBy = "service", targetEntity = Account.class)
    private List<Account> accounts;
}

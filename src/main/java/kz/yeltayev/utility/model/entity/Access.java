package kz.yeltayev.utility.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Access")
@Data
public class Access {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;
}

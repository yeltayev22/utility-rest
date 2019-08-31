package kz.yeltayev.utility.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Access")
@Data
public class Access {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}

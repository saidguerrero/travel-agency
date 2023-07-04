package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SalesPersonInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "cityId", referencedColumnName = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "branchId", referencedColumnName = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_id", nullable = false)
    private User user;

}

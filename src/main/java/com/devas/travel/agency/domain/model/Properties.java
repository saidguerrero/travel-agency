package com.devas.travel.agency.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Properties {

    @Id
    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

}

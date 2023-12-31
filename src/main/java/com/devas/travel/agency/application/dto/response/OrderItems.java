package com.devas.travel.agency.application.dto.response;

import com.devas.travel.agency.domain.model.Branch;
import com.devas.travel.agency.domain.model.City;
import com.devas.travel.agency.domain.model.Supplier;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderItems {

    private List<Branch> branches;

    private List<Supplier> suppliers;

    private List<City> cities;

    private List<UserResponse> salesPersons;

    private List<Item> supplierItems;

}

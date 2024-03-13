package com.devas.travel.agency.application.dto.response;

import com.devas.travel.agency.domain.model.Branch;
import com.devas.travel.agency.domain.model.City;
import com.devas.travel.agency.domain.model.PaymentMethod;
import com.devas.travel.agency.domain.model.PaymentType;
import com.devas.travel.agency.domain.model.Supplier;
import com.devas.travel.agency.domain.model.TypeService;
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

    private List<TypeService> typeServices;

    private List<PaymentMethod> paymentMethods;

    private List<PaymentType> paymentTypes;

}

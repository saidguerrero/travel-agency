package com.devas.travel.agency.domain.service;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.Item;
import com.devas.travel.agency.domain.model.Supplier;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierService {

        Either<Error,List<Supplier>> getSuppliers();

        List<Item> getSupplierItems();

}

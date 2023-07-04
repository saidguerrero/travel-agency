package com.devas.travel.agency.domain.service.impl;

import com.devas.travel.agency.application.dto.response.Error;
import com.devas.travel.agency.application.dto.response.Item;
import com.devas.travel.agency.domain.model.Supplier;
import com.devas.travel.agency.domain.service.SupplierService;
import com.devas.travel.agency.infrastructure.adapter.repository.SupplierRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Either<Error, List<Supplier>> getSuppliers() {
        var items = supplierRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return Either.left(Error.builder()
                    .message("supplier not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build());

        }
        return Either.right(items);

    }

    @Override
    public List<Item> getSupplierItems() {
        var items = supplierRepository.findAllByActiveTrue();
        if (items.isEmpty()) {
            return List.of();

        }
        return items.stream().map(item -> Item.builder()
                .id(String.valueOf(item.getSupplierId()))
                .description(item.getDescription())
                .build()).collect(Collectors.toList());

    }
}

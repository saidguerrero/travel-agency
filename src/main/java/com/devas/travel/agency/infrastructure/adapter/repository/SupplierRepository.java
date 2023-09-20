package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findAllByActiveTrue();

    Optional<Supplier> findBySupplierId(int id);

}

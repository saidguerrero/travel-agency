package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.FixedCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FixedChargesRepository extends JpaRepository<FixedCharges, Integer> {

    @Query(value = "SELECT * FROM fixed_charges WHERE ? BETWEEN quantity_from  AND quantity_to ", nativeQuery = true)
    Optional<FixedCharges> findByValue(BigDecimal value);

}

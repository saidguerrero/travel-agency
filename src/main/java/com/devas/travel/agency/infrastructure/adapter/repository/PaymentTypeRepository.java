package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {

    List<PaymentType> findAllByActiveTrue();

    Optional<PaymentType> findByPaymentTypeId(int id);

}

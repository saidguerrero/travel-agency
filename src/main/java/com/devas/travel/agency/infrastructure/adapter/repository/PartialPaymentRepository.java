package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.PartialPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PartialPaymentRepository extends JpaRepository<PartialPayments, Long> {

    @Query("SELECT o.amount - COALESCE(SUM(pp.paymentAmount), 0) FROM Orders o LEFT JOIN PartialPayments pp ON o.id = pp.order.id WHERE o.id = :orderId GROUP BY o.id")
    BigDecimal findBalanceDueByOrderId(Long orderId);

    List<PartialPayments> findByOrderOrderId(int orderId);

}
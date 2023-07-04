package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findById(Long id);

    Optional<Orders> findByReservationNumber(String reservationNumber);

    List<Orders> findAllBySalesPersonUserIdOrderByOrderIdDesc(int userId);

    Page<Orders> findBySalesPersonUserIdOrderByOrderIdDesc(int userId, Pageable pageable);

}

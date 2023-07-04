package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.OrderFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderFilesRepository extends JpaRepository<OrderFiles, Long> {

    List<OrderFiles> findByOrdersByOrderIdOrderId(Long orderId);

    Optional<OrderFiles> findById(Long id);

}

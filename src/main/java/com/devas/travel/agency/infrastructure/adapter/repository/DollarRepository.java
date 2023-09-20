package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Dollar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DollarRepository extends JpaRepository<Dollar, Integer> {

    Optional<Dollar> findById(int id);

    Optional<Dollar> findByActiveTrue();

}

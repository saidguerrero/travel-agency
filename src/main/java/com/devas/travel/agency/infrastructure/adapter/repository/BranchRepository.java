package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    List<Branch> findAllByActiveTrue();

    Optional<Branch> findByBranchId(int id);

}

package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.Euro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EuroRepository extends JpaRepository<Euro, Integer> {

    Optional<Euro> findById(int id);

}

package com.devas.travel.agency.infrastructure.adapter.repository;


import com.devas.travel.agency.domain.model.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Integer> {

    List<TypeService> findAllByActiveTrue();

    Optional<TypeService> findByServiceId(int id);

}

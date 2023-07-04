package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findAllByActiveTrue();

    Optional<City> findByCityId(int id);

}

package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.SalesPersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesPersonInfoRepository extends JpaRepository<SalesPersonInfo, Integer> {

  Optional<SalesPersonInfo> findByUserUserId(int userId);

}

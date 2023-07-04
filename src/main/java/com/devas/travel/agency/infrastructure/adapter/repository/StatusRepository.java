package com.devas.travel.agency.infrastructure.adapter.repository;


import com.devas.travel.agency.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findByStatusId(int statusId);

}

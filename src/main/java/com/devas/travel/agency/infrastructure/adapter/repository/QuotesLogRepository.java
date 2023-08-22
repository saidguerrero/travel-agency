package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.QuotesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotesLogRepository extends JpaRepository<QuotesLog, Long> {
}

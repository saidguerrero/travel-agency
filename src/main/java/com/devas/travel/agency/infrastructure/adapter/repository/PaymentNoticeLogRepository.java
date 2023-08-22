package com.devas.travel.agency.infrastructure.adapter.repository;

import com.devas.travel.agency.domain.model.PaymentNoticeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentNoticeLogRepository extends JpaRepository<PaymentNoticeLog, Long> {
}

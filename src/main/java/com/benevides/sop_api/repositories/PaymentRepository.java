package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    boolean existsByCommitmentId(UUID commitment_id);
    List<Payment> findAllByCommitmentId(UUID commitment_id);
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM payment p where p.commitment.id = :commitment_id")
    float sumPaymentsByCommitmentId(UUID commitment_id);
}

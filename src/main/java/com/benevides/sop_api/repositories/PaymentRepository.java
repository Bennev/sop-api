package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    boolean existsByCommitmentId(UUID commitment_id);
    List<Payment> findAllByCommitment_Id(UUID commitment_id);
}

package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByCommitmentId(long commitment_id);
    List<Payment> findAllByCommitmentId(long commitment_id);
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM payment p where p.commitment.id = :commitment_id")
    float sumPaymentsByCommitmentId(long commitment_id);
}

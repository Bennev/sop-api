package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByCommitmentId(long commitment_id);
    Page<Payment> findAllByCommitmentId(long commitment_id, Pageable pageable);
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM payment p where p.commitment.id = :commitment_id")
    float sumPaymentsByCommitmentId(long commitment_id);
    @Query("SELECT COALESCE(MAX(CAST(RIGHT(p.payment_number, 4) AS int)), 0) " +
            "FROM payment p WHERE LEFT(p.payment_number, 4) = :year")
    Long findMaxPaymentSequenceByYear(@Param("year") String year);
}

package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.commitment.Commitment;
import com.benevides.sop_api.domain.commitment.GetCommitmentsWithPaymentCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
    boolean existsByExpenseId(long expense_id);
    @Query("SELECT new com.benevides.sop_api.domain.commitment.GetCommitmentsWithPaymentCountDTO(" +
            "c.id, c.date, c.value, c.note, c.commitment_number, COALESCE(COUNT(p), 0)) " +
            "FROM commitment c LEFT JOIN c.payments p " +
            "WHERE c.expense.id = :expense_id " +
            "GROUP BY c.id")
    Page<GetCommitmentsWithPaymentCountDTO> findAllWithPaymentCountByExpenseId(Long expense_id, Pageable pageable);
    @Query("SELECT COALESCE(SUM(c.value), 0) FROM commitment c where c.expense.id = :expense_id")
    BigDecimal sumCommitmentsByExpenseId(long expense_id);
    @Query("SELECT COALESCE(MAX(CAST(RIGHT(c.commitment_number, 4) AS int)), 0) " +
            "FROM commitment c WHERE LEFT(c.commitment_number, 4) = :year")
    Long findMaxCommitmentSequenceByYear(@Param("year") String year);
}

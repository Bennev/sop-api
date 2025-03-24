package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.commitment.Commitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
    boolean existsByExpenseId(long expense_id);
    Page<Commitment> findAllByExpenseId(Long expenseId, Pageable pageable);
    @Query("SELECT COALESCE(SUM(c.value), 0) FROM commitment c where c.expense.id = :expense_id")
    float sumCommitmentsByExpenseId(long expense_id);
}

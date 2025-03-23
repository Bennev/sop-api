package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.commitment.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
    boolean existsByExpenseId(long expense_id);
    List<Commitment> findAllByExpenseId(long expense_id);
    @Query("SELECT COALESCE(SUM(c.value), 0) FROM commitment c where c.expense.id = :expense_id")
    float sumCommitmentsByExpenseId(long expense_id);
}

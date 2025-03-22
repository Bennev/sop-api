package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.commitment.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommitmentRepository extends JpaRepository<Commitment, UUID> {
    boolean existsByExpenseId(UUID expense_id);
    List<Commitment> findAllByExpenseId(UUID expense_id);
    @Query("SELECT COALESCE(SUM(c.value), 0) FROM commitment c where c.expense.id = :expense_id")
    float sumCommitmentsByExpenseId(UUID expense_id);
}

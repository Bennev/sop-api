package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT new com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO(e.id, e.type, e.protocol_date, e.due_date, " +
            "e.creditor, e.description, e.value, COALESCE(COUNT(c), 0)) " +
            "FROM expense e LEFT JOIN e.commitments c " +
            "GROUP BY e.id")
    Page<GetExpensesWithCommitmentCountDTO> findAllWithCommitmentCount(Pageable pageable);
}

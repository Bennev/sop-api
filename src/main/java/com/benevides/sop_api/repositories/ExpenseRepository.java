package com.benevides.sop_api.repositories;

import com.benevides.sop_api.domain.expense.Expense;
import com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT new com.benevides.sop_api.domain.expense.GetExpensesWithCommitmentCountDTO(e.id, e.type, e.protocol_date, e.due_date, " +
            "e.creditor, e.description, e.value, e.protocol_number, COALESCE(COUNT(c), 0)) " +
            "FROM expense e LEFT JOIN e.commitments c " +
            "GROUP BY e.id")
    Page<GetExpensesWithCommitmentCountDTO> findAllWithCommitmentCount(Pageable pageable);
    @Query("SELECT COALESCE(MAX(CAST(SUBSTRING(e.protocol_number FROM 7 FOR 6) AS int)), 0) " +
            "FROM expense e WHERE SUBSTRING(e.protocol_number FROM 14 FOR 7) = :yearMonth")
    Long findMaxProtocolSequenceByMonth(@Param("yearMonth") String yearMonth);
}

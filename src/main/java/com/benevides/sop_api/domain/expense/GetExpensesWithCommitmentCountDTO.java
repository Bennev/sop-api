package com.benevides.sop_api.domain.expense;

import java.math.BigDecimal;
import java.util.Date;

public record GetExpensesWithCommitmentCountDTO(
        long id,
        ExpenseType type,
        Date protocol_date,
        Date due_date,
        String creditor,
        String description,
        BigDecimal value,
        String protocol_number,
        ExpenseStatus status,
        long commitment_count
) {
}

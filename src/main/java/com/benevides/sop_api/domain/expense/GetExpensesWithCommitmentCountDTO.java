package com.benevides.sop_api.domain.expense;

import java.util.Date;

public record GetExpensesWithCommitmentCountDTO(
        long id,
        ExpenseType type,
        Date protocol_date,
        Date due_date,
        String creditor,
        String description,
        float value,
        long commitment_count
) {
}

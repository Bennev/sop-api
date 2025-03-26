package com.benevides.sop_api.domain.commitment;

import java.math.BigDecimal;
import java.util.Date;

public record GetCommitmentsWithPaymentCountDTO(
        long id,
        Date date,
        BigDecimal value,
        String note,
        String commitment_number,
        long payment_count
) {
}

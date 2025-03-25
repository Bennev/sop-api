package com.benevides.sop_api.domain.commitment;

import java.util.Date;

public record GetCommitmentsWithPaymentCountDTO(
        long id,
        Date date,
        float value,
        String note,
        String commitment_number,
        long payment_count
) {
}

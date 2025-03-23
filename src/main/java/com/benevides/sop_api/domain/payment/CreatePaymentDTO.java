package com.benevides.sop_api.domain.payment;

import java.util.Date;

public record CreatePaymentDTO(Date date, float value, String note, long commitment_id) {
}

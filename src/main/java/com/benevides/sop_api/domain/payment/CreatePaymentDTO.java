package com.benevides.sop_api.domain.payment;

import java.util.Date;
import java.util.UUID;

public record CreatePaymentDTO(Date date, float value, String note, UUID commitment_id) {
}

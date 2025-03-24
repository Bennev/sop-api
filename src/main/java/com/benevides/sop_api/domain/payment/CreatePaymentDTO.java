package com.benevides.sop_api.domain.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CreatePaymentDTO(
        @NotNull(message = "A data é obrigatória")
        Date date,

        @Positive(message = "O valor é obrigatório e deve ser maior que zero")
        float value,

        String note,

        @Positive(message = "O id do empenho deve ser válido")
        long commitment_id
) {
}

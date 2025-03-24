package com.benevides.sop_api.domain.commitment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CreateCommitmentDTO(
        @NotNull(message = "A data é obrigatória")
        Date date,

        @Positive(message = "O valor é obrigatório e deve ser maior que zero")
        float value,

        String note,

        @Positive(message = "O id da despesa deve ser válido")
        long expense_id
) {
}

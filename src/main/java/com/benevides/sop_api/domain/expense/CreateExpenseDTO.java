package com.benevides.sop_api.domain.expense;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CreateExpenseDTO(
        @NotNull(message = "O tipo de despesa é obrigatório")
        @NotEmpty(message = "O tipo não pode ser vazio")
        String type,

        @NotNull(message = "A data do protocolo é obrigatória")
        Date protocol_date,

        @NotNull(message = "A data de vencimento é obrigatória")
        Date due_date,

        @NotNull(message = "O credor é obrigatório")
        @NotEmpty(message = "O credor não pode ser vazio")
        String creditor,

        String description,

        @Positive(message = "O valor é obrigatório e deve ser maior que zero")
        float value
) {
}

package com.benevides.sop_api.domain.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotNull(message = "Nome é obrigatório")
        @NotEmpty(message = "Nome não pode ser vazio")
        String name,

        @NotNull(message = "Login é obrigatório")
        @NotEmpty(message = "Login não pode ser vazio")
        String login,

        @NotNull(message = "Senha é obrigatória")
        @NotEmpty(message = "Senha não pode ser vazia")
        String password) {
}

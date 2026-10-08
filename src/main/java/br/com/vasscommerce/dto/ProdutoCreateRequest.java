package br.com.vasscommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProdutoCreateRequest(
        @NotBlank(message = "nome é obrigatório")
        @Size(max = 100, message = "nome deve ter no máximo 100 caracteres")
        String nome,

        @Size(max = 500, message = "descricao deve ter no máximo 500 caracteres")
        String descricao,

        @NotNull(message = "valorUnitario é obrigatório")
        @Positive(message = "valorUnitario deve ser maior que zero")
        BigDecimal valorUnitario) {
}

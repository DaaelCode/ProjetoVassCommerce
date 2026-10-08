package br.com.vasscommerce.dto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id, String nome, String descricao, BigDecimal valorUnitario) {
}

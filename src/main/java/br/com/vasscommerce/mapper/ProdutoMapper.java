package br.com.vasscommerce.mapper;

import br.com.vasscommerce.dto.ProdutoCreateRequest;
import br.com.vasscommerce.dto.ProdutoResponse;
import br.com.vasscommerce.entity.Produto;

public final class ProdutoMapper {

    private ProdutoMapper() {
    }

    /** Converte o DTO da camada web em entidade de negócio. */
    public static Produto toEntity(ProdutoCreateRequest req) {
        return new Produto(null, req.nome(), req.descricao(), req.valorUnitario());
    }

    /** Converte a entidade de negócio em DTO de resposta. */
    public static ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getDescricao(),
                produto.getValorUnitario());
    }
}

package br.com.vasscommerce.model;

import br.com.vasscommerce.entity.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Contrato da regra de Produto. O controller só conhece esta interface. */
public interface ProdutoModel {

    Produto criar(Produto produto);                            // regra de criação

    Optional<Produto> obterPorId(Long id);                     // consulta por id

    List<Produto> buscar(String nome,
                         BigDecimal valorMinimo,
                         BigDecimal valorMaximo);              // filtros opcionais
}

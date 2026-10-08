package br.com.vasscommerce.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vasscommerce.entity.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

/** As duas implementações precisam respeitar o mesmo contrato (ProdutoModel). */
class ProdutoModelTest {

    private static List<ProdutoModel> implementacoes() {
        return List.of(new ProdutoModelMemoria(), new ProdutoModelSql());
    }

    private static Produto produto(String nome, String valor) {
        return new Produto(null, nome, "descrição", new BigDecimal(valor));
    }

    @Test
    void criaProdutoComIdEConsegueObterPorId() {
        for (ProdutoModel model : implementacoes()) {
            Produto criado = model.criar(produto("Notebook", "3500.00"));

            assertNotNull(criado.getId());
            assertEquals("Notebook", model.obterPorId(criado.getId()).orElseThrow().getNome());
        }
    }

    @Test
    void idInexistenteRetornaVazio() {
        for (ProdutoModel model : implementacoes()) {
            assertTrue(model.obterPorId(9999L).isEmpty());
        }
    }

    @Test
    void buscaPorNomeEFaixaDeValor() {
        for (ProdutoModel model : implementacoes()) {
            model.criar(produto("Notebook Vass", "3500.00"));
            model.criar(produto("Mouse Vass", "80.00"));
            model.criar(produto("Teclado", "150.00"));

            assertEquals(2, model.buscar("vass", null, null).size());
            assertEquals(2, model.buscar(null, new BigDecimal("100"), null).size());
            assertEquals(1, model.buscar("vass", null, new BigDecimal("100")).size());
            assertEquals(3, model.buscar(null, null, null).size());
        }
    }

    @Test
    void sequenciasDiferentesIdentificamAImplementacao() {
        long idMemoria = new ProdutoModelMemoria().criar(produto("A", "1.00")).getId();
        long idSql = new ProdutoModelSql().criar(produto("A", "1.00")).getId();

        assertEquals(1L, idMemoria);
        assertEquals(1001L, idSql);
    }
}

package br.com.vasscommerce.model;

import br.com.vasscommerce.entity.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementação "simulada" de banco (Postgres/MySQL). Ativa com o profile "sql".
 * Por enquanto a "tabela" é uma lista; a sequence começa em 1001 para a troca de
 * implementação ficar visível nas respostas.
 */
@Service("produtoModelSql")
@Profile("sql")
public class ProdutoModelSql implements ProdutoModel {

    private static final Logger log = LoggerFactory.getLogger(ProdutoModelSql.class);

    private final List<Produto> tabelaProduto = new CopyOnWriteArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1000);

    public ProdutoModelSql() {
        log.info("ProdutoModel em uso: SQL simulado (profile 'sql')");
    }

    @Override
    public Produto criar(Produto produto) {
        Long novoId = sequence.incrementAndGet();
        Produto criado = new Produto(novoId, produto.getNome(), produto.getDescricao(), produto.getValorUnitario());
        tabelaProduto.add(criado);
        log.info("[SQL] INSERT INTO produto -> id={}", novoId);
        return criado;
    }

    @Override
    public Optional<Produto> obterPorId(Long id) {
        log.info("[SQL] SELECT * FROM produto WHERE id = {}", id);
        return tabelaProduto.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal min, BigDecimal max) {
        log.info("[SQL] SELECT * FROM produto (filtros opcionais)");
        return tabelaProduto.stream().filter(p -> p.atende(nome, min, max)).toList();
    }
}

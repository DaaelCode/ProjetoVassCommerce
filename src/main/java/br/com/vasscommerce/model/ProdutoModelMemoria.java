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

/** Implementação em memória. Ativa com o profile "mem". */
@Service("produtoModelMemoria")
@Profile("mem")
public class ProdutoModelMemoria implements ProdutoModel {

    private static final Logger log = LoggerFactory.getLogger(ProdutoModelMemoria.class);

    private final List<Produto> banco = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    public ProdutoModelMemoria() {
        log.info("ProdutoModel em uso: MEMÓRIA (profile 'mem')");
    }

    @Override
    public Produto criar(Produto produto) {
        Long novoId = seq.incrementAndGet();
        Produto criado = new Produto(novoId, produto.getNome(), produto.getDescricao(), produto.getValorUnitario());
        banco.add(criado);
        return criado;
    }

    @Override
    public Optional<Produto> obterPorId(Long id) {
        return banco.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal min, BigDecimal max) {
        return banco.stream().filter(p -> p.atende(nome, min, max)).toList();
    }
}

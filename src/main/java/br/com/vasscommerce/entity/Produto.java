package br.com.vasscommerce.entity;

import java.math.BigDecimal;
import java.util.Locale;

/** Entidade de negócio: é com ela que a camada model trabalha (DTOs ficam só na borda web). */
public class Produto {

    private final Long id;
    private final String nome;
    private final String descricao;
    private final BigDecimal valorUnitario;

    public Produto(Long id, String nome, String descricao, BigDecimal valorUnitario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    /** Regra da busca (filtros opcionais): nome contém o trecho e valor dentro da faixa. */
    public boolean atende(String trechoNome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        boolean nomeOk = trechoNome == null || trechoNome.isBlank()
                || nome.toLowerCase(Locale.ROOT).contains(trechoNome.toLowerCase(Locale.ROOT));
        boolean minimoOk = valorMinimo == null || valorUnitario.compareTo(valorMinimo) >= 0;
        boolean maximoOk = valorMaximo == null || valorUnitario.compareTo(valorMaximo) <= 0;
        return nomeOk && minimoOk && maximoOk;
    }
}

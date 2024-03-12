package ericrlessa.rinhabackend.domain.transacao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Transacao{

    @Id
    @GeneratedValue
    private Long id;

    private Integer clienteId;

    private Integer valor;

    private String tipo;

    private String descricao;

    private Instant realizadaEm;

    Transacao(){}

    public Transacao(Integer clienteId, Integer valor, String tipo, String descricao) {
        this.clienteId = clienteId;
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public Long getId() {
        return id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Integer getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Instant getRealizada_em() {
        return realizadaEm;
    }
}

package ericrlessa.rinhabackend.domain.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Integer clientId;

    @JsonProperty("valor")
    private Integer value;

    @JsonProperty("tipo")
    private String type;
    @JsonProperty("descricao")
    private String description;

    @JsonProperty("realizada_em")
    private Instant executedOn;

    Transaction(){}

    public Transaction(Integer clientId, Integer value, String type, String description) {
        this.clientId = clientId;
        this.value = value;
        this.type = type;
        this.description = description;
        this.executedOn = Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public Long getId() {
        return id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }
}

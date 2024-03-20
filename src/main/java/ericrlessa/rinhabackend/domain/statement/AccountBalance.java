package ericrlessa.rinhabackend.domain.statement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public record AccountBalance(@JsonProperty("total") Long total, @JsonProperty("data_extrato") Instant statementDate, @JsonProperty("limite") Long limit) {
    public static AccountBalance of(Long total, Long limite){
        return new AccountBalance(total, Instant.now().truncatedTo(ChronoUnit.MICROS), limite);
    }
}

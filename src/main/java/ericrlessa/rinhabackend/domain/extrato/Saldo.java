package ericrlessa.rinhabackend.domain.extrato;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record Saldo(Long total, Instant data_extrato, Long limite) {

    public static Saldo of(Long total, Long limite){
        return new Saldo(total, Instant.now().truncatedTo(ChronoUnit.MICROS), limite);
    }
}

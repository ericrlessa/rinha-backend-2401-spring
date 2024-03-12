package ericrlessa.rinhabackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    public Integer id;

    public Long limite;
    public Long saldo;

    Cliente(){}

    public Cliente(Integer id, Long limite, Long saldo){
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public static Cliente of(Integer id, Long limite, Long saldoInicial){
        return new Cliente(id, limite, saldoInicial);
    }

    public static Cliente of(Integer id, Long limite){
        return new Cliente(id, limite, 0l);
    }
}

package ericrlessa.rinhabackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    public Integer id;

    private Long limite;
    private Long saldo;

    Cliente(){}

    public Cliente(Long limite, Long saldo){
        this.limite = limite;
        this.saldo = saldo;
    }

    public static Cliente of(Long limite, Long saldoInicial){
        return new Cliente(limite, saldoInicial);
    }

    public static Cliente of(Integer id, Long limite){
        return new Cliente(limite, 0l);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLimite() {
        return limite;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }
}

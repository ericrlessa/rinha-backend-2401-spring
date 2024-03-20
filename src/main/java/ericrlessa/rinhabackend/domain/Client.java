package ericrlessa.rinhabackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Client {

    @Id
    public Integer id;

    @JsonProperty("limite")
    private Long limitValue;

    @JsonProperty("saldo")
    private Long accountBalance;

    Client(){}

    public Client(Integer id, Long limite, Long accountBalance){
        this.id = id;
        this.limitValue = limite;
        this.accountBalance = accountBalance;
    }

    public static Client of(Long limite, Long saldoInicial){
        return new Client(null, limite, saldoInicial);
    }

    public static Client of(Integer id, Long limite){
        return new Client(id, limite, 0l);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(Long limitValue) {
        this.limitValue = limitValue;
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }
}

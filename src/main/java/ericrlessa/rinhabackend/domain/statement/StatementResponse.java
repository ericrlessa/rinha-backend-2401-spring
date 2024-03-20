package ericrlessa.rinhabackend.domain.statement;

import com.fasterxml.jackson.annotation.JsonProperty;
import ericrlessa.rinhabackend.domain.transaction.Transaction;

import java.util.List;

public record StatementResponse(@JsonProperty("saldo") AccountBalance accountBalance, @JsonProperty("ultimas_transacoes") List<Transaction> lastTransactions){}

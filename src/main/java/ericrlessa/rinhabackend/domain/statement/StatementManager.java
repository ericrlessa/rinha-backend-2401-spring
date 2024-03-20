package ericrlessa.rinhabackend.domain.statement;

import ericrlessa.rinhabackend.domain.Client;
import ericrlessa.rinhabackend.domain.AbstractManager;
import ericrlessa.rinhabackend.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementManager extends AbstractManager {

    public StatementResponse extrato(Integer id){
        validarClienteExistente(id);

        Client c = clientRepository.findById(id).get();

        List<Transaction> transacoes = transactionRepository.findFirst10ByClientIdOrderByExecutedOnDesc(id);

        return new StatementResponse(AccountBalance.of(c.getAccountBalance(), c.getLimitValue()), transacoes);
    }
}

package ericrlessa.rinhabackend.domain.transaction;

import ericrlessa.rinhabackend.domain.Client;
import ericrlessa.rinhabackend.domain.AbstractManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionBusinessManager extends AbstractManager {

    public TransactionResponse debito(Transaction transaction){
        validarClienteExistente(transaction.getClientId());
        validarDescricao(transaction.getDescription());

        Client client = clientRepository.findByIdPessimisticWrite(transaction.getClientId()).get();
        if((client.getAccountBalance() - transaction.getValue()) < client.getLimitValue() * -1){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }else{
            client.setAccountBalance(client.getAccountBalance() - transaction.getValue());
        }

        transactionRepository.save(transaction);

        return new TransactionResponse(client.getLimitValue(), client.getAccountBalance());
    }

    public TransactionResponse credito(Transaction transaction){
        validarClienteExistente(transaction.getClientId());
        validarDescricao(transaction.getDescription());

        Client client = clientRepository.findByIdPessimisticWrite(transaction.getClientId()).get();
        client.setAccountBalance(client.getAccountBalance() + transaction.getValue());

        transactionRepository.save(transaction);

        return new TransactionResponse(client.getLimitValue(), client.getAccountBalance());
    }

    private void validarDescricao(String descricao){
        if(descricao == null || descricao.isBlank() || descricao.length() > 10){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}

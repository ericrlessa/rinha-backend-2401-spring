package ericrlessa.rinhabackend.domain;

import ericrlessa.rinhabackend.domain.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractManager {

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    public static final Integer UNPROCESSABLE_ENTITY = 422;

    public void validarClienteExistente(Integer id) {
        if(clientRepository.countById(id) == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

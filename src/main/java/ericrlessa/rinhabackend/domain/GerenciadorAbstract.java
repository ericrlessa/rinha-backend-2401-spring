package ericrlessa.rinhabackend.domain;

import ericrlessa.rinhabackend.domain.transacao.TransacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class GerenciadorAbstract {

    @Autowired
    protected ClienteRepositorio clienteRepositorio;

    @Autowired
    protected TransacaoRepositorio transacaoRepositorio;

    public static final Integer UNPROCESSABLE_ENTITY = 422;

    public void validarClienteExistente(Integer id) {
        if(clienteRepositorio.countById(id) == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

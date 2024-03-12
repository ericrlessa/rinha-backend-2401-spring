package ericrlessa.rinhabackend.domain.transacao;

import ericrlessa.rinhabackend.domain.Cliente;
import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GerenciadorTransacao extends GerenciadorAbstract {

    public TransacaoResponse debito(Transacao transacao){
        validarClienteExistente(transacao.getClienteId());
        validarDescricao(transacao.getDescricao());

        Cliente cliente = clienteRepositorio.findByIdPessimisticWrite(transacao.getClienteId()).get();
        if((cliente.getSaldo() - transacao.getValor()) < cliente.getLimite() * -1){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }else{
            cliente.setSaldo(cliente.getSaldo() - transacao.getValor());
        }

        transacaoRepositorio.save(transacao);

        return new TransacaoResponse(cliente.getLimite(), cliente.getSaldo());
    }

    public TransacaoResponse credito(Transacao transacao){
        validarClienteExistente(transacao.getClienteId());
        validarDescricao(transacao.getDescricao());

        Cliente cliente = clienteRepositorio.findByIdPessimisticWrite(transacao.getClienteId()).get();
        cliente.setSaldo(cliente.getSaldo() + transacao.getValor());

        transacaoRepositorio.save(transacao);

        return new TransacaoResponse(cliente.getLimite(), cliente.getSaldo());
    }

    private void validarDescricao(String descricao){
        if(descricao == null || descricao.isBlank() || descricao.length() > 10){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}

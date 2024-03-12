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
        if((cliente.saldo - transacao.getValor()) < cliente.limite * -1){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }else{
            cliente.saldo -= transacao.getValor();
        }

        transacaoRepositorio.save(transacao);

        return new TransacaoResponse(cliente.limite, cliente.saldo);
    }

    public TransacaoResponse credito(Transacao transacao){
        validarClienteExistente(transacao.getClienteId());
        validarDescricao(transacao.getDescricao());

        Cliente cliente = clienteRepositorio.findByIdPessimisticWrite(transacao.getClienteId()).get();
        cliente.saldo += transacao.getValor();

        transacaoRepositorio.save(transacao);

        return new TransacaoResponse(cliente.limite, cliente.saldo);
    }

    private void validarDescricao(String descricao){
        if(descricao == null || descricao.isBlank() || descricao.length() > 10){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}

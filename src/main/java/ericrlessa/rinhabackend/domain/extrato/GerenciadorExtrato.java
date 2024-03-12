package ericrlessa.rinhabackend.domain.extrato;

import ericrlessa.rinhabackend.domain.Cliente;
import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import ericrlessa.rinhabackend.domain.transacao.Transacao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciadorExtrato extends GerenciadorAbstract {

    public ExtratoResponse extrato(Integer id){
        validarClienteExistente(id);

        Cliente c = clienteRepositorio.findById(id).get();

        List<Transacao> transacoes = transacaoRepositorio.findFirst10ByClienteIdOrderByRealizadaEmDesc(id);

        return new ExtratoResponse(Saldo.of(c.getSaldo(), c.getLimite()), transacoes);
    }
}

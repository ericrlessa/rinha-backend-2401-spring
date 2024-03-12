package ericrlessa.rinhabackend.rs;

import ericrlessa.rinhabackend.domain.GerenciadorAbstract;
import ericrlessa.rinhabackend.domain.extrato.ExtratoResponse;
import ericrlessa.rinhabackend.domain.extrato.GerenciadorExtrato;
import ericrlessa.rinhabackend.domain.transacao.GerenciadorTransacao;
import ericrlessa.rinhabackend.domain.transacao.TransacaoResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes/{id}")
public class ClienteRest {

    @Autowired
    GerenciadorTransacao gerenciadorTransacao;

    @Autowired
    GerenciadorExtrato gerenciadorExtrato;

    @PostMapping(value ="/transacoes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<TransacaoResponse> creditoDebito(@PathVariable("id") Integer clienteId, @RequestBody TransacaoForm transacaoForm){

        if(transacaoForm == null || isDecimal(transacaoForm.valor())){
            return ResponseEntity.status(GerenciadorAbstract.UNPROCESSABLE_ENTITY).build();
        }

        return switch (transacaoForm.getTipo()) {
            case null -> ResponseEntity.status(GerenciadorAbstract.UNPROCESSABLE_ENTITY).build();
            case TransacaoForm.Tipo.DEBITO ->
                    ResponseEntity.ok(gerenciadorTransacao.debito(transacaoForm.parse(clienteId)));
            case TransacaoForm.Tipo.CREDITO ->
                    ResponseEntity.ok(gerenciadorTransacao.credito(transacaoForm.parse(clienteId)));
        };
    }

    private boolean isDecimal(Double valor) {
        return valor - valor.intValue() != 0;
    }

    @GetMapping(value ="/extrato")
    public ResponseEntity<ExtratoResponse> extrato(@PathVariable("id") Integer clienteId){
        return ResponseEntity.ok(gerenciadorExtrato.extrato(clienteId));
    }

}

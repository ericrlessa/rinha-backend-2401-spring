package ericrlessa.rinhabackend.rs;

import ericrlessa.rinhabackend.domain.Client;
import ericrlessa.rinhabackend.domain.ClientRepository;
import ericrlessa.rinhabackend.domain.AbstractManager;
import ericrlessa.rinhabackend.domain.statement.StatementResponse;
import ericrlessa.rinhabackend.domain.statement.StatementManager;
import ericrlessa.rinhabackend.domain.transaction.TransactionBusinessManager;
import ericrlessa.rinhabackend.domain.transaction.TransactionResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientRest {

    @Autowired
    TransactionBusinessManager transactionBusinessManager;
    @Autowired
    StatementManager statementManager;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> getClientes(){
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Client> createCliente(@RequestBody Client client){
        clientRepository.save(client);
        return ResponseEntity.ok(client);
    }

    @PostMapping(value ="/{id}/transacoes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<TransactionResponse> creditoDebito(@PathVariable("id") Integer clienteId, @RequestBody TransactionForm transactionForm){

        if(transactionForm == null || isDecimal(transactionForm.valor())){
            return ResponseEntity.status(AbstractManager.UNPROCESSABLE_ENTITY).build();
        }

        return switch (transactionForm.getTipo()) {
            case null -> ResponseEntity.status(AbstractManager.UNPROCESSABLE_ENTITY).build();
            case TransactionForm.Tipo.DEBITO ->
                    ResponseEntity.ok(transactionBusinessManager.debito(transactionForm.parse(clienteId)));
            case TransactionForm.Tipo.CREDITO ->
                    ResponseEntity.ok(transactionBusinessManager.credito(transactionForm.parse(clienteId)));
        };
    }

    private boolean isDecimal(Double valor) {
        return valor - valor.intValue() != 0;
    }

    @GetMapping(value ="/{id}/extrato")
    public ResponseEntity<StatementResponse> extrato(@PathVariable("id") Integer clienteId){
        return ResponseEntity.ok(statementManager.extrato(clienteId));
    }

}

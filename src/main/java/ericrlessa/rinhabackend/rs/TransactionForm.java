package ericrlessa.rinhabackend.rs;

import ericrlessa.rinhabackend.domain.transaction.Transaction;

public record TransactionForm(Double valor, String tipo, String descricao) {

    enum Tipo {
        DEBITO,
        CREDITO
    }

    public Tipo getTipo(){
        if(tipo() == null){
            return null;
        }

        if(tipo().equals("d")){
            return Tipo.DEBITO;
        }else if(tipo().equals("c")){
            return Tipo.CREDITO;
        }else{
            return null;
        }
    }

    public Transaction parse(Integer clienteId){
       return new Transaction(clienteId, valor.intValue(), tipo, descricao);
    }
}

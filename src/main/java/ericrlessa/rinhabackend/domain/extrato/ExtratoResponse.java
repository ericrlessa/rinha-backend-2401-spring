package ericrlessa.rinhabackend.domain.extrato;

import ericrlessa.rinhabackend.domain.transacao.Transacao;

import java.util.List;

public record ExtratoResponse (
Saldo saldo,
List<Transacao> ultimas_transacoes

){}

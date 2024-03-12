package ericrlessa.rinhabackend.domain.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {

    List<Transacao> findFirst10ByClienteIdOrderByRealizadaEmDesc(Integer clienteId);

}

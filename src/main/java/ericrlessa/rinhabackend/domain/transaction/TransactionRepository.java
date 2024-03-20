package ericrlessa.rinhabackend.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findFirst10ByClientIdOrderByExecutedOnDesc(Integer clientId);

}

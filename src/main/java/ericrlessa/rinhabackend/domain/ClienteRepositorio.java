package ericrlessa.rinhabackend.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    long countById(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Cliente> findById(Integer id);
}

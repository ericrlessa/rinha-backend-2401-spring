package ericrlessa.rinhabackend;


import ericrlessa.rinhabackend.domain.Cliente;
import ericrlessa.rinhabackend.domain.ClienteRepositorio;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    protected ClienteRepositorio clienteRepositorio;

    @BeforeEach
    @Transactional
    public void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port + "/api";

        clienteRepositorio.deleteAll();
        clienteRepositorio.save(Cliente.of(1, 100000l));
        clienteRepositorio.save(Cliente.of(2, 80000l));
        clienteRepositorio.save(Cliente.of(3, 1000000l));
        clienteRepositorio.save(Cliente.of(4, 10000000l));
        clienteRepositorio.save(Cliente.of(5, 500000l));

    }
}

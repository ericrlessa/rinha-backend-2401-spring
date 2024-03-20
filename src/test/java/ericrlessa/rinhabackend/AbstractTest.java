package ericrlessa.rinhabackend;


import ericrlessa.rinhabackend.domain.Client;
import ericrlessa.rinhabackend.domain.ClientRepository;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    protected ClientRepository clientRepository;

    @BeforeEach
    @Transactional
    public void beforeEach(){
        RestAssured.baseURI = "http://localhost:" + port;

        clientRepository.deleteAll();
        clientRepository.save(Client.of(1, 100000l));
        clientRepository.save(Client.of(2, 80000l));
        clientRepository.save(Client.of(3, 1000000l));
        clientRepository.save(Client.of(4, 10000000l));
        clientRepository.save(Client.of(5, 500000l));

    }
}

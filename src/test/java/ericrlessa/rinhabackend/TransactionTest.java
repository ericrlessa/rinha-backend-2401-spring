package ericrlessa.rinhabackend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class TransactionTest extends AbstractTest{

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.2"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void testDebito(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1000,
                         "tipo": "d",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(-1000));
    }

    @Test
    public void testCredito(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1000,
                         "tipo": "c",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(1000));
    }

    @Test
    public void testErroValorDecimal(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1.2,
                         "tipo": "c",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);
    }
    @Test
    public void testErroTipoTransacao(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "x",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);
    }

    @Test
    public void testErroClienteNaoEncontrado(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": "descricao"}
                        """)
                .when()
                .post("/clientes/6/transacoes")
                .then()
                .statusCode(404);
    }

}

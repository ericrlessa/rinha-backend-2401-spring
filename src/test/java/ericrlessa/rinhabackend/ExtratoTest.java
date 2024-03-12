package ericrlessa.rinhabackend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class ExtratoTest extends AbstractTest{

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
    public void testExtrato(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1000,
                         "tipo": "c",
                         "descricao": "toma"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(1000));

        given().contentType("application/json")
                .body("""
                        {"valor": 3500,
                         "tipo": "c",
                         "descricao": "toma"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(4500));

        given().contentType("application/json")
                .body("""
                        {"valor": 2000,
                         "tipo": "d",
                         "descricao": "devolve"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(100000))
                .body("saldo", is(2500));


        given().contentType("application/json")
                .body("""
                        {"valor": 158,
                         "tipo": "c",
                         "descricao": "toma"}
                        """)
                .when()
                .post("/clientes/2/transacoes")
                .then()
                .statusCode(200)
                .body("limite", is(80000))
                .body("saldo", is(158));

        final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{6}Z";
        given().contentType("application/json")
                .when()
                .get("/clientes/1/extrato")
                .then()
                .statusCode(200)
                .body("saldo.total", is(2500))
                .body("saldo.limite", is(100000))
                .body("saldo.data_extrato", matchesPattern(DATE_FORMAT))
                .body("ultimas_transacoes[0].valor", is(2000))
                .body("ultimas_transacoes[0].tipo", is("d"))
                .body("ultimas_transacoes[0].descricao", is("devolve"))
                .body("ultimas_transacoes[0].realizada_em", matchesPattern(DATE_FORMAT))
                .body("ultimas_transacoes[1].valor", is(3500))
                .body("ultimas_transacoes[1].tipo", is("c"))
                .body("ultimas_transacoes[1].descricao", is("toma"))
                .body("ultimas_transacoes[1].realizada_em", matchesPattern(DATE_FORMAT))
                .body("ultimas_transacoes[2].valor", is(1000))
                .body("ultimas_transacoes[2].tipo", is("c"))
                .body("ultimas_transacoes[2].descricao", is("toma"))
                .body("ultimas_transacoes[2].realizada_em", matchesPattern(DATE_FORMAT));
    }

    @Test
    public void testErroDescricao(){
        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": "12345678910"}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);

        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": ""}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);

        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": ""}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);

        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": "    "}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);

        given().contentType("application/json")
                .body("""
                        {"valor": 1,
                         "tipo": "c",
                         "descricao": null}
                        """)
                .when()
                .post("/clientes/1/transacoes")
                .then()
                .statusCode(422);
    }

    @Test
    public void testClienteNaoEncontradoExtrato(){
        given().contentType("application/json")
                .when()
                .get("/clientes/6/extrato")
                .then()
                .statusCode(404);
    }

}

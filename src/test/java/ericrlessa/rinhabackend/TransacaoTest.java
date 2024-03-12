package ericrlessa.rinhabackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
public class TransacaoTest extends AbstractTest{

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

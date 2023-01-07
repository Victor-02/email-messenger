package com.messenger.email;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class EmailTest extends BaseTest {

    @Test
    @DisplayName("Should return a status code 201 when send a email")
    public void t1() {
        given()
                .body("{\n" +
                        "\t\"emailFrom\": \"vc2002casemiro@gmail.com\",\n" +
                        "\t\"emailTo\": \"vc2002casemiro@gmail.com\",\n" +
                        "\t\"subject\": \"teste\",\n" +
                        "\t\"text\": \"Apenas um teste\"\n" +
                        "}")
                .contentType(ContentType.JSON)
            .when()
                .post("email/send")
            .then()
                .assertThat()
                .body("subject", equalTo("teste"))
                    .and()
                .body("statusEmail", equalTo("SENT"))
                .statusCode(201);
    }
}

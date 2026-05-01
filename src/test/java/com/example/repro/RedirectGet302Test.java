package com.example.repro;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@QuarkusTest
@QuarkusTestResource(RedirectServerTestResource.class)
public class RedirectGet302Test {

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void follows302RedirectForGet() {
        when()
                .get("/exercise")
                .then()
                .statusCode(200)
                .body(equalTo("Hello World!"));
    }
}

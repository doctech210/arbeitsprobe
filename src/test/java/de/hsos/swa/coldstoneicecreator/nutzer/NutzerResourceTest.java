package de.hsos.swa.coldstoneicecreator.nutzer;

import org.junit.jupiter.api.Test;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerImportDTO;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
public class NutzerResourceTest {

    @TestHTTPResource("/api/nutzer")
    URL nutzerEndpoint;

    @Test
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(nutzerEndpoint)
            .then().statusCode(401);
    }

    @Test
    public void testPost() {
        given().contentType(ContentType.JSON).body(new NutzerImportDTO(null, "test", "test"))
            .when().post(nutzerEndpoint).then().statusCode(200);
    }

    @Test
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(nutzerEndpoint)
            .then().statusCode(200)
            .body("size()", is(2));
    }
}

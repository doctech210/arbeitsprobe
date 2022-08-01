//@author Stefan Bierenriede 852142

package de.hsos.swa.coldstoneicecreator.nutzer;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import de.hsos.swa.coldstoneicecreator.nutzer.boundary.dto.NutzerImportDTO;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

@QuarkusTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
public class NutzerResourceTest {

    @TestHTTPResource("/api/nutzer")
    URL nutzerEndpoint;

    @Test
    @Order(55)
    public void testGetUnauthorized(){
        given().contentType(ContentType.JSON).when().get(nutzerEndpoint)
            .then().statusCode(401);
    }

    @Test
    @Order(56)
    public void testPost() {
        given().contentType(ContentType.JSON).body(new NutzerImportDTO(null, "test", "test"))
            .when().post(nutzerEndpoint).then().statusCode(200);
    }

    @Test
    @Order(57)
    @TestSecurity(user= "admin", roles = {"Admin"})
    public void testGetAdmin(){
        given().contentType(ContentType.JSON).when().get(nutzerEndpoint)
            .then().statusCode(200)
            .body("size()", is(2));
    }
}

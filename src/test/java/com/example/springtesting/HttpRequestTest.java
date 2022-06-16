package com.example.springtesting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hola, mundo");
    }

    //Pruebas Parametrizadas

    @DisplayName("multiple")
    @ParameterizedTest(name="{displayName} [{index}] {0} + {1} = {2}")
    @CsvSource({
            "1,     2,  3.0",
            "1,     1,  2.0",
            "1.0, 1.0,  2.0",
            "1,    -2, -1.0",
            "1,  -1.0,  0.0",
            "0,     2,  0.0",
            "1,    '',  1.0"
    })
    void canAddParameterized(String a, String b, String expected) {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=" + a + "&b=" + b, String.class))
                .isEqualTo(expected);
    }


    //Add
    @Test
    public void catAdd() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=2",
                String.class)).isEqualTo("3.0");
    }

    @Test
    public void catAddWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1.5&b=2", String.class))
                .isEqualTo("3.5");
    }

    @Test
    public void catAddWithInvalidNumber() {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
                .getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    public void catAddNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=-2", String.class))
                .isEqualTo("-1.0");
    }

    //Substract
    @Test
    public void catSubstract() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=4&b=2", String.class))
                .isEqualTo("2.0");
    }

    @Test
    public void catSubstractPositeveNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=-1&b=2", String.class))
                .isEqualTo("-3.0");
    }

    @Test
    public void catSubstractWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1", String.class))
                .isEqualTo("1.0");

    }

    @Test
    public void catSubstractNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=-2&b=-4", String.class))
                .isEqualTo("2.0");

    }

    @Test
    public void catSubstractWithInvalidNumber() {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/subtract?a=1&b=X", String.class)
                .getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    public void catSubstractWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1.5&b=2", String.class))
                .isEqualTo("-0.5");
    }

    //Multiply
    @Test
    public void catMultiply() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=4&b=2", String.class))
                .isEqualTo("8.0");
    }

    @Test
    public void catMultiplyWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void catMultiplyNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=-2&b=-4", String.class))
                .isEqualTo("8.0");

    }

    @Test
    public void catMultiplyWithInvalidNumber() {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/multiply?a=1&b=X", String.class)
                .getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    public void ccatMultiplyWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=1.5&b=2", String.class))
                .isEqualTo("0.75");
    }

    //Divide
    @Test
    public void catDivide() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=4&b=2", String.class))
                .isEqualTo("2.0");
    }

    @Test
    public void catDivideWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=1", String.class))
                .contains("Infinity");
    }

    @Test
    public void catDivideNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=-2&b=-4", String.class))
                .isEqualTo("0.5");

    }

    @Test
    public void catDivideWithInvalidNumber() {
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/divide?a=1&b=X", String.class)
                .getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    public void catDivideWithZero() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=2&b=0", String.class))
                .contains("Infinity");
    }

    @Test
    public void catDivideBetweenZeros() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=0&b=0", String.class))
                .contains("NaN");
    }
}

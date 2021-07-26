package com.pedroso.diogo.assignment.authentication.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
    
	@Test
    public void authReturnsUser() throws Exception {
        //given
        AuthenticationRequestModel requestModel = new AuthenticationRequestModel("userAdmin", "password");
        //when
        ResponseEntity<AuthenticationResponseModel> postRequest = this.restTemplate.postForEntity("http://localhost:" + port + "/", requestModel, AuthenticationResponseModel.class);
        System.out.println(postRequest);
        //then
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/authenticate",
            AuthenticationResponseModel.class)).isNotNull();
    	}
}
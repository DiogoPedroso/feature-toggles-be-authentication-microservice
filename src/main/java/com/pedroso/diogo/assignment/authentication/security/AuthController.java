package com.pedroso.diogo.assignment.authentication.security;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InMemoryUserDetailsManager userDetailsService;

    @Autowired
    private JWTUtil jwtUtils;

    /**
     * API function that returns either a jwt token if user is sucessfully logged or error message
     * 
     * @param authRequest
     * @return JWT Token or Bad Username/Password message
     * @throws Exception
     */
    @PostMapping("/authenticate")
    @CrossOrigin("*")
    @PermitAll
    public ResponseEntity<?> authenticationResponseModel(@RequestBody AuthenticationRequestModel authRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<GenericResponseModel>(new GenericResponseModel("Bad Username or Password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        String jwt = jwtUtils.generateToken(userDetails);

        return new ResponseEntity<AuthenticationResponseModel>(new AuthenticationResponseModel(jwt), HttpStatus.OK);
    }
}
package com.example.movieticketbooking.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.signed-key}")
    private String SIGNER_KEY;
    private final UserDetailsService userDetailsService;

    public String generateAccessToken(Authentication authentication) throws JOSEException {
        // get user information
        String email = authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        // create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // create JWT Claims
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("scope", scope)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600000)) // one hour
                .build();
        // create signer
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        // create jws object
        JWSObject jwsObject = new JWSObject(header, payload);
        // sign jws object
        byte[] decodedKey = Base64.getDecoder().decode(SIGNER_KEY);
        jwsObject.sign(new MACSigner(decodedKey));
        // return String jwt
        return jwsObject.serialize();
    }

    public String generateRefreshToken(Authentication authentication) throws JOSEException {
        // get user information
        String email = authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        // create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // create JWT Claims
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("scope", scope)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 2592000000L)) // one hour
                .build();
        // create signer
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        // create jws object
        JWSObject jwsObject = new JWSObject(header, payload);
        // sign jws object
        byte[] decodedKey = Base64.getDecoder().decode(SIGNER_KEY);
        jwsObject.sign(new MACSigner(decodedKey));
        // return String jwt
        return jwsObject.serialize();
    }

}

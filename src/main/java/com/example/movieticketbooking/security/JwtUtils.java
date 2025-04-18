package com.example.movieticketbooking.security;

import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.InvalidTokenSignatureException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtils {
    @Value("${jwt.signed-key}")
    private String SIGNER_KEY;

    public String generateToken(Authentication authentication) throws JOSEException {
        // get user information
        String email = authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        // create header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // create JWT Claims
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .claim("scope", scope)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600_000)) // one hour
                .build();
        // create signer
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        // create jws object
        JWSObject jwsObject = new JWSObject(header, payload);
        // sign jws object
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        // return String jwt
        return jwsObject.serialize();
    }

    public String getEmail(String token) {
        try {
            // Parse JWT
            SignedJWT signedJWT = SignedJWT.parse(token);
            // Create verifier to validate the jwt sign
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);

            // validate jwt sign
            if (signedJWT.verify(verifier)) {
                // get claims from JWT
                JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                return claims.getSubject();  // Trả về username
            } else {
                throw new InvalidTokenSignatureException(Code.JWT_INVALID);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while validating JWT", e);
        }
    }

    public String getScope(String token) {
        try {
            // Parse JWT
            SignedJWT signedJWT = SignedJWT.parse(token);
            // get claims from JWT
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            // return String scope
            return claims.getStringClaim("scope");
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting scope from JWT", e);
        }
    }
}

package com.example.movieticketbooking.security;

import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.InvalidTokenSignatureException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
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
        byte[] decodedKey = Base64.getDecoder().decode(SIGNER_KEY);
        jwsObject.sign(new MACSigner(decodedKey));
        // return String jwt
        return jwsObject.serialize();
    }

    public String getEmail(String token) {
        try {
            // validate jwt sign
            if (isValidToken(token)) {
                // Parse JWT
                SignedJWT signedJWT = SignedJWT.parse(token);
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

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new InvalidTokenSignatureException(Code.JWT_INVALID);
        }
        return bearerToken.substring(7); // get token after "Bearer "
    }

    public boolean isValidToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY);
            return signedJWT.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (Exception e) {
            throw new InvalidTokenSignatureException(Code.JWT_INVALID);
        }
    }
}

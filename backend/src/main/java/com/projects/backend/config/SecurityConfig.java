package com.projects.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projects.backend.dto.custom.JwtDataDto;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Configuration
public class SecurityConfig {

    private static final String secret = "1234567891011121314151617181920";

    public String generateToken(String userName, Integer userId) {

        if (userName == null || userName.equals("")) {
            return "USERNAME NULL";
        }
        if (userId == null) {
            return "USER_ID NULL";
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth2")
                    .withIssuedAt(new Date())
                    .withClaim("username", userName)
                    .withClaim("userId", userId)
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return e.getMessage();
        }
    }


    public boolean verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(decodedJWT);
            return true;
        } catch (JWTVerificationException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public String hashPassword(@NotNull String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public Boolean comparePassword(@NotNull String hash, @NotNull String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        String encryptedPassword = DatatypeConverter.printHexBinary(md.digest());
        if (hash.equals(encryptedPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public JwtDataDto getJWTData(String token) {
        JwtDataDto jwtDataDto = new JwtDataDto();
        String[] tokenParts = token.split("\\.");
        String payload = tokenParts[1];

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payloadJson = new String(decoder.decode(payload));
        JSONObject data = new JSONObject(payloadJson);

        jwtDataDto.setUsername(data.get("username").toString());
        jwtDataDto.setUserId(Integer.parseInt(data.get("userId").toString()));
        jwtDataDto.setIss(data.get("iss").toString());
        return jwtDataDto;
    }
}

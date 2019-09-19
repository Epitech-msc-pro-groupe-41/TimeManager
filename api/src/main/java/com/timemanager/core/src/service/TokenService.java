package com.timemanager.core.src.service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.timemanager.core.src.element.TokenData;
import com.timemanager.core.src.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TokenService {

    @Autowired
    UserService userService;

    private static final String SECRET_TOKEN = "WnCiOAhTBTD3HdinJwbW";

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    public String createToken(String userID) {
        try {
            String xsrf_token = generateRandomString(50);
            Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DATE, 30);

            String token = JWT.create().withClaim("c-xsrf-token", xsrf_token).withSubject(userID)
                    .withExpiresAt(c.getTime()).sign(algorithm);

            return token;

        } catch (UnsupportedEncodingException exception) {
            LOGGER.info("UnsupportedEncodingException in AuthService :", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unsopported encoding for token");
        } catch (JWTCreationException exception) {
            LOGGER.info("JWTCreationException in AuthService :", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error during token creation");
        }
    }

    public TokenData getTokenData(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            return new TokenData(jwt.getSubject(), jwt.getClaim("c-xsrf-token").asString(),
                    jwt.getExpiresAt().getTime());
        } catch (UnsupportedEncodingException exception) {
            LOGGER.info("UnsupportedEncodingException in AuthService :", exception.getMessage());
            // throw new ResponseStatusException(
            // HttpStatus.FORBIDDEN, "Unsopported encoding for token");
            return null;
        } catch (JWTVerificationException exception) {
            LOGGER.info("JWTVerificationException in AuthService :", exception.getMessage());
            // throw new ResponseStatusException(
            // HttpStatus.FORBIDDEN, "Error during token verification");
            return null;
        }
    }

    public boolean tokenExist(String token) {
        TokenData tokenData = this.getTokenData(token);
        if (tokenData == null)
            return false;

        return tokenData.getUserID() != null && tokenData.getXsrfToken() != null
                && tokenData.getExpireDate() > Calendar.getInstance().getTimeInMillis();
    }

    public boolean isTokenValid(String userID, String xsrf_token) {
        User user = userService.getUserById(userID, false);
        if (user == null || user.getToken() == null)
            return false;
        TokenData tokenData = this.getTokenData(user.getToken());
        if (tokenData == null)
            return false;
        if (!tokenData.getXsrfToken().equals(xsrf_token))
            return false;
        return true;
    }

    public static String generateRandomString(int length) {
        // You can customize the characters that you want to add into
        // the random strings
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1)
            throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}
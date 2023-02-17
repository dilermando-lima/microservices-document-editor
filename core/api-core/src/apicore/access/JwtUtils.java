package apicore.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apicore.exception.Throw;

@Component
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static final String NAME_HEADER_AUTHORIZATION = "Authorization";

    @Value("${secret.jwt}")
    private String secretJWT;

    public String tokenize(Object subject){
        try {
            return JWT
                .create()
                .withSubject(new ObjectMapper().writeValueAsString(subject))
                .sign(Algorithm.HMAC512(secretJWT));
        } catch (JsonProcessingException | IllegalArgumentException e) {
            Throw.any(logger, HttpStatus.INTERNAL_SERVER_ERROR, "Error when building token authorization");
        }catch( JWTVerificationException e){
            Throw.any(logger, HttpStatus.UNAUTHORIZED, "Authorization token must be verified or refreshed");
        }
        return null;
    }

    public <T> T retriveSubject(String token, Class<T> type)  {
        try {
            var subject =  new ObjectMapper().readValue(
                                JWT
                                    .require(Algorithm.HMAC512(secretJWT))
                                    .build()
                                    .verify(token)
                                    .getSubject(),
                                type
                            );
            Throw.unauthorized(logger, "Authorization token is invalid or must be refreshed", subject == null);
            
            return subject;
        }catch (JsonProcessingException | IllegalArgumentException e) {
            Throw.any(logger, HttpStatus.INTERNAL_SERVER_ERROR, "Error when building token authorization");
        }catch( JWTVerificationException e){
            Throw.any(logger, HttpStatus.UNAUTHORIZED, "Authorization token must be verified or refreshed");
        }
        return null;
    }


    
}

package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.Date;

public class TokenUtil {
    public final static String token_key = "token";
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private long expirationTimeInMinute;

    public TokenUtil(long expirationTimeInMinute){
        this.expirationTimeInMinute=expirationTimeInMinute;
    }
    public String generateJWT(User user) {
        long expirationTime = expirationTimeInMinute *1000 *60;
        String jwtToken = Jwts.builder()
                .setSubject(user.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
        return jwtToken;
    }
    public void verifyToken(String jwtToken) throws ApplicationException {
        try{
            if(StringUtils.isNotBlank(jwtToken)){
                Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parse(jwtToken);
            }else{
                throw new ApplicationException("ML0001000005ERR");
            }
        }catch (Exception e){
            throw new ApplicationException("ML0001000005ERR");
        }
    }
}

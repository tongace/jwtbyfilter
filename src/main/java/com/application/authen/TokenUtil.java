package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
                .setIssuedAt(new Date())
                .setIssuer("PIHD")
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
        return jwtToken;
    }
    public String verifyToken(String jwtToken) throws ApplicationException {
        try{
            if(StringUtils.isNotBlank(jwtToken)){
                Jwt jwt = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parse(jwtToken);
                Claims claims = (Claims)jwt.getBody();
                return claims.getSubject();
            }else{
                throw new ApplicationException("ML0001000005ERR");
            }
        }catch (Exception e){
            throw new ApplicationException("ML0001000005ERR");
        }
    }

    public String extractTokenFromHttpRequestCookie(HttpServletRequest request){
        Cookie[]cookies = request.getCookies();
        String jwt = "";

        if(ArrayUtils.isNotEmpty(cookies)){
            for (Cookie cookie:cookies) {
                if(StringUtils.equalsIgnoreCase(cookie.getName(),TokenUtil.token_key)){
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        return jwt;
    }
}

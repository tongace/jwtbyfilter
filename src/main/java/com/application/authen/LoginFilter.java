package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.utils.ApiResponse;
import com.application.utils.ResponseUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
@Order(2)
public class LoginFilter extends OncePerRequestFilter {
    @Value("${jwt.expirationTime}")
    private int cookieExpiredTime;
    @Autowired
    LoginService loginSerice;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiResponse<String> resObj = new ApiResponse<>();
        if(StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.toString())){
            Login login = new Gson().fromJson(request.getReader(),Login.class);
            try{
                String jwt = loginSerice.login(login);
                log.debug("token: >>>> "+jwt);
                Cookie cookie = new Cookie("token",jwt);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(cookieExpiredTime*60);
                response.addCookie(cookie);
            }catch (ApplicationException ae){
                resObj.setMessageCode(ae.getMessageCode());
                ResponseUtil.createJsonResponse(response,HttpServletResponse.SC_UNAUTHORIZED,resObj);
            }
        }else {
            ResponseUtil.createJsonResponse(response,HttpServletResponse.SC_METHOD_NOT_ALLOWED,resObj);
        }
    }
}

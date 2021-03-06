package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.utils.ApiResponse;
import com.application.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    TokenUtil tokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiResponse<String> resObj = new ApiResponse<>();
        String requestUri = request.getRequestURI();
        if(StringUtils.equalsIgnoreCase(requestUri,"/login")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwt = tokenUtil.extractTokenFromHttpRequestCookie(request);

        try {
            tokenUtil.verifyToken(jwt);
            filterChain.doFilter(request,response);
        } catch (ApplicationException ae) {
            resObj.setMessageCode(ae.getMessageCode());
            ResponseUtil.createJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, resObj);
        }
    }
}

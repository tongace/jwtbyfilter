package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.utils.ApiResponse;
import com.application.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    TokenUtil tokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiResponse<String> resObj = new ApiResponse<>();
        Cookie []cookies = request.getCookies();
        String jwt = "";
        for (Cookie cookie:cookies) {
            if(StringUtils.equalsIgnoreCase(cookie.getName(),TokenUtil.token_key)){
                jwt = cookie.getValue();
                break;
            }
        }
        if(StringUtils.isNotBlank(jwt)) {
            try {
                tokenUtil.verifyToken(jwt);
                filterChain.doFilter(request,response);
            } catch (ApplicationException ae) {
                resObj.setMessageCode(ae.getMessageCode());
                ResponseUtil.createJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, resObj);
            }
        }
    }
}

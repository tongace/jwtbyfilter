package com.application.authen;

import com.application.exception.ApplicationException;
import com.application.utils.ApiResponse;
import com.application.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Order(3)
@Slf4j
public class ResourceAccessibleFilter extends OncePerRequestFilter {
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    MenuService menuService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiResponse<String> resObj = new ApiResponse<>();
        String requestUri = request.getRequestURI();
        try {
            String jwt = tokenUtil.extractTokenFromHttpRequestCookie(request);
            String userId = tokenUtil.verifyToken(jwt);
            List<Menu> menuList = menuService.getMenuFromUserId(userId);

            Menu menu = menuList.stream()
                        .filter(menu1 -> StringUtils.startsWithIgnoreCase(requestUri,menu1.getUri()))
                        .findAny()
                        .orElse(null);
            log.info("requestUri >>>> "+requestUri);
            log.info("select Menu >>>> "+menu);
            if(menu==null){
                resObj.setMessageCode("ML0001000006ERR");
                ResponseUtil.createJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, resObj);
            }else {
                filterChain.doFilter(request,response);
            }
        } catch (ApplicationException ae) {
            resObj.setMessageCode(ae.getMessageCode());
            ResponseUtil.createJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, resObj);
        }
    }
}

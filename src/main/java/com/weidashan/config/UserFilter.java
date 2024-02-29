package com.weidashan.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/app-cart/*", "/app-order/*", "/sec-kill/secKillNow"})
public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getMethod().equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String token = request.getHeader("token");
        if (token !=null && token.length()>0){
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("weidashan")).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

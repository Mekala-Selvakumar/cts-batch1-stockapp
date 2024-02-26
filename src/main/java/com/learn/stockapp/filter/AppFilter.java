package com.learn.stockapp.filter;


/* create  a filter class to  filter the request  from the client
 * if the request is valid then it will be forwarded to the controller
 * else it will be blocked
 * check if the jwt token is valid for all the request 
 * except for the /users/register and /users/login
 * it should extends  OncePerRequestFilter
 * 
 */

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
 import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

 
import java.io.IOException;



//@Component
public class AppFilter extends OncePerRequestFilter {

    @Value("${app.jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/api/v1/users/register") || requestURI.equals("/api/v1/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                System.out.println("Secret: " + secret);
                System.out.println("token: " + token);
              String username= Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
             if (username != null) { 
              filterChain.doFilter(request, response);
             } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Token...........");
             }
            } catch (Exception e) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid Token");
            }
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token is missing");
        }
    }
}





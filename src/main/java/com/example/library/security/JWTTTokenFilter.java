//package com.example.library.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.net.Authenticator;
//import org.springframework.http.HttpHeaders;
//import java.util.List;
//
//public class JWTTTokenFilter extends OncePerRequestFilter {
//    private final String key;
//
//    public JWTTTokenFilter(String key){
//        this.key = key;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authheader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authheader!=null && authheader.startsWith("Bearer ")) {
//            String token = authheader.split(" ")[1];
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//            String userId = (String) claims.get("id");
//            String userRole = (String) claims.get("role");
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(userId, null,
//                            List.of(new SimpleGrantedAuthority(userRole)));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        filterChain.doFilter(request, response);
//    }
//}

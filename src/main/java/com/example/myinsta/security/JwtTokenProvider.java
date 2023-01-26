package com.example.myinsta.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

        private final CustomDetailService customDetailService;
        @Value("${jwt.token.secret-key}")
        private String secretKey;

        @Value("${jwt.token.expire-length}")
        private long expireTime;

        public SignInResponseDto generateToken(Authentication authentication) {
            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            long now = (new Date()).getTime();
            Date accessTokenExpiresIn = new Date(now + expireTime);
            String accessToken = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("auth", authorities)
                    .setExpiration(accessTokenExpiresIn)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();

            return SignInResponseDto.builder()
                    .grantType("Bearer")
                    .accessToken(accessToken)
                    .build();
        }

        public Authentication getAuthentication(String accessToken) {
            Claims claims = parseClaims(accessToken);

            if (claims.get("auth") == null) {
                throw new JwtCustomException("No Authority info", HttpStatus.UNAUTHORIZED);
            }

            UserDetails memberDetails = customDetailService.loadUserByUsername(claims.getSubject());
            return new UsernamePasswordAuthenticationToken(customDetailService, "", memberDetails.getAuthorities());
        }

        public String resolveToken(HttpServletRequest req) {
            String bearerToken = req.getHeader("Authorization");
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(6).trim();
            }
            return null;
        }
        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                throw new JwtCustomException("Error on Token", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        private Claims parseClaims(String accessToken) {
            try {
                return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
            } catch (ExpiredJwtException e) {
                return e.getClaims();
            }
        }

    }

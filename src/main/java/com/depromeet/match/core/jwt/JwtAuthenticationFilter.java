package com.depromeet.match.core.jwt;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtAuthenticationFilter extends GenericFilter {
    private static final String TOKEN_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            String jwt = obtainAuthorizationToken(request);
            if (Objects.nonNull(jwt)) {
                Claims claims = JwtResolver.parseJwtToClaims(jwt);
                long id = claims.get("id", Long.class);
                String nickName = claims.get("nickName", String.class);
                String profileImageUrl = claims.get("profileImageUrl", String.class);
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                    new JwtAuthentication(id, nickName, profileImageUrl),
                    null,
                    null //권한은 생략한다.
                );
                jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
            }
        }

        chain.doFilter(req, res);
    }

    private String obtainAuthorizationToken(HttpServletRequest req) {
        String token = req.getHeader(TOKEN_HEADER);
        if (Objects.nonNull(token)) {
            return token;
        }

        return null;
    }
}

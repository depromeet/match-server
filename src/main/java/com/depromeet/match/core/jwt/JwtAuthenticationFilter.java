package com.depromeet.match.core.jwt;

import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import static java.util.stream.Collectors.toList;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilter {
    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    private static final String TOKEN_HEADER = "Authorization";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            String jwt = obtainAuthorizationToken(request);
            Claims claims;
            if (Objects.nonNull(jwt) && Objects.nonNull(claims = JwtResolver.parseJwtToClaims(jwt))) {
                long id = claims.get("id", Long.class);
                String nickName = claims.get("nickName", String.class);
                String profileImageUrl = claims.get("profileImageUrl", String.class);
                List<String> roles = claims.get("roles", List.class);
                List<GrantedAuthority> authorities = obtainAuthorities(roles);
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                    new JwtAuthentication(id, nickName, profileImageUrl),
                    null,
                    authorities
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
            if (log.isDebugEnabled())
                log.debug("Jwt authorization api detected: {}", token);
            try {
                token = URLDecoder.decode(token, StandardCharsets.UTF_8.name());
                String[] parts = token.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];
                    return BEARER.matcher(scheme).matches() ? credentials : null;
                }
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
            return token;
        }

        return null;
    }

    private List<GrantedAuthority> obtainAuthorities(List<String> roles) {
        return roles == null || roles.isEmpty() ?
                Collections.emptyList() :
                roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }
}

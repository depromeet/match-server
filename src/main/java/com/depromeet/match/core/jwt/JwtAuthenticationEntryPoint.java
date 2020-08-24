package com.depromeet.match.core.jwt;

import com.depromeet.match.core.response.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ApiError E401 = new ApiError("Authentication error (cause: unauthorized)");

    private final ObjectMapper om;

    public JwtAuthenticationEntryPoint(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res,
        AuthenticationException authException) throws IOException, ServletException {
        res.setStatus(401);
        res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8;");
        res.getWriter().write(om.writeValueAsString(E401));
        res.getWriter().flush();
        res.getWriter().close();
    }
}

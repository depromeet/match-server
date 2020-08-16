package com.depromeet.match.core.jwt;

import com.depromeet.match.core.response.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private static final ApiError E403 = new ApiError("Authentication error (cause: forbidden)");

    private final ObjectMapper om;

    public JwtAccessDeniedHandler(ObjectMapper om){
        this.om = om;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            res.setStatus(403);
            res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8;");
            res.getWriter().write(om.writeValueAsString(E403));
            res.getWriter().flush();
            res.getWriter().close();
    }

}

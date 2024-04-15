package com.llc.search_service.domain.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

    private AntPathRequestMatcher matcher;
    public JsonUsernamePasswordAuthenticationFilter(AntPathRequestMatcher matcher, AuthenticationManager authenticationManager) {
        super(matcher, authenticationManager);
        this.matcher = matcher;
    }
    protected JsonUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!Objects.requireNonNullElse(matcher, DEFAULT_ANT_PATH_REQUEST_MATCHER).matches(request)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String contentType = request.getContentType();
        if (!contentType.equals(MediaType.APPLICATION_JSON_VALUE) && !contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            throw new AuthenticationServiceException("Authentication content type not supported: " + contentType);
        }
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest = null;
        try (InputStream is = request.getInputStream()) {
            var type = new TypeReference<Map<String, String>>() {};
            Map<String, String> data = mapper.readValue(is, type);
            String username = data.getOrDefault("username", "");
            String password = data.getOrDefault("password", "");
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            log.warn("Json Authentication error: {}", e.getMessage());
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

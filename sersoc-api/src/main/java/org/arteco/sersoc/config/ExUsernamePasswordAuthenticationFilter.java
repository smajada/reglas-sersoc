package org.arteco.sersoc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ExUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly  && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String tipaut = request.getParameter("tipaut");
        if (username == null) { username = ""; }
        if (password == null) { password = ""; }
        username = username.trim();

        // Cream una instancia que té la responsabilitat de ????
        SersocTokenAuthentication authRequest = new SersocTokenAuthentication(username, password, tipaut);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
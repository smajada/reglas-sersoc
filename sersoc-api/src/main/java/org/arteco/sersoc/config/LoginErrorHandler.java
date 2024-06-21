package org.arteco.sersoc.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

import org.arteco.sersoc.config.exceptions.CanviarPasswordException;
import org.arteco.sersoc.config.exceptions.ErrorInternException;
import org.arteco.sersoc.config.exceptions.FaltaRolCredentialsException;
import org.arteco.sersoc.config.exceptions.UsuariDeshabilitatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

public class LoginErrorHandler extends SimpleUrlAuthenticationFailureHandler {
	private static final Logger LOG = LoggerFactory.getLogger(LoginErrorHandler.class.getCanonicalName());

	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_TIPAUT = "tipaut";
	private static final String PARAM_KEY_ERR = "&keyErr=";
	private static final String PARAM_KEY_USER = "&user=";
	private static final String URL_LOGIN_ERR = "/login/error?tipaut=";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		String keyErr;
		LOG.info("LoginErrorHandler.onAuthenticationFailure: exception={}, message={}",
				 exception.getClass().getName(), exception.getLocalizedMessage());
		if (exception instanceof CanviarPasswordException) {
			getRedirectStrategy().sendRedirect(request, response, "/login/canvipwd?usuari="
				+ request.getParameter(PARAM_USERNAME));
		} else if (exception instanceof UsuariDeshabilitatException) { // Les 4 urls seguents son iguals.
			keyErr="imiloginerror.deshabilitat";
			getRedirectStrategy().sendRedirect(request, response, URL_LOGIN_ERR
				+ request.getParameter(PARAM_TIPAUT) + PARAM_KEY_ERR + keyErr
				+ PARAM_KEY_USER + request.getParameter(PARAM_USERNAME));
		} else if (exception instanceof FaltaRolCredentialsException) {
			keyErr="imiloginerror.noterol";
			getRedirectStrategy().sendRedirect(request, response, URL_LOGIN_ERR
				+ request.getParameter(PARAM_TIPAUT) + PARAM_KEY_ERR + keyErr
				+ PARAM_KEY_USER + request.getParameter(PARAM_USERNAME));
		} else if (exception instanceof BadCredentialsException) {
			keyErr="imiloginerror.info";
			getRedirectStrategy().sendRedirect(request, response, URL_LOGIN_ERR
				+ request.getParameter(PARAM_TIPAUT) + PARAM_KEY_ERR + keyErr
				+ PARAM_KEY_USER + request.getParameter(PARAM_USERNAME));
		} else if (exception instanceof ErrorInternException) {
			keyErr="imiloginerror.intern";
			getRedirectStrategy().sendRedirect(request, response, URL_LOGIN_ERR
				+ request.getParameter(PARAM_TIPAUT) + PARAM_KEY_ERR + keyErr
				+ PARAM_KEY_USER + request.getParameter(PARAM_USERNAME));
		}
	}
}

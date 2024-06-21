package org.arteco.sersoc.config.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class ErrorInternException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public ErrorInternException(String msg) {
		super(msg);
	}

}

package org.arteco.sersoc.config.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class CanviarPasswordException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public CanviarPasswordException(String msg) {
		super(msg);
	}

}

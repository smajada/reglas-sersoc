package org.arteco.sersoc.config.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class UsuariDeshabilitatException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public UsuariDeshabilitatException(String msg) {
		super(msg);
	}

}

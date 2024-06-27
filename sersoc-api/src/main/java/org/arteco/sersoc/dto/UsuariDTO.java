package org.arteco.sersoc.dto;

import es.palma.imicorpservices.common.dto.usuari.PublicUserDto;

public class UsuariDTO extends PublicUserDto {
	public UsuariDTO(PublicUserDto puser) {
		super(puser.getCoa(), puser.getName(), puser.getEmail(), puser.getUsuCoaDit(), puser.getTipaut(),
			  puser.getApl(), puser.getPercon(),puser.getPerfils());
	}
}

package org.arteco.sersoc.service;

import es.palma.imicorpservices.common.dto.session.NlsSessionParametersDto;
import es.palma.imicorpservices.common.dto.usuari.CanviPasswordDto;
import es.palma.imicorpservices.common.dto.usuari.GenUsuariDto;
import es.palma.imicorpservices.common.dto.usuari.LoginResultDto;
import java.util.Locale;
import org.arteco.sersoc.dto.UsuariDto;

public interface ImicorpService {
	LoginResultDto login(GenUsuariDto usuari);
	UsuariDto getPublicUserDto(String token);
	CanviPasswordDto canviarPassword(CanviPasswordDto dto, Locale locale);
	CanviPasswordDto inicialitzarPassword(CanviPasswordDto dto, Locale locale);

	NlsSessionParametersDto dbInfo();

}

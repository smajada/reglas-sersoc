package org.arteco.sersoc.service;

import es.palma.imicorpservices.common.dto.usuari.GenUsuariDto;
import es.palma.imicorpservices.common.dto.usuari.LoginResultDto;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.arteco.sersoc.dto.UsuariDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class ImiCorpService {

	private final Logger logger = LoggerFactory.getLogger(ImiCorpService.class);
	private final WebClient webClient = WebClient.create();
	@Value("${imi.service.api.host}")
	private String servicesApi;
	@Value("${imi.sersoc.apl}")
	private String application;
	@Value("${imi.http.auth-token-header-name}")
	private String principalRequestHeader;
	@Value("${corpapi.http.auth-token}")
	private String principalRequestValue;

	private String getBaseServiceUrl() {

		return servicesApi;
	}

	public LoginResultDto login(GenUsuariDto usuari) {

		String url = getBaseServiceUrl() + "/doLoginRest?apl=" + application;

		ResponseEntity<LoginResultDto> response = ResponseEntity.ofNullable(null);

		try {
			response = webClient.post()
				.uri(url)
				.header(principalRequestHeader, principalRequestValue)
				.bodyValue(usuari)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseEntity<LoginResultDto>>() {
				})
				.block();
		} catch (WebClientResponseException e) {
			// Handle exception
			logger.error("Error: {}", e.getMessage());
		}

		assert response != null;
		return response.getBody() == null ? new LoginResultDto() : response.getBody();

	}

	public UsuariDTO getPublicUserDto(String token) {

		String url = getBaseServiceUrl() + "/auth?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);

		ResponseEntity<UsuariDTO> response = ResponseEntity.ofNullable(null);

		try {
			response = webClient.get()
				.uri(url)
				.header(principalRequestHeader, principalRequestValue)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<ResponseEntity<UsuariDTO>>() {
				})
				.block();
		} catch (WebClientResponseException e) {
			// Handle exception
			logger.error("Error: {}", e.getMessage());
		}

		assert response != null;
		return response.getBody() == null ? null : new UsuariDTO(response.getBody());
	}


}

package org.arteco.sersoc.service;

import es.palma.imicorpservices.common.dto.usuari.GenUsuariDto;
import es.palma.imicorpservices.common.dto.usuari.LoginResultDto;
import es.palma.imicorpservices.common.utils.HttpUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class imiCorpService {
	@Value("${imi.service.api.host}")
	private String servicesApi;
	@Value("${imi.sersoc.apl}")
	private String application;
	@Value("${imi.http.auth-token-header-name}")
	private String principalRequestHeader;
	@Value("${corpapi.http.auth-token}")
	private String principalRequestValue;

	private RestTemplate restTemplate = new RestTemplate();

	private String getBaseServiceUrl() { return servicesApi; }

	public LoginResultDto login(GenUsuariDto usuari) {

		Map<String, String> params = new HashMap<>();
		params.put("apl", application);
		String url = getBaseServiceUrl() + "/doLoginRest?apl={apl}";
		ResponseEntity<LoginResultDto> response = restTemplate.exchange(url, HttpMethod.POST,
																		new HttpEntity<>(usuari,
																						 HttpUtil.createHeaders(
																							 principalRequestHeader,
																							 principalRequestValue)),
																		LoginResultDto.class, params);
		return response.getBody() == null ? new LoginResultDto() : response.getBody();
	}




}

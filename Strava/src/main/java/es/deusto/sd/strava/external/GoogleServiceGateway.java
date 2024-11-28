package es.deusto.sd.strava.external;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.strava.entity.ServidorAuth;


@Component
public class GoogleServiceGateway implements ILoginServiceGateway{
	private final String API_URL = "http://localhost:8081/auth/login";
	
	public boolean login(String email, String password) {
		
		//template
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> requestBody = Map.of("email", email, "password", password);
        
		//headers
		HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        //entidad http
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
        	restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {}); //map.class ult parametro			
		} catch (Exception e) {
			return false;
		}
        return true;
	}

	@Override
	public ServidorAuth getServidorAuth() {
		return ServidorAuth.GOOGLE;
	}
}

package es.deusto.sd.strava.external;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class LoginServiceGateway implements ILoginServiceGateway{
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
        
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {}); //map.class ult parametro
        // Verificamos si la respuesta tiene el código de estado OK
        if (response.getStatusCode() == HttpStatus.OK) {
            // Si el código es OK, devolvemos true
            return true;
        } else {
            // Si el código no es OK (401 Unauthorized), devolvemos false
            return false;
        }
	}
}

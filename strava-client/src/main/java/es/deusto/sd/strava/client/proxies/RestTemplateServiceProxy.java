/**
 * This code is based on solutions provided by Claude Sonnet 3.5 and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.strava.client.proxies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import es.deusto.sd.strava.client.data.Reto;
import es.deusto.sd.strava.client.data.TokenId;
import es.deusto.sd.strava.client.data.Usuario;
import es.deusto.sd.strava.client.data.Credenciales;
import es.deusto.sd.strava.client.data.Entrenamiento;

@Service
public class RestTemplateServiceProxy implements IStravaServiceProxy{

    private final RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public RestTemplateServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TokenId login(Credenciales credentials) {
        String url = apiBaseUrl + "/auth/login";
	        
        try {
            return restTemplate.postForObject(url, credentials, TokenId.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Login failed: Invalid credentials.");
                default -> throw new RuntimeException("Login failed: " + e.getStatusText());
            }
        }
    }
    
    @Override    
    public void logout(String token) {
        String url = String.format("%s/auth/logout?userToken=%s", apiBaseUrl, token);
        
        try {
            restTemplate.postForObject(url, token, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Logout failed: Invalid token.");
                default -> throw new RuntimeException("Logout failed: " + e.getStatusText());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Entrenamiento> getTrainings(String userToken, int userId, Long fechaInicio, Long fechaFin) {
    	String url = String.format("%s/strava/users/%d/trainings?userToken=%s%s%s", 
                apiBaseUrl, 
                userId, 
                userToken,
                fechaInicio != null ? "&FechaInicio=" + fechaInicio : "",
                fechaFin != null ? "&FechaFin=" + fechaFin : "");
    	try {
    		return restTemplate.getForObject(url, List.class);
    		
    	} catch (HttpStatusCodeException e) {
    		switch (e.getStatusCode().value()) {
    		case 401: throw new RuntimeException("Credenciales incorrectas");
    		default: throw new RuntimeException("Error al recuperar los entrenamientos");
    		}
    	}
    }

    @Override
	public void register(Usuario usuario) {
    	String url = String.format("%s/auth/register", apiBaseUrl);
        
        try {
            restTemplate.postForObject(url, usuario, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 400 -> throw new RuntimeException("Register failed");
                default -> throw new RuntimeException("Register failed: " + e.getStatusText());
            }
        }
        
		
	}


    @Override
    public void addTraining(String userToken, int userId, Entrenamiento training) {
        String url = String.format("%s/strava/users/%d/trainings?userToken=%s", apiBaseUrl, userId, userToken);

        try {
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Content-Type", "application/json");
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(training, headers), Void.class);
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
        	switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Unauthorized: Invalid token");
                default -> throw new RuntimeException("Failed to add training: " + e.getStatusText());
            }
        }
    }

	@Override
	public void addReto(String userToken, Reto reto) {
        String url = String.format("%s/strava/challenges?userToken=%s", apiBaseUrl, userToken);

        try {
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Content-Type", "application/json");
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(reto, headers), Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Unauthorized: Invalid token");
                default -> throw new RuntimeException("Failed to add reto: " + e.getStatusText());
            }
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reto> getChallenges(String userToken, Long fechaInicio, Long fechaFin, String deporte) {
		String url = String.format("%s/strava/challenges?userToken=%s%s%s%s", 
                apiBaseUrl,  
                userToken,
                fechaInicio != null ? "&FechaInicio=" + fechaInicio : "",
                fechaFin != null ? "&FechaFin=" + fechaFin : "",
                deporte != null ? "&Deporte=" + deporte : "");
		try {
    		return restTemplate.getForObject(url, List.class);
    		
    	} catch (HttpStatusCodeException e) {
    		switch (e.getStatusCode().value()) {
    		case 401: throw new RuntimeException("Credenciales incorrectas");
    		default: throw new RuntimeException("Error al recuperar los retos");
    		}
    	}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Double> getUserChallenges(String userToken, int userId) {
		String url = String.format("%s/strava/users/%d/challenges?userToken=%s", apiBaseUrl, userId ,userToken);
		try {
    		return restTemplate.getForObject(url, Map.class);
    		
    	} catch (HttpStatusCodeException e) {
    		switch (e.getStatusCode().value()) {
    		case 401: throw new RuntimeException("Credenciales incorrectas");
    		default: throw new RuntimeException("Error al recuperar los retos");
    		}
    	}
	}

	@Override
	public Reto getChallengeDetail(String userToken, int idReto) {
		String url = String.format("%s/strava/challenges/%d?userToken=%s", apiBaseUrl, idReto ,userToken);
		try {
    		return restTemplate.getForObject(url, Reto.class);
    
    	} catch (HttpStatusCodeException e) {
    		switch (e.getStatusCode().value()) {
    		case 404: throw new RuntimeException("Reto no encontrado");
    		case 401: throw new RuntimeException("Credenciales incorrectas");
    		default: throw new RuntimeException("Error al recuperar los retos");
    		}
    	}
	}

	@Override
	public void acceptChallenge(String userToken, int userId, int idReto) {
        String url = String.format("%s/strava/users/%d/challenges/%d", apiBaseUrl, userId, idReto);

        try {
            restTemplate.postForObject(url, userToken, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Unauthorized: Invalid token");
                default -> throw new RuntimeException("Failed to accept training: " + e.getStatusText());
            }
        }
		
	}


}
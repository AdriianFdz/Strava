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
import es.deusto.sd.strava.client.data.Usuario;
import es.deusto.sd.strava.client.data.Credenciales;
import es.deusto.sd.strava.client.data.Entrenamiento;

/**
 * RestTemplateServiceProxy class is an implementation of the Service Proxy design pattern.
 * This class acts as an intermediary between the client and the RESTful web service,
 * encapsulating all the REST API calls using Spring's RestTemplate and handling various 
 * exceptions that may occur during these interactions. This class serves as an intermediary 
 * for the client to perform CRUD operations, such as user authentication (login/logout),
 * retrieving categories and articles, and placing bids on articles. By encapsulating 
 * the HTTP request logic and handling various exceptions, this proxy provides a cleaner 
 * interface for clients to interact with the underlying service.
 * 
 * The @Service annotation indicates that this class is a Spring service component, 
 * which allows it to be detected and managed by the Spring container. This enables 
 * dependency injection for the RestTemplate instance, promoting loose coupling and 
 * enhancing testability.
 * 
 * RestTemplate is a synchronous client provided by Spring for making HTTP requests. 
 * It simplifies the interaction with RESTful services by providing a higher-level 
 * abstraction over the lower-level `HttpURLConnection`. Particularities of using 
 * RestTemplate include its capability to automatically convert HTTP responses into 
 * Java objects using message converters, support for various HTTP methods (GET, POST, 
 * PUT, DELETE), and built-in error handling mechanisms. However, it's important to 
 * note that since RestTemplate is synchronous, it can block the calling thread, which 
 * may not be suitable for high-performance applications that require non-blocking 
 * behavior.
 * 
 * (Description generated with ChatGPT 4o mini)
 */
@Service
public class RestTemplateServiceProxy implements IAuctionsServiceProxy{

    private final RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public RestTemplateServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String login(Credenciales credentials) {
        String url = apiBaseUrl + "/auth/login";
	        
        try {
            return restTemplate.postForObject(url, credentials, String.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Login failed: Invalid credentials.");
                default -> throw new RuntimeException("Login failed: " + e.getStatusText());
            }
        }
    }
    
    @Override    
    public void logout(String token) {
        String url = apiBaseUrl + "/auth/logout";
        
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
    	System.out.println(url);
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
		// TODO Auto-generated method stub
		
	}


    @Override
    public void addTraining(String userToken, int userId, Entrenamiento training) {
        String url = String.format("%s/strava/users/%d", apiBaseUrl, userId);

        try {
            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Content-Type", "application/json");
            Map<String, Object> requestBody = Map.of("userToken", userToken, "Training", training );
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(requestBody, headers), Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Unauthorized: Invalid token");
                default -> throw new RuntimeException("Failed to add training: " + e.getStatusText());
            }
        }
    }

	@Override
	public void addReto(String userToken, Reto reto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reto> getChallenges(String userToken, long fechaInicio, long fechaFin, String deporte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Double> getUserChallenges(String userToken, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reto getChallengeDetail(String userToken, int idReto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void acceptChallenge(String userToken, int userId, int idReto) {
		// TODO Auto-generated method stub
		
	}


}
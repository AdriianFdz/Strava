/**
 * This code is based on solutions provided by Claude Sonnet 3.5 and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.strava.client.proxies;

import java.util.List;
import java.util.Map;

import es.deusto.sd.strava.client.data.Reto;
import es.deusto.sd.strava.client.data.TokenId;
import es.deusto.sd.strava.client.data.Usuario;
import es.deusto.sd.strava.client.data.Credenciales;
import es.deusto.sd.strava.client.data.Entrenamiento;

/**
 * IAuctionsServiceProxy interface defines a contract for communication 
 * with the AuctionsService, enabling different implementations to provide 
 * the same set of functionalities for client interactions. This interface 
 * is aligned with the Service Proxy design pattern, which aims to create 
 * an intermediary between the client and the underlying service. It includes 
 * methods for user authentication (login/logout), retrieving categories 
 * and articles, and placing bids on articles.
 * 
 * By defining a common interface, we promote loose coupling between 
 * the client code and the underlying service implementations. This allows 
 * for greater flexibility and easier maintenance, as clients can work 
 * with any implementation of the interface without needing to know the 
 * specifics of how the HTTP communication is handled. For instance, 
 * both `HttpServiceProxy` and `RestTemplateServiceProxy` can implement 
 * this interface, allowing developers to switch between different 
 * implementations based on performance requirements, error handling strategies, 
 * or other factors without modifying the client code.
 * 
 * Additionally, using an interface facilitates unit testing and mocking, 
 * as it allows for the creation of test doubles that adhere to the same 
 * contract. This leads to more robust and maintainable code, as the 
 * interface serves as a clear specification of the expected behavior 
 * for any service proxy implementation.
 * 
 * (Description generated with ChatGPT 4o mini)
 */
public interface IStravaServiceProxy {
	
	void register(Usuario usuario);

	// Method for user login
	TokenId login(Credenciales credentials);

	// Method for user logout
	void logout(String token);

	List<Entrenamiento> getTrainings(String userToken, int userId, Long fechaInicio, Long fechaFin);
	
	void addTraining(String userToken, int userId, Entrenamiento training);
	
	void addReto(String userToken, Reto reto);
	
	List<Reto> getChallenges(String userToken, Long fechaInicio, Long fechaFin, String deporte);
	
	Map<Integer, Double> getUserChallenges(String userToken, int userId);
	
	Reto getChallengeDetail(String userToken, int idReto);
	
	void acceptChallenge(String userToken, int userId, int idReto);

	List<String> getSports(String userToken);

	List<String> getObjectives(String userToken);
	
	List<String> getAuths();
}

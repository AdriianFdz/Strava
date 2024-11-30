package es.deusto.sd.strava.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dao.UsuarioRepository;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.external.LoginGatewayFactory;
import jakarta.validation.constraints.Null;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    LoginGatewayFactory loginGatewayFactory = new LoginGatewayFactory();
    
    // Storage to keep the session of the Usuarios that are logged in
    private static Map<String, Usuario> tokenStore = new HashMap<>(); 

	public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    
    // Login method that checks if the Usuario exists in the database and validates the password
    public Optional<String> login(String email, String password) {	
    	Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
    	if (usuario.isEmpty()) {
    		return Optional.of("Invalid email");			
		}
    	
    	ResponseEntity<String> resultado = loginGatewayFactory.getLoginServiceGateway(usuario.get().getServidorAuth()).login(email, password);

    	if (resultado.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
    		return Optional.of("Invalid password");
    	}
    	
		if(resultado.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
			String token = generateToken();  // Generate a random token for the session
			tokenStore.put(token, usuario.get());     // Store the token and associate it with the Usuario
			return Optional.of(token);
		}
		return Optional.of("Internal Server Error");
    }
    
    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    // Method to add a new Usuario to the repository
    public void register(Usuario Usuario) {
    	if (Usuario != null) {
    		usuarioRepository.save(Usuario);
    	}
    }
    
	public boolean isValidTokenWithUser(String token, Usuario u) {
		return tokenStore.containsKey(token) && tokenStore.get(token).equals(u);
	}
	
	public boolean isValidToken(String token) {
		return tokenStore.containsKey(token);
	}
	
    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

}
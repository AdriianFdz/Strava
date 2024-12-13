package es.deusto.sd.strava.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dao.UsuarioRepository;
import es.deusto.sd.strava.dto.TokenIdDTO;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.external.LoginGatewayFactory;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    
    // Storage to keep the session of the Usuarios that are logged in
    private static Map<String, Usuario> tokenStore = new HashMap<>(); 

	public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    
    // Login method that checks if the Usuario exists in the database and validates the password
    public TokenIdDTO login(String email, String password) {	
    	Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
    	TokenIdDTO resultado = new TokenIdDTO();
    	if (usuario.isEmpty()) {
    		resultado.setToken("Invalid email");
    		return resultado;			
		}
    	
    	ResponseEntity<String> resultadoFactory = LoginGatewayFactory.getLoginServiceGateway(usuario.get().getServidorAuth()).login(email, password);

    	if (resultadoFactory.getStatusCode().isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
    		resultado.setToken("Invalid password");
    		return resultado;
    	}
    	
		if(resultadoFactory.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
			String token = generateToken();  // Generate a random token for the session
			tokenStore.put(token, usuario.get());     // Store the token and associate it with the Usuario
			resultado.setToken(token);
			resultado.setId(usuario.get().getId());
			return resultado;
		}
		resultado.setToken("Internal Server Error");
		return resultado;
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
    public boolean register(Usuario Usuario) {
    	if (Usuario != null && !usuarioRepository.existsByEmail(Usuario.getEmail())) {
    		usuarioRepository.save(Usuario);
    		return true;
    	}
    	return false;
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
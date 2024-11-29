package es.deusto.sd.strava.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dao.UsuarioRepository;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.external.LoginGatewayFactory;

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
		if (usuario.isPresent() && loginGatewayFactory.getLoginServiceGateway(usuario.get().getServidorAuth()).login(email, password)) {
			String token = generateToken();  // Generate a random token for the session
			tokenStore.put(token, usuario.get());     // Store the token and associate it with the Usuario
			return Optional.of(token); 		
		}	
    	return Optional.empty();
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
    
    // Method to get the Usuario based on the token
	public Usuario getUsuarioByToken(String token) {
		return tokenStore.get(token) != null ? tokenStore.get(token) : null;
	}
    
    // Method to get the Usuario based on the ID and token
	public Usuario getUsuarioByID(int id, String token) {
		Usuario u = tokenStore.get(token);
		if (u != null && u.getId() == id) {
			return u;
		}
		return null;
	}
    
	public boolean isValidToken(String token) {
		return tokenStore.containsKey(token);
	}
	
    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

}
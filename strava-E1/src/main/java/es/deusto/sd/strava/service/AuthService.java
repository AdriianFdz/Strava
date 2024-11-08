package es.deusto.sd.strava.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dto.UsuarioDTO;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class AuthService {
    private final AtomicInteger idUserGenerator = new AtomicInteger(0);

    // Simulating a Usuario repository
    private static Map<String, Usuario> UsuarioRepository = new HashMap<>();
    
    // Storage to keep the session of the Usuarios that are logged in
    private static Map<String, Usuario> tokenStore = new HashMap<>(); 

    // Login method that checks if the Usuario exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        Usuario Usuario = UsuarioRepository.get(email);
        
        if (Usuario != null) {
        	// Meta o google comprueba la contraseña
        	
        	// Si es correcta
            String token = generateToken();  // Generate a random token for the session
            tokenStore.put(token, Usuario);     // Store the token and associate it with the Usuario

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
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
    		UsuarioRepository.putIfAbsent(Usuario.getEmail(), Usuario);
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

	public Usuario parseUsuarioDTO(UsuarioDTO user) {
		return new Usuario(idUserGenerator.incrementAndGet(), user.getEmail(), user.getNombre(), user.getFechaNacimiento(), user.getPeso(), user.getAltura(), user.getFrecuenciaCardiacaMax(), user.getFrecuenciaCardiacaReposo());
	}
	
    // Synchronized method to guarantee unique token generation
    private static synchronized String generateToken() {
        return Long.toHexString(System.currentTimeMillis());
    }

}
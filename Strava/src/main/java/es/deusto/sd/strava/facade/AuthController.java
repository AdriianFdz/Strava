/**
 * This code is based on solutions provided by ChatGPT 4o and 
 * adapted using GitHub Copilot. It has been thoroughly reviewed 
 * and validated to ensure correctness and that it is free of errors.
 */
package es.deusto.sd.strava.facade;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.CredencialesDTO;
import es.deusto.sd.strava.dto.TokenIdDTO;
import es.deusto.sd.strava.dto.UsuarioDTO;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "Login and logout operations")
public class AuthController {

    private final AuthService authService;
    
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
    
	// Register endpoint
	@Operation(summary = "Register a new user", description = "Allows a user to register by providing the user's details.", responses = {
			@ApiResponse(responseCode = "201", description = "Created: User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid user details or the user exists"), })
	@PostMapping("/register")
	public ResponseEntity<Void> register(
			@Parameter(name = "user", description = "User's details", required = true) 
			@RequestBody UsuarioDTO user) {
		
		Usuario u = parseUsuarioDTO(user);
		boolean resultado = authService.register(u);
		if (!resultado) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
    // Login endpoint
    @Operation(
        summary = "Login to the system",
        description = "Allows a user to login by providing email and password. Returns a token if successful.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Login successful, returns a token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid password, login failed"),
            @ApiResponse(responseCode = "404", description = "Not Found: Invalid email, login failed"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenIdDTO> login(
    		@Parameter(name = "credentials", description = "User's credentials", required = true)    	
    		@RequestBody CredencialesDTO credentials) {    	
    	
    	TokenIdDTO resultado = authService.login(credentials.getEmail(), credentials.getPassword());
    	if (resultado.getToken().equals("Invalid password")) {
    		return new ResponseEntity<>(resultado, HttpStatus.UNAUTHORIZED);
    	}
    	if (resultado.getToken().equals("Invalid email")) {
    		return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    	}
    	if (resultado.getToken().equals("Internal Server Error")) {
    		return new ResponseEntity<>(resultado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    // Logout endpoint
    @Operation(
        summary = "Logout from the system",
        description = "Allows a user to logout by providing the authorization token.",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content: Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"),
        }
    )    
    @PostMapping("/logout")    
    public ResponseEntity<Void> logout(
    		@Parameter(name = "userToken", description = "Authorization token", required = true, example = "1924888a05c")
    		@RequestParam("userToken") String userToken) {    	
        Optional<Boolean> result = authService.logout(userToken);
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
    }
    
    // parse DTO to entity
	public Usuario parseUsuarioDTO(UsuarioDTO user) {
		return new Usuario(null, user.getEmail(), user.getNombre(), user.getFechaNacimiento(), user.getPeso(), user.getAltura(), user.getFrecuenciaCardiacaMax(), user.getFrecuenciaCardiacaReposo(), user.getServidorAuth());
	}
}
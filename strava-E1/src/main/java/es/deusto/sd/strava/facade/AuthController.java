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
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.CredencialesDTO;
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

    private AuthService authService;
    
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
    
	// Register endpoint
	@Operation(summary = "Register a new user", description = "Allows a user to register by providing the user's details.", responses = {
			@ApiResponse(responseCode = "201", description = "Created: User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid user details"), })
	@PostMapping("/register")
	public ResponseEntity<Void> register(
			@Parameter(name = "user", description = "User's details", required = true) 
			@RequestBody UsuarioDTO user) {
		
		Usuario u = authService.parseUsuarioDTO(user);
		authService.register(u);
		return new ResponseEntity<>(HttpStatus.CREATED);
		
	}
    // Login endpoint
    @Operation(
        summary = "Login to the system",
        description = "Allows a user to login by providing email and password. Returns a token if successful.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Login successful, returns a token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials, login failed"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
    		@Parameter(name = "credentials", description = "User's credentials", required = true)    	
    		@RequestBody CredencialesDTO credentials) {    	
        Optional<String> token = authService.login(credentials.getEmail(), credentials.getPassword());
        
    	if (token.isPresent()) {
    		return new ResponseEntity<>(token.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
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
    		@Parameter(name = "token", description = "Authorization token", required = true, example = "Bearer 1924888a05c")
    		@RequestBody String token) {    	
        Optional<Boolean> result = authService.logout(token);
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
    }
}
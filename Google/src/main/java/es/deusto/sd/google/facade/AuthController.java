package es.deusto.sd.google.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.google.dto.CredentialsDTO;
import es.deusto.sd.google.entity.Credentials;
import es.deusto.sd.google.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "Login operations")
public class AuthController {
	
    private AuthService authService;
    
    
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
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
    		@RequestBody CredentialsDTO credentials) {    
    	Credentials credential = parseCredentialsDTO(credentials);
    	
        boolean validEmail = authService.validateEmail(credential.getEmail());
        
    	if (validEmail) {
    		boolean validCredentials = authService.validateCredentials(credential.getEmail(), credential.getPassword());
			if (validCredentials) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
			}
    	} else {
    		return new ResponseEntity<>("Invalid email", HttpStatus.UNAUTHORIZED);    	}
    }	
    
	public Credentials parseCredentialsDTO(CredentialsDTO credencials) {
		return new Credentials(credencials.getEmail(), credencials.getPassword());
	}
}

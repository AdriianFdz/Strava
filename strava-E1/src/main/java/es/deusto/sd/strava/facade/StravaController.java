package es.deusto.sd.strava.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.service.AuthService;
import es.deusto.sd.strava.service.StravaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/strava")
@Tag(name = "Strava", description = "The Strava API")
public class StravaController {
	private final AuthService authService;
	private final StravaService stravaService;
	
	public StravaController(AuthService authService, StravaService stravaService) {
		this.authService = authService;
		this.stravaService = stravaService;
	}
	
	// user trainings endpoint
	@Operation(summary = "Get user trainings", description = "Allows a user to get his trainings.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: User trainings retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@PostMapping("/trainings/get")
	public ResponseEntity<List<Entrenamiento>> getTrainings(
		@Parameter(name = "User token", description = "The token of a logged user", required = true, example = "192ee4daf90") 
		@RequestBody String userToken,
		@Parameter(name = "FechaInicio", description = "The start date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaInicio") LocalDate fechaInicio, 
		@Parameter(name = "FechaFin", description = "The end date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaFin") LocalDate fechaFin){
		Usuario u = authService.getUsuarioByToken(userToken);
		if (u == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (fechaInicio != null || fechaFin != null) {
			return new ResponseEntity<>(stravaService.filtrarEntrenamientos(u.getEntrenamientos(), fechaInicio, fechaFin), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(u.getEntrenamientos(), HttpStatus.OK);
		}
	}
	
	// create training endpoint
	@Operation(summary = "create a training", description = "Allows a user to create a training.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: Training created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@PostMapping("/trainings/create")
	public ResponseEntity<HttpStatus> addTraining(
		@Parameter(name = "Training", description = "The training object to create it", required = true)
		@RequestBody EntrenamientoDTO training) {
		Usuario u = authService.getUsuarioByToken(training.getUserToken());
		if (u == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		u.addEntrenamiento(stravaService.parseEntrenamientoDTO(training));
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
}

package es.deusto.sd.strava.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;
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
	
	@GetMapping("users/{userID}/trainings")
	public ResponseEntity<List<EntrenamientoDTO>> getTrainings(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
		@RequestParam("userToken") String userToken,
		@Parameter(name = "userID", description = "The id of a registered user", required = true, example = "1")
		@PathVariable("userID") int userID,
		@Parameter(name = "FechaInicio", description = "The start date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaInicio") Long fechaInicio, 
		@Parameter(name = "FechaFin", description = "The end date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaFin") Long fechaFin){
		Usuario u = stravaService.getUsuarioById(userID);
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		
		if (fechaInicio != null || fechaFin != null) {
			List<Entrenamiento> entrenamientos = stravaService.filtrarEntrenamientosPorUsuario(userID, fechaInicio, fechaFin);
			return new ResponseEntity<>(parseEntrenamientosToDTO(entrenamientos), HttpStatus.OK);
			
		}
		return new ResponseEntity<>(parseEntrenamientosToDTO(u.getEntrenamientos()), HttpStatus.OK);
	}
	
	// create training endpoint
	@Operation(summary = "create a training", description = "Allows a user to create a training.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: Training created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@PostMapping("users/{userID}/trainings")
	public ResponseEntity<HttpStatus> addTraining(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90")
		@RequestParam("userToken") String userToken,
		@Parameter(name = "userID", description = "The id of a registered user", required = true, example = "1")
		@PathVariable("userID") int userID,
		@Parameter(name = "Training", description = "The training object to create it", required = true)
		@RequestBody EntrenamientoDTO training) {
		Usuario u = stravaService.getUsuarioById(userID);
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		stravaService.anadirEntrenamiento(u, parseEntrenamientoDTO(training, u));
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// create challenge endpoint
	@Operation(summary = "create a challenge", description = "Allows a user to create a challenge.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: Challenge created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@PostMapping("challenges")
	public ResponseEntity<HttpStatus> addReto(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90")
		@RequestParam("userToken") String userToken,
		@Parameter(name = "Reto", description = "The challenge object to create it", required = true)
		@RequestBody RetoDTO retoDTO) {
		if (!authService.isValidToken(userToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Reto reto = parseRetoDTO(retoDTO);
		stravaService.anadirReto(reto);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// get all challenges endpoint
	@Operation(summary = "Get all challenges", description = "Allows a registered user to view all the available challenges.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: challenges retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@GetMapping("challenges")
	public ResponseEntity<List<RetoDTO>> getChallenges(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
		@RequestParam("userToken") String userToken,
		@Parameter(name = "FechaInicio", description = "The start date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaInicio") Long fechaInicio, 
		@Parameter(name = "FechaFin", description = "The end date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaFin") Long fechaFin,
	    @Parameter(name = "Deporte", description = "Type of sport", required = false)
	    @RequestParam(required = false, name = "Deporte") TipoDeporte deporte){
		if (!authService.isValidToken(userToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		if (fechaInicio != null || fechaFin != null || deporte != null) {
			return new ResponseEntity<>(parseRetosToDTO(stravaService.filtrarRetos(fechaInicio, fechaFin, deporte)), HttpStatus.OK);			
		}
		return new ResponseEntity<>(parseRetosToDTO(stravaService.getTop5Retos()), HttpStatus.OK);
	}
	
	//get users challenges endpoint
	@Operation(summary = "Get user challenges", description = "Allows a user to get his acepted challenges.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: User challenges retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@GetMapping("users/{userID}/challenges")
	public ResponseEntity<Map<Integer, Double>> getUserChallenges(
			@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
			@RequestParam("userToken") String userToken,
			@Parameter(name = "userID", description = "The id of a registered user", required = true, example = "1")
			@PathVariable("userID") int userID){
		Usuario u = stravaService.getUsuarioById(userID);
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		return new ResponseEntity<>(stravaService.calcularPorcentajeReto(u.getRetosAceptados(), u.getEntrenamientos()), HttpStatus.OK);
	}
	
	// get challenge details endpoint
	@Operation(summary = "Get details of a specific challenge", description = "Allows a registered user to view the details of an specific challenge.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: challenge retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"),
			@ApiResponse(responseCode = "404", description = "Not found: The ID of the challengue doesn't exist"), })
	
	@GetMapping("challenges/{idReto}")
	public ResponseEntity<RetoDTO> getChallengeDetail(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
		@RequestParam("userToken") String userToken,
	    @Parameter(name = "idReto", description = "The ID of a challenge", required = true)
	    @RequestParam("idReto") int idReto){
		if (!authService.isValidToken(userToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		Reto r = stravaService.getRetoById(idReto);
		if (r == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(parseToRetoDTO(r), HttpStatus.OK);
	}
	
	//accept challenge endpoint
	@Operation(summary = "Accept a challenge", description = "Allows a user to accept a challenge.", responses = {
            @ApiResponse(responseCode = "200", description = "Success: Challenge accepted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@PostMapping("users/{userID}/challenges/{idReto}")
	public ResponseEntity<HttpStatus> acceptChallenge(
			@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
			@RequestParam(required = true, name = "userToken") String userToken,
			@Parameter(name = "userID", description = "The id of a registered user", required = true)
			@PathVariable("userID") int userID,
			@Parameter(name = "idReto", description = "The id of the challenge to accept it", required = true)
			@PathVariable("idReto") int idReto) {
		Usuario u = stravaService.getUsuarioById(userID);
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		Reto r = stravaService.getRetoById(idReto);
		stravaService.aceptarReto(u, r);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	// parse DTO to entity
	public Entrenamiento parseEntrenamientoDTO(EntrenamientoDTO dto, Usuario u) {
		return new Entrenamiento(
            dto.getTitulo(),
            dto.getDeporte(),
            dto.getDistancia(),
            dto.getFechaHora(),
            dto.getDuracion(),
            u
        );
	}
	
	public Reto parseRetoDTO(RetoDTO dto) {
		return new Reto(
				null,
				dto.getNombre(),
				dto.getFechaInicio(),
				dto.getFechaFin(),
				dto.getObjetivo(),
				dto.getTipoObjetivo(),
				dto.getDeporte()
			);
	}
	
	public List<EntrenamientoDTO> parseEntrenamientosToDTO(List<Entrenamiento> entrenamientos) {
		List<EntrenamientoDTO> resultado = new ArrayList<>();
		for (Entrenamiento entrenamiento : entrenamientos) {
			resultado.add(new EntrenamientoDTO(entrenamiento.getTitulo(), entrenamiento.getDeporte(), entrenamiento.getDistancia(), entrenamiento.getFechaHora(), entrenamiento.getDuracion()));
		}
		return resultado;
	}
	
	public List<RetoDTO> parseRetosToDTO(List<Reto> retos) {
		List<RetoDTO> resultado = new ArrayList<>();
		for (Reto reto : retos) {
			resultado.add(new RetoDTO(reto.getNombre(), reto.getFechaInicio(), reto.getFechaFin(), reto.getObjetivo(),reto.getTipoObjetivo(), reto.getDeporte()));
		}
		return resultado;
		
	}
	
	public RetoDTO parseToRetoDTO(Reto reto) {
		return new RetoDTO(reto.getNombre(), reto.getFechaInicio(), reto.getFechaFin(), reto.getObjetivo(),
				reto.getTipoObjetivo(), reto.getDeporte());
	}
}

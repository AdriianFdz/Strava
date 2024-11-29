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

import es.deusto.sd.strava.dao.EntrenamientoRepository;
import es.deusto.sd.strava.dao.RetoRepository;
import es.deusto.sd.strava.dao.UsuarioRepository;
import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.dto.EntrenamientoUsuarioDTO;
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
	private final RetoRepository retoRepository;
	private final UsuarioRepository usuarioRepository;
	private final EntrenamientoRepository entrenamientoRepository;
	
	public StravaController(AuthService authService, StravaService stravaService, RetoRepository retoRepository, UsuarioRepository usuarioRepository, EntrenamientoRepository entrenamientoRepository) {
		this.authService = authService;
		this.stravaService = stravaService;
		this.retoRepository = retoRepository;
		this.usuarioRepository = usuarioRepository;
		this.entrenamientoRepository = entrenamientoRepository;
	}
	
	// user trainings endpoint
	@Operation(summary = "Get user trainings", description = "Allows a user to get his trainings.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: User trainings retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@GetMapping("users/{userID}/trainings")
	public ResponseEntity<List<EntrenamientoUsuarioDTO>> getTrainings(
		@Parameter(name = "userToken", description = "The token of a logged user", required = true, example = "192ee4daf90") 
		@RequestParam("userToken") String userToken,
		@Parameter(name = "userID", description = "The id of a registered user", required = true, example = "1")
		@PathVariable("userID") int userID,
		@Parameter(name = "FechaInicio", description = "The start date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaInicio") Long fechaInicio, 
		@Parameter(name = "FechaFin", description = "The end date to filter the trainings", required = false)
		@RequestParam(required = false, name = "FechaFin") Long fechaFin){
		Usuario u = usuarioRepository.findById(userID).get();
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		
		if (fechaInicio != null || fechaFin != null) {
			//List<Entrenamiento> entrenamientos = stravaService.filtrarEntrenamientos(u.getEntrenamientos(), fechaInicio, fechaFin);
			List<Entrenamiento> entrenamientos = entrenamientoRepository.filtrarEntrenamientosPorUsuario(userID, fechaInicio, fechaFin);
			return new ResponseEntity<>(parseEntrenamientoUsuarioDTO(entrenamientos), HttpStatus.OK);
			
		}
		return new ResponseEntity<>(parseEntrenamientoUsuarioDTO(u.getEntrenamientos()), HttpStatus.OK);
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
		Usuario u = usuarioRepository.findById(userID).get();
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		u.addEntrenamiento(parseEntrenamientoDTO(training, u));
//		usuarioRepository.save(u);
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
		retoRepository.save(reto);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// get all challenges endpoint
	@Operation(summary = "Get all challenges", description = "Allows a registered user to view all the available challenges.", responses = {
			@ApiResponse(responseCode = "200", description = "Success: challenges retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"), })
	
	@GetMapping("challenges")
	public ResponseEntity<List<Reto>> getChallenges(
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
			return new ResponseEntity<>(retoRepository.filtrarRetos(fechaInicio, fechaFin, deporte), HttpStatus.OK);			
		}
		return new ResponseEntity<>(retoRepository.findTop5ByOrderByIdAsc(), HttpStatus.OK);
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
		Usuario u = usuarioRepository.findById(userID).get();
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		return new ResponseEntity<>(stravaService.calcularPorcentajeReto(u.getRetosAceptados(), u.getEntrenamientos()), HttpStatus.OK);
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
		Usuario u = usuarioRepository.findById(userID).get();
		if (!authService.isValidTokenWithUser(userToken, u)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);			
		}
		Reto r = retoRepository.findById(idReto).get();
		u.addRetosAceptados(r);
		usuarioRepository.save(u);
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
	
	public List<EntrenamientoUsuarioDTO> parseEntrenamientoUsuarioDTO(List<Entrenamiento> entrenamientos){
		List<EntrenamientoUsuarioDTO> lista = new ArrayList<>();
        for (Entrenamiento entrenamiento : entrenamientos) {
            lista.add(new EntrenamientoUsuarioDTO(entrenamiento.getTitulo(), entrenamiento.getDeporte(), entrenamiento.getDistancia(), entrenamiento.getFechaHora(), entrenamiento.getDuracion(), entrenamiento.getUsuario().getId()));
        }
        return lista;
	}
}

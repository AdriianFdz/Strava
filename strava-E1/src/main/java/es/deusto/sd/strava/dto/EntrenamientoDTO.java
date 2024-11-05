package es.deusto.sd.strava.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import es.deusto.sd.strava.entity.TipoDeporte;
import jakarta.validation.constraints.NotNull;

public class EntrenamientoDTO {
	@NotNull(message = "El token del usuario es obligatorio")
	private String userToken;
	@NotNull(message = "El título es obligatorio")
	private String titulo;
	@NotNull(message = "El deporte es obligatorio")
	private TipoDeporte deporte;
	@NotNull(message = "La distancia es obligatoria")
	private double distancia;
	@NotNull(message = "La fecha de inicio es obligatoria")
	private LocalDate fechaInicio;
	@NotNull(message = "La hora de inicio es obligatoria")
	private LocalTime horaInicio;
	@NotNull(message = "La duración es obligatoria")
	private int duracion;
	
	// Constructor sin argumentos
	public EntrenamientoDTO() {}
	
	// Constructor con argumentos
	public EntrenamientoDTO(String userToken, String titulo, TipoDeporte deporte, double distancia, LocalDate fechaInicio,
			LocalTime horaInicio, int duracion) {
		this.userToken = userToken;
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.duracion = duracion;
	}

	// Getters y setters
	public String getUserToken() {
		return userToken;
	}
	
	public void setUserToken(String token) {
		this.userToken = token;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoDeporte getDeporte() {
		return deporte;
	}

	public void setDeporte(TipoDeporte deporte) {
		this.deporte = deporte;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
		
}

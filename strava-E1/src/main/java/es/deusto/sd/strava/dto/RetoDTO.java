package es.deusto.sd.strava.dto;

import java.time.LocalDate;
import java.util.Date;

import es.deusto.sd.strava.entity.TipoDeporte;
import jakarta.validation.constraints.NotNull;

public class RetoDTO {
	@NotNull(message = "El token del usuario es obligatorio")
	private String userToken;
	@NotNull(message = "El nombre es obligatorio")
	private String nombre;
	@NotNull(message = "La fecha de inicio es obligatoria")
	private LocalDate fechaInicio;
	@NotNull(message = "La fecha de fin es obligatoria")
	private LocalDate fechaFin;
	@NotNull(message = "La distancia es obligatoria")
	private double distancia;
	@NotNull(message = "El tiempo objetivo es obligatorio")
	private int tiempoObjetivo;
	@NotNull(message = "El deporte es obligatorio")
	private TipoDeporte deporte;
	
	// Constructor sin argumentos
	public RetoDTO() {}
	
	// Constructor con argumentos
	public RetoDTO(String userToken, String nombre, LocalDate fechaInicio, LocalDate fechaFin, double distancia, 
			int tiempoObjetivo, TipoDeporte deporte) {
		super();
		this.userToken = userToken;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.distancia = distancia;
		this.tiempoObjetivo = tiempoObjetivo;
		this.deporte = deporte;
	}

	
	// Getters y setters
	
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getTiempoObjetivo() {
		return tiempoObjetivo;
	}

	public void setTiempoObjetivo(int tiempoObjetivo) {
		this.tiempoObjetivo = tiempoObjetivo;
	}

	public TipoDeporte getDeporte() {
		return deporte;
	}

	public void setDeporte(TipoDeporte deporte) {
		this.deporte = deporte;
	}
	
	
}

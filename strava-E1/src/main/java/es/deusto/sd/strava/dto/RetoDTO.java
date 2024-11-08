package es.deusto.sd.strava.dto;

import java.time.LocalDate;

import es.deusto.sd.strava.entity.TipoDeporte;
import es.deusto.sd.strava.entity.TipoObjetivo;
import jakarta.validation.constraints.NotNull;

public class RetoDTO {
	@NotNull(message = "El nombre es obligatorio")
	private String nombre;
	@NotNull(message = "La fecha de inicio es obligatoria")
	private LocalDate fechaInicio;
	@NotNull(message = "La fecha de fin es obligatoria")
	private LocalDate fechaFin;
	@NotNull(message = "El objetivo es obligatorio")
	private double objetivo;
	@NotNull(message = "El tipo de objetivo es obligatorio")
	private TipoObjetivo tipoObjetivo;
	@NotNull(message = "El deporte es obligatorio")
	private TipoDeporte deporte;
	
	// Constructor sin argumentos
	public RetoDTO() {}
	
	// Constructor con argumentos
	public RetoDTO(String nombre, LocalDate fechaInicio, LocalDate fechaFin, double objetivo, TipoObjetivo tipoObjetivo, TipoDeporte deporte) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.objetivo = objetivo;
		this.tipoObjetivo = tipoObjetivo;
		this.deporte = deporte;
	}

	
	// Getters y setters
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

	public double getObjetivo() {
		return objetivo;
	}
	
	public void setObjetivo(double objetivo) {
		this.objetivo = objetivo;
	}
	
	public TipoObjetivo getTipoObjetivo() {
		return tipoObjetivo;
	}
	
	public void setTipoObjetivo(TipoObjetivo tipoObjetivo) {
		this.tipoObjetivo = tipoObjetivo;
	}

	public TipoDeporte getDeporte() {
		return deporte;
	}

	public void setDeporte(TipoDeporte deporte) {
		this.deporte = deporte;
	}
	
	
}

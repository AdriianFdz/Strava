package es.deusto.sd.strava.dto;

import es.deusto.sd.strava.entity.TipoDeporte;
import jakarta.validation.constraints.NotNull;

public class EntrenamientoDTO {
	@NotNull(message = "El título es obligatorio")
	private String titulo;
	@NotNull(message = "El deporte es obligatorio")
	private TipoDeporte deporte;
	@NotNull(message = "La distancia es obligatoria")
	private double distancia;
	@NotNull(message = "El timestamp de la fecha y hora de inicio es obligatoria")
    private long fechaHora;
	@NotNull(message = "La duración es obligatoria")
	private int duracion;
	
	// Constructor sin argumentos
	public EntrenamientoDTO() {}
	
	// Constructor con argumentos
	public EntrenamientoDTO(String titulo, TipoDeporte deporte, double distancia, long fechaHora, int duracion) {
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fechaHora = fechaHora;
		this.duracion = duracion;
	}

	// Getters y setters
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
	
	public long getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(long fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
		
}

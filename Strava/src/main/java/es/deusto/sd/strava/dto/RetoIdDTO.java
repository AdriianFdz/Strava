package es.deusto.sd.strava.dto;

import es.deusto.sd.strava.entity.TipoDeporte;
import es.deusto.sd.strava.entity.TipoObjetivo;
import jakarta.validation.constraints.NotNull;

public class RetoIdDTO {
	@NotNull(message = "El id es obligatorio")
	private int id;
	@NotNull(message = "El nombre es obligatorio")
	private String nombre;
	@NotNull(message = "La fecha de inicio es obligatoria")
	private long fechaInicio;
	@NotNull(message = "La fecha de fin es obligatoria")
	private long fechaFin;
	@NotNull(message = "El objetivo es obligatorio")
	private double objetivo;
	@NotNull(message = "El tipo de objetivo es obligatorio")
	private TipoObjetivo tipoObjetivo;
	@NotNull(message = "El deporte es obligatorio")
	private TipoDeporte deporte;
	
	// Constructor sin argumentos
	public RetoIdDTO() {}
	
	// Constructor con argumentos
	public RetoIdDTO(int id, String nombre, long fechaInicio, long fechaFin, double objetivo, TipoObjetivo tipoObjetivo, TipoDeporte deporte) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.objetivo = objetivo;
		this.tipoObjetivo = tipoObjetivo;
		this.deporte = deporte;
	}

	
	// Getters y setters
	public int getId() {
        return id;
    }
	
	public void setId(int id) {
        this.id = id;
    }	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public long getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(long fechaFin) {
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

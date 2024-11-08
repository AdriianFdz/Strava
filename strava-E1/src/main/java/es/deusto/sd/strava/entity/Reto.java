package es.deusto.sd.strava.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reto {
	private int id;
	private String nombre;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private double objetivo;
	private TipoObjetivo tipoObjetivo;
	private TipoDeporte deporte;
	
	private List<Usuario> participantes = new ArrayList<>();
	
	// Constructor without parameters
	public Reto() { }

	// Constructor with parameters
	public Reto(int id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, double objetivo, TipoObjetivo tipoObjetivo,
			TipoDeporte deporte) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.objetivo = objetivo;
		this.tipoObjetivo = tipoObjetivo;
		this.deporte = deporte;
	}

	// Getters and Setters
	public int getId() {
		return id;
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

	
	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void addParticipantes(Usuario participante) {
		this.participantes.add(participante);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, fechaFin, fechaInicio, id, nombre, objetivo, participantes, tipoObjetivo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reto other = (Reto) obj;
		return deporte == other.deporte && Objects.equals(fechaFin, other.fechaFin)
				&& Objects.equals(fechaInicio, other.fechaInicio) && id == other.id
				&& Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(objetivo) == Double.doubleToLongBits(other.objetivo)
				&& Objects.equals(participantes, other.participantes) && tipoObjetivo == other.tipoObjetivo;
	}
}

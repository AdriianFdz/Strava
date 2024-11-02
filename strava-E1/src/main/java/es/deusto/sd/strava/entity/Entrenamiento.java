package es.deusto.sd.strava.entity;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class Entrenamiento {
	private String titulo;
	private TipoDeporte deporte;
	private double distancia;
	private Date fechaInicio;
	private LocalTime horaInicio;
	private int duracion;
	
	// Constructor without parameters
	public Entrenamiento() { }
	
	// Constructor with parameters
	public Entrenamiento(String titulo, TipoDeporte deporte, double distancia, Date fechaInicio,
			LocalTime horaInicio, int duracion) {
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fechaInicio = fechaInicio;
		this.horaInicio = horaInicio;
		this.duracion = duracion;
	}

	// Getters and Setters

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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
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

	// HashCode and Equals
	
	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, duracion, fechaInicio, horaInicio, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrenamiento other = (Entrenamiento) obj;
		return Objects.equals(deporte, other.deporte)
				&& Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& duracion == other.duracion && Objects.equals(fechaInicio, other.fechaInicio)
				&& Objects.equals(horaInicio, other.horaInicio) && Objects.equals(titulo, other.titulo);
	}	
	
	
}

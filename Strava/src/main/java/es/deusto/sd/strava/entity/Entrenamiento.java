package es.deusto.sd.strava.entity;

import java.util.Objects;

public class Entrenamiento {
	private String titulo;
	private TipoDeporte deporte;
	private double distancia;
	private long fechaHora;
	private int duracion;
	
	// Constructor without parameters
	public Entrenamiento() { }
	
	// Constructor with parameters
	public Entrenamiento(String titulo, TipoDeporte deporte, double distancia, long fechaHora, int duracion) {
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fechaHora = fechaHora;
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

	// HashCode and Equals
	
	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, duracion, fechaHora, titulo);
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
		return deporte == other.deporte
				&& Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& duracion == other.duracion && fechaHora == other.fechaHora && Objects.equals(titulo, other.titulo);
	}	
	
	
}

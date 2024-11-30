package es.deusto.sd.strava.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Entrenamiento")
public class Entrenamiento {
	@Column(nullable = false)
	private String titulo;
	
	@Enumerated(EnumType.STRING)
	private TipoDeporte deporte;
	
	@Column(nullable = false)
	private double distancia;
	
	@Id
	@Column(nullable = false)
	private long fechaHora;
	
	@Column(nullable = false)
	private int duracion;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	// Constructor without parameters
	public Entrenamiento() { }
	
	// Constructor with parameters
	public Entrenamiento(String titulo, TipoDeporte deporte, double distancia, long fechaHora, int duracion, Usuario usuario) {
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fechaHora = fechaHora;
		this.duracion = duracion;
		this.usuario = usuario;
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

	public Usuario getUsuario() {
		return usuario;
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, duracion, fechaHora, titulo, usuario);
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
				&& duracion == other.duracion && fechaHora == other.fechaHora && Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuario, other.usuario);
	}	
}

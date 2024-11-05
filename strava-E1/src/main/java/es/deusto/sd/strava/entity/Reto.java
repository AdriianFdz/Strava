package es.deusto.sd.strava.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Reto {
	private String nombre;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private double distancia;
	private int tiempoObjetivo;
	private TipoDeporte deporte;
	
	private List<Usuario> participantes = new ArrayList<>();
	
	// Constructor without parameters
	public Reto() { }

	// Constructor with parameters
	public Reto(String nombre, LocalDate fechaInicio, LocalDate fechaFin, double distancia, int tiempoObjetivo,
			TipoDeporte deporte) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.distancia = distancia;
		this.tiempoObjetivo = tiempoObjetivo;
		this.deporte = deporte;
	}

	// Getters and Setters
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

	
	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void addParticipantes(Usuario participante) {
		this.participantes.add(participante);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deporte, distancia, fechaFin, fechaInicio, nombre, participantes, tiempoObjetivo);
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
		return deporte == other.deporte
				&& Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaFin, other.fechaFin) && Objects.equals(fechaInicio, other.fechaInicio)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(participantes, other.participantes)
				&& tiempoObjetivo == other.tiempoObjetivo;
	}
}

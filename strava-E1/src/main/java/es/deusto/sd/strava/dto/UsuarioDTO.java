package es.deusto.sd.strava.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO {
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    // Campos opcionales
    private double peso;
    private double altura;
    private int frecuenciaCardiacaMax;
    private int frecuenciaCardiacaReposo;

    // Constructor sin argumentos
	public UsuarioDTO() {}
    
    //Constructor con argumentos
	public UsuarioDTO(String email, String nombre, LocalDate fechaNacimiento, Double peso, Integer altura,
			int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo) {
		this.email = email;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.peso = peso;
		this.altura = altura;
		this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
		this.frecuenciaCardiacaReposo = frecuenciaCardiacaReposo;
	}    
	
    // Getters y setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public int getFrecuenciaCardiacaMax() {
		return frecuenciaCardiacaMax;
	}
	public void setFrecuenciaCardiacaMax(int frecuenciaCardiacaMaxima) {
		this.frecuenciaCardiacaMax = frecuenciaCardiacaMaxima;
	}
	public int getFrecuenciaCardiacaReposo() {
		return frecuenciaCardiacaReposo;
	}
	public void setFrecuenciaCardiacaReposo(int frecuenciaCardiacaReposo) {
		this.frecuenciaCardiacaReposo = frecuenciaCardiacaReposo;
	}

    
}

package es.deusto.sd.strava.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;

@Service
public class StravaService {
	public Entrenamiento parseEntrenamientoDTO(EntrenamientoDTO dto) {
		return new Entrenamiento(
            dto.getTitulo(),
            dto.getDeporte(),
            dto.getDistancia(),
            dto.getFechaInicio(),
            dto.getHoraInicio(),
            dto.getDuracion()
        );
	}
	
	public List<Entrenamiento> filtrarEntrenamientos(List<Entrenamiento> entrenamientos, LocalDate fechaInicio, LocalDate fechaFin) {
	    List<Entrenamiento> resultado = new ArrayList<>();

	    for (Entrenamiento entrenamiento : entrenamientos) {
	        if (fechaInicio != null && fechaFin != null) {
	            if ((!entrenamiento.getFechaInicio().isBefore(fechaInicio)) && (!entrenamiento.getFechaInicio().isAfter(fechaFin))) {
	                resultado.add(entrenamiento);
	            }
	        }
	        else if (fechaInicio != null) {
	            if (!entrenamiento.getFechaInicio().isBefore(fechaInicio)) {
	                resultado.add(entrenamiento);
	            }
	        }
	        else if (fechaFin != null) {
	            if (!entrenamiento.getFechaInicio().isAfter(fechaFin)) {
	                resultado.add(entrenamiento);
	            }
	        }
	    }

	    return resultado;
	}


}

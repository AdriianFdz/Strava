package es.deusto.sd.strava.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
	
	private List<Reto> listaRetos = new ArrayList<>();
	
	public List<Reto> getListaRetos() {
		return listaRetos;
	}

	public Reto parseRetoDTO(RetoDTO dto) {
		return new Reto(
				dto.getNombre(),
				dto.getFechaInicio(),
				dto.getFechaFin(),
				dto.getDistancia(),
				dto.getTiempoObjetivo(),
				dto.getDeporte()
			);
	}
	
	public List<Reto> filtrarRetos(List<Reto> retos, LocalDate fechaInicio, LocalDate fechaFin, TipoDeporte deporte) {
	    List<Reto> resultado = new ArrayList<>();

	    for (Reto reto : retos) {
	        if (fechaInicio != null && fechaFin != null && deporte != null) {
	            if ((!reto.getFechaInicio().isBefore(fechaInicio)) && (!reto.getFechaInicio().isAfter(fechaFin)) && reto.getDeporte().equals(deporte)) {
	                resultado.add(reto);
	            }
	        }
	        else if (fechaInicio != null && deporte != null) {
	            if (!reto.getFechaInicio().isBefore(fechaInicio) && reto.getDeporte().equals(deporte)) {
	                resultado.add(reto);
	            }
	        }
	        else if (fechaFin != null &&  deporte != null) {
	            if (!reto.getFechaInicio().isAfter(fechaFin) && reto.getDeporte().equals(deporte)) {
	                resultado.add(reto);
	            }
	        }
	    	
	        else if (fechaInicio != null && fechaFin != null) {
	            if ((!reto.getFechaInicio().isBefore(fechaInicio)) && (!reto.getFechaInicio().isAfter(fechaFin))) {
	                resultado.add(reto);
	            }
	        }
	        else if (fechaInicio != null) {
	            if (!reto.getFechaInicio().isBefore(fechaInicio)) {
	                resultado.add(reto);
	            }
	        }
	        else if (fechaFin != null) {
	            if (!reto.getFechaInicio().isAfter(fechaFin)) {
	                resultado.add(reto);
	            }
	        } 
	        else if (deporte != null) {
            	if (reto.getDeporte().equals(deporte)) {
            		resultado.add(reto);
            	}
	    }
	        
	    }
	    return resultado;
	}
	
    public Map<Reto, Double> calcularPorcentajeReto(List<Reto> retos) {
        Map<Reto, Double> porcentajeReto = new HashMap<>();

        for (Reto reto : retos) {
            double porcentaje = reto.getTiempoObjetivo() / reto.getDistancia();
            porcentajeReto.put(reto, porcentaje);
        }

        return porcentajeReto;
    }

	
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

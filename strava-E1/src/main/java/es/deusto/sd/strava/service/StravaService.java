package es.deusto.sd.strava.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;

@Service
public class StravaService {
	
	private Map<Integer, Reto> mapaRetos = new HashMap<>();
	
	public Map<Integer, Reto> getMapaRetos() {
		return mapaRetos;
	}
	
	public List<Reto> filtrarRetos(Map<Integer, Reto> retos, LocalDate fechaInicio, LocalDate fechaFin, TipoDeporte deporte) {
	    List<Reto> resultado = new ArrayList<>();

	    for (Reto reto : retos.values()) {
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
	
    public Map<Integer, Double> calcularPorcentajeReto(List<Reto> retos, List<Entrenamiento> entrenamientos) {
        Map<Integer, Double> porcentajeReto = new HashMap<>();       
        
        for (Reto reto : retos) {
        	double resultado = 0;
        	for (Entrenamiento entrenamiento : entrenamientos) {
        		if (reto.getDeporte() != entrenamiento.getDeporte()) {
					continue;
				}
				if (!reto.getFechaInicio().isAfter(timeStampToLocalDate(entrenamiento.getFechaHora())) && !reto.getFechaFin().isBefore(timeStampToLocalDate(entrenamiento.getFechaHora()))) {
					switch(reto.getTipoObjetivo()) {
                        case DISTANCIA:
                            resultado += entrenamiento.getDistancia();
                            break;
                        case TIEMPO:
                            resultado += entrenamiento.getDuracion();
                            break;
                    }
				}
        	}
            double porcentaje = (resultado / reto.getObjetivo())*100;
            porcentajeReto.put(reto.getId(), porcentaje);
        }

        return porcentajeReto;
    }
	
	public List<Entrenamiento> filtrarEntrenamientos(List<Entrenamiento> entrenamientos, LocalDate fechaInicio, LocalDate fechaFin) {
	    List<Entrenamiento> resultado = new ArrayList<>();

	    for (Entrenamiento entrenamiento : entrenamientos) {
	        if (fechaInicio != null && fechaFin != null) {
	            if ((!timeStampToLocalDate(entrenamiento.getFechaHora()).isBefore(fechaInicio)) && (!timeStampToLocalDate(entrenamiento.getFechaHora()).isAfter(fechaFin))) {
	                resultado.add(entrenamiento);
	            }
	        }
	        else if (fechaInicio != null) {
	            if (!timeStampToLocalDate(entrenamiento.getFechaHora()).isBefore(fechaInicio)) {
	                resultado.add(entrenamiento);
	            }
	        }
	        else if (fechaFin != null) {
	            if (!timeStampToLocalDate(entrenamiento.getFechaHora()).isAfter(fechaFin)) {
	                resultado.add(entrenamiento);
	            }
	        }
	    }

	    return resultado;
	}
	
	public LocalDate timeStampToLocalDate(long timestamp) {
		return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}

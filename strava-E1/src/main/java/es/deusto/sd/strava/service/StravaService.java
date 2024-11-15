package es.deusto.sd.strava.service;

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
	
	public List<Reto> filtrarRetos(Map<Integer, Reto> retos, Long fechaInicio, Long fechaFin, TipoDeporte deporte) {
	    List<Reto> resultado = new ArrayList<>();
	    for (Reto reto : retos.values()) {
	        boolean cumpleFechaInicio = (fechaInicio == null || reto.getFechaInicio() >= fechaInicio);
	        boolean cumpleFechaFin = (fechaFin == null || reto.getFechaFin() <= fechaFin);
	        boolean cumpleDeporte = (deporte == null || reto.getDeporte().equals(deporte));
	        
	        if (cumpleFechaInicio && cumpleFechaFin && cumpleDeporte) {
	            resultado.add(reto);
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
				if (reto.getFechaInicio() <= entrenamiento.getFechaHora() && reto.getFechaFin() >= entrenamiento.getFechaHora()){
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
	
	public List<Entrenamiento> filtrarEntrenamientos(List<Entrenamiento> entrenamientos, Long fechaInicio, Long fechaFin) {
	    List<Entrenamiento> resultado = new ArrayList<>();

	    for (Entrenamiento entrenamiento : entrenamientos) {
	        if (fechaInicio != null && fechaFin != null) {
	        	if (entrenamiento.getFechaHora() >= fechaInicio && entrenamiento.getFechaHora() <= fechaFin) {
            		resultado.add(entrenamiento);
	        	}
	        }
	        else if (fechaInicio != null) {
	        	if (entrenamiento.getFechaHora() >= fechaInicio) {
            		resultado.add(entrenamiento);
            	}
					
	        }
	        else if (fechaFin != null) {
				if (entrenamiento.getFechaHora() <= fechaFin) {
					resultado.add(entrenamiento);
				}
	        }
	    }

	    return resultado;
	}
}

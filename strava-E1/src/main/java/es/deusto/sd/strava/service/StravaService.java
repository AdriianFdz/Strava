package es.deusto.sd.strava.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dto.AceptarRetoDTO;
import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
	
	private Map<Integer, Reto> mapaRetos = new HashMap<>();
    private final AtomicInteger idRetoGenerator = new AtomicInteger(0);
	
	public Map<Integer, Reto> getMapaRetos() {
		return mapaRetos;
	}

	public Reto parseRetoDTO(RetoDTO dto) {
		return new Reto(
				idRetoGenerator.incrementAndGet(),
				dto.getNombre(),
				dto.getFechaInicio(),
				dto.getFechaFin(),
				dto.getDistancia(),
				dto.getTiempoObjetivo(),
				dto.getDeporte()
			);
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
	
    public Map<Integer, Double> calcularPorcentajeReto(List<Reto> retos) {
        Map<Integer, Double> porcentajeReto = new HashMap<>();

        for (Reto reto : retos) {
            double porcentaje = reto.getTiempoObjetivo() / reto.getDistancia();
            porcentajeReto.put(reto.getId(), porcentaje);
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

	public Reto parseAceptarRetoDTO(AceptarRetoDTO aceptarReto, Usuario participante) {
		Reto reto = mapaRetos.get(aceptarReto.getIdReto());
		reto.addParticipantes(participante);
		return reto;
	}

}

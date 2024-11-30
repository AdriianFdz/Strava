package es.deusto.sd.strava.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dao.EntrenamientoRepository;
import es.deusto.sd.strava.dao.RetoRepository;
import es.deusto.sd.strava.dao.UsuarioRepository;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
	
	private final RetoRepository retoRepository;
	private final UsuarioRepository usuarioRepository;
	private final EntrenamientoRepository entrenamientoRepository;
	
	public StravaService(RetoRepository retoRepository, UsuarioRepository usuarioRepository,
			EntrenamientoRepository entrenamientoRepository) {
		this.retoRepository = retoRepository;
		this.usuarioRepository = usuarioRepository;
		this.entrenamientoRepository = entrenamientoRepository;
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).get();
	}
	
	public Reto getRetoById(int id) {
		return retoRepository.findById(id).get();
	}
	
	public List<Entrenamiento> filtrarEntrenamientosPorUsuario(int id, long fechaInicio, long fechaFin) {
		return entrenamientoRepository.filtrarEntrenamientosPorUsuario(id, fechaInicio, fechaFin);
	}
	
	public void anadirEntrenamiento(Usuario u, Entrenamiento e) {
		u.addEntrenamiento(e);
		usuarioRepository.save(u);
	}
	
	public void anadirReto(Reto r) {
		retoRepository.save(r);
	}
	
	public List<Reto> filtrarRetos(long fechaInicio, long fechaFin, TipoDeporte deporte) {
		return retoRepository.filtrarRetos(fechaInicio, fechaFin, deporte);
	}
	
	public List<Reto> getTop5Retos() {
		return retoRepository.findTop5ByOrderByIdAsc();
	}
	
	public void aceptarReto(Usuario u, Reto r) {
		u.addRetosAceptados(r);
		usuarioRepository.save(u);
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

}

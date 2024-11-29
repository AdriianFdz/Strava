package es.deusto.sd.strava.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.TipoDeporte;

@Repository
public interface RetoRepository extends JpaRepository<Reto, Integer> {
    @Query("SELECT r FROM Reto r WHERE " +
            "( :fechaInicio IS NULL OR r.fechaInicio >= :fechaInicio ) AND " +
            "( :fechaFin IS NULL OR r.fechaFin <= :fechaFin ) AND " +
            "( :deporte IS NULL OR r.deporte = :deporte )")
    List<Reto> filtrarRetos(
    	    @Param("fechaInicio") Long fechaInicio, 
    	    @Param("fechaFin") Long fechaFin, 
    	    @Param("deporte") TipoDeporte deporte);
    List<Reto> findTop5ByOrderByIdAsc();

}

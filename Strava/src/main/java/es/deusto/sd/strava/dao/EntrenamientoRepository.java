package es.deusto.sd.strava.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.deusto.sd.strava.entity.Entrenamiento;

@Repository
public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Integer>  {
    @Query("SELECT e FROM Entrenamiento e WHERE e.usuario.id = :usuarioId " +
            "AND (:fechaInicio IS NULL OR e.fechaHora >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR e.fechaHora <= :fechaFin)")
     List<Entrenamiento> filtrarEntrenamientosPorUsuario(@Param("usuarioId") Integer usuarioId, 
                                                         @Param("fechaInicio") Long fechaInicio, 
                                                         @Param("fechaFin") Long fechaFin);
}

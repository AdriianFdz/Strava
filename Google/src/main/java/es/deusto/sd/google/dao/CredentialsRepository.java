package es.deusto.sd.google.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.deusto.sd.google.entity.Credentials;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    Optional<Credentials> findByEmail(String email);
}

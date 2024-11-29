package es.deusto.sd.strava.external;

import org.springframework.http.ResponseEntity;

import es.deusto.sd.strava.entity.ServidorAuth;

public interface ILoginServiceGateway {
	public ResponseEntity<String> login(String email, String password);
	public ServidorAuth getServidorAuth();
}

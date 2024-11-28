package es.deusto.sd.strava.service;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.external.ILoginServiceGateway;

@Service
public class LoginService {
	
	private final ILoginServiceGateway loginServiceGateway;
	public LoginService(ILoginServiceGateway loginServiceGateway) {
		this.loginServiceGateway = loginServiceGateway;
	}
	
	public boolean login(String email, String password) {
		return loginServiceGateway.login(email, password);
	}
}

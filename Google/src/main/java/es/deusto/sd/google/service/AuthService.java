package es.deusto.sd.google.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.deusto.sd.google.dao.CredentialsRepository;
import es.deusto.sd.google.entity.Credentials;

@Service
public class AuthService {
	
	@Autowired
    private CredentialsRepository credentialsRepository;

	public boolean validateEmail(String email) {
		Optional<Credentials> storedEmail = credentialsRepository.findByEmail(email);

		return storedEmail.isPresent();
	}
    
    public boolean validateCredentials(String email, String Password) {
        Optional<Credentials> storedEmail = credentialsRepository.findByEmail(email);

        if (storedEmail.isPresent()) {
            String storedPassword = storedEmail.get().getPassword();

            return Password.equals(storedPassword);
        }

        return false;
    }
}

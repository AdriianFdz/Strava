package es.deusto.ingenieria.sd;

import java.util.HashMap;
import java.util.Map;

public class MetaAuth {
    private Map<String, String> users;

    public MetaAuth() {
        this.users = new HashMap<>();
 
        this.users.put("user1@example.com", "password123");
        this.users.put("user2@example.com", "securepassword");
    }

    public String verifyAndAuthenticate(String email, String password) {
        if (!users.containsKey(email)) {
            return "ERROR#Email not registered";
        }

        String storedPassword = users.get(email);
        if (storedPassword.equals(password)) {
            return "OK#Authentication successful";
        } else {
            return "ERROR#Invalid password";
        }
    }
}
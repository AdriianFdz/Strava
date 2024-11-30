package es.deusto.sd.strava.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.deusto.sd.strava.entity.ServidorAuth;

public class MetaServiceGateway implements ILoginServiceGateway{

	private final static int SERVER_PORT = 8082;
	
	private String serverIP = "localhost";
	private static String DELIMITER = "#";
	
	@Override
	public ResponseEntity<String> login(String email, String password) {
		try (Socket socket = new Socket(serverIP, SERVER_PORT);
				// Streams to send and receive information are created from the Socket
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
			
			out.writeUTF(email + DELIMITER + password);
			String result = in.readUTF();
			StringTokenizer tokenizer = new StringTokenizer(result, DELIMITER);
			String msgCode = tokenizer.nextToken();
			String message = tokenizer.nextToken();

			if (msgCode.equals("OK")) {
		        return new ResponseEntity<>(HttpStatus.OK);
			}
			
			if (msgCode.equals("ERROR")) {
				if (message.equals("Email not registered")) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				if (message.equals("Invalid password")) {
					return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
				}					
			}			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (UnknownHostException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@Override
	public ServidorAuth getServidorAuth() {
		return ServidorAuth.META;
	}

}

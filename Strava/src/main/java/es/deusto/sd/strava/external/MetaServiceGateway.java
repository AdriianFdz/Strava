package es.deusto.sd.strava.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import es.deusto.sd.strava.entity.ServidorAuth;

public class MetaServiceGateway implements ILoginServiceGateway{

	private final static int SERVER_PORT = 8082;
	
	private String serverIP = "localhost";
	private static String DELIMITER = "#";
	
	@Override
	public boolean login(String email, String password) {
		try (Socket socket = new Socket(serverIP, SERVER_PORT);
				// Streams to send and receive information are created from the Socket
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
			
			out.writeUTF(email + DELIMITER + password);
			Boolean result = in.readBoolean();
			return result;
			
		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}	
	}

	@Override
	public ServidorAuth getServidorAuth() {
		return ServidorAuth.META;
	}

}

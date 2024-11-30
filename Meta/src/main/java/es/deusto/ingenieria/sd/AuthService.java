package es.deusto.ingenieria.sd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class AuthService extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket tcpSocket;

    private static String DELIMITER = "#";

    public AuthService(Socket socket) {
        try {
            this.tcpSocket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.err.println("# AuthService - TCPConnection IO error:" + e.getMessage());
        }
    }

    public void run() {
        try {
            String data = this.in.readUTF();            
            System.out.println("   - AuthService - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");                    
            
            data = this.authenticate(data);
                    
            this.out.writeUTF(data);                    
            System.out.println("   - AuthService - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");
        } catch (EOFException e) {
            System.err.println("   # AuthService - TCPConnection EOF error" + e.getMessage());
        } catch (IOException e) {
            System.err.println("   # AuthService - TCPConnection IO error:" + e.getMessage());
        } finally {
            try {
                tcpSocket.close();
            } catch (IOException e) {
                System.err.println("   # AuthService - TCPConnection IO error:" + e.getMessage());
            }
        }
    }
    
    public String authenticate(String msg) {
        String result = null;
        
        if (msg != null && !msg.trim().isEmpty()) {
            try {
                StringTokenizer tokenizer = new StringTokenizer(msg, DELIMITER);        
                String email = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                System.out.println("   - Starting authentication for email: " + email);

                MetaAuth auth = new MetaAuth();
                result = auth.verifyAndAuthenticate(email, password);
                System.out.println("   - Authentication result: " + result);
            } catch (Exception e) {
                System.err.println("   # AuthService - Authentication error:" + e.getMessage());
                result = "ERROR#Internal server error";
            }
        }
        
        return (result == null) ? "ERROR#Invalid request" : result;
    }
}
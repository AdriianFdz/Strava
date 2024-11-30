package es.deusto.ingenieria.sd;

import java.io.IOException;
import java.net.ServerSocket;

public class AuthServer {
    
    private static int numClients = 0;
    private static final int SERVER_PORT = 8082;

    public static void main(String args[]) {        
        try (ServerSocket tcpServerSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println(" - AuthServer: Waiting for connections at '" + tcpServerSocket.getInetAddress().getHostAddress() + ":" + tcpServerSocket.getLocalPort() + "' ...");
            while (true) {
                new AuthService(tcpServerSocket.accept());
                System.out.println(" - AuthServer: New client connection accepted. Client number: " + ++numClients);
            }
        } catch (IOException e) {
            System.err.println("# AuthServer: IO error:" + e.getMessage());
        }
    }
}
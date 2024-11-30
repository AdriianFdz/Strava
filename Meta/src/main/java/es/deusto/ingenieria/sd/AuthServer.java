package es.deusto.ingenieria.sd;

import java.io.IOException;
import java.net.ServerSocket;

public class AuthServer {
    
    private static int numClients = 0;

    public static void main(String args[]) {
        if (args.length < 1) {
            System.err.println(" # Usage: AuthServer [PORT]");
            System.exit(1);
        }
        
        int serverPort = Integer.parseInt(args[0]);

        try (ServerSocket tcpServerSocket = new ServerSocket(serverPort)) {
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
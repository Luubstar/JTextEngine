
import java.util.Scanner;

import TextEngine.ClientObject;
import TextEngine.NetworkHost;
import TextEngine.Debug.Debug;

public class TestServer extends NetworkHost {

    private static int port;
    int Timeout = 5000;
    boolean going = true;

    @Override
    public void onClientConnected(ClientObject client){
        try{
            SendToAll("[SERVER] Client has connected from " + client.clientSocket.getInetAddress().getHostAddress());
            System.out.println("[SERVER] Client has connected from " + client.clientSocket.getInetAddress().getHostAddress());
        }
        catch(Exception e){
            Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());
        }
    }

    @Override
    public void onClientDisconnected(ClientObject client){
        try{
            SendToAll("[SERVER] Client has disconnected from " + client.clientSocket.getInetAddress().getHostAddress());
            System.out.println("[SERVER] Client has disconnected from " + client.clientSocket.getInetAddress().getHostAddress());
        }
        catch(Exception e){
            Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());
        }
    }

    @Override
    public void onRecieveFromClient(ClientObject client, byte[] data) {
        try{
            SendToAll(data);
            System.out.println(new String(data,"UTF-8"));
        }
        catch(Exception e){
            Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());
        }
    }

    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduzca el puerto: ");
            port = scanner.nextInt();
            scanner.close();
            NetworkHost host = new NetworkHost();
            TestServer server = new TestServer();
            host.Start(port, server);
        }
        catch(Exception e){System.out.println(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}
    }
}

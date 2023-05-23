

import java.util.Scanner;

import TextEngine.ClientObject;
import TextEngine.NetworkHost;

public class TestServer extends NetworkHost {

    private static int port;
    int Timeout = 5000;
    boolean going = true;

    @Override
    public void onClientConnected(ClientObject client) throws Exception {
        SendToAll("[SERVER] Client has connected from " + client.clientSocket.getInetAddress().getHostAddress());
        System.out.println("[SERVER] Client has connected from " + client.clientSocket.getInetAddress().getHostAddress());
    }

    @Override
    public void onClientDisconnected(ClientObject client) throws Exception {
        SendToAll("[SERVER] Client has disconnected from " + client.clientSocket.getInetAddress().getHostAddress());
        System.out.println("[SERVER] Client has disconnected from " + client.clientSocket.getInetAddress().getHostAddress());
    }

    @Override
    public void onRecieveFromClient(ClientObject client, byte[] data) throws Exception {
        SendToAll(data);
        System.out.println(new String(data,"UTF-8"));
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

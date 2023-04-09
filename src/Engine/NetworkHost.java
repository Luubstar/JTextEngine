package Engine;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkHost{
    public  boolean going = true;
    public int Timeout = 15000;

    public List<ClientObject> Clients = new ArrayList<>();
    public ServerSocket serverSocket;

    public NetworkHost(){}

    public void Start(int port, NetworkHost host) throws Exception{
        serverSocket = new ServerSocket(port);

        while (going) {
            Socket clientSocket = serverSocket.accept();
            clientSocket.setSoTimeout(Timeout);
            ClientObject client = new ClientObject(clientSocket, host);
            client.start();
            host.Clients.add(client);
            host.onClientConnected(client);
        }
        serverSocket.close();
    }

    public void Stop(){this.going = false;}


    public void onClientConnected(ClientObject client) throws Exception{
        System.out.println("connected");
    }

    public void onClientDisconnected(ClientObject client) throws Exception{
        System.out.println("disconnected");
    }

    public void onRecieveFromClient(ClientObject client, byte[] data) throws Exception{
        System.out.println("data");
    }

    public void Send(ClientObject client, String data) throws IOException{
        client.salida.writeInt(data.getBytes().length);
        client.salida.write(data.getBytes());
    }

    public void Send(ClientObject client, byte[] data) throws IOException{
        client.salida.writeInt(data.length);
        client.salida.write(data);
    }

    public void SendToAll(String data) throws IOException,InterruptedException{

        for (ClientObject clientToSend : Clients){
            int i = 0;
            while(clientToSend.salida == null && i < 10){
                i++;
                Thread.sleep(10);
            }
            if(clientToSend.salida != null){
                clientToSend.salida.writeInt(data.getBytes().length);
                clientToSend.salida.write(data.getBytes());
            }
        }
    }

    public void SendToAll(byte[] data) throws IOException, InterruptedException{

        for (ClientObject clientToSend : Clients){
            int i = 0;
            while(clientToSend.salida == null && i < 10){
                i++;
                Thread.sleep(10);
            }
            
            if(clientToSend.salida != null){
                clientToSend.salida.writeInt(data.length);
                clientToSend.salida.write(data);
            }
        }
    }

    public String receiveString(ClientObject client) throws IOException{

        if (client.entrada.available() > 0){
            int length = client.entrada.readInt();
            if (length > 0) {
                byte[] mensajeByte = new byte[length];
                client.entrada.readFully(mensajeByte,0,length);
                String mensaje = new String(mensajeByte,"UTF-8");
                return mensaje;
            }
            return "";
        }
        else{return "";}
    }

    public byte[] receiveByte(ClientObject client) throws IOException{
        if (client.entrada.available() > 0){
            int length = client.entrada.readInt();
            if (length > 0) {
                byte[] mensajeByte = new byte[length];
                client.entrada.readFully(mensajeByte,0,length);
                return mensajeByte;
            }
            return new byte[0];
        }
        else{return new byte[0];}
    }

    public ClientObject[] GetClients(){return Clients.toArray(new ClientObject[Clients.size()]);}

    public void setTimeout(int Timeout){this.Timeout = Timeout;}


}

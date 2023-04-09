import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkHost{
    

    private int port = 5555;
    private boolean going = true;
    int Timeout = 15000;

    public List<ClientObject> Clients = new ArrayList<>();
    ServerSocket serverSocket;

    public NetworkHost(int port){this.port = port;}

    public NetworkHost(){}

    public void Start() throws Exception{
        serverSocket = new ServerSocket(port);
        Engine.print("Started on " + port);

        while (going) {
            Socket clientSocket = serverSocket.accept();
            clientSocket.setSoTimeout(Timeout);
            ClientObject client = new ClientObject(clientSocket, this);
            onClientConnected(client);
            Clients.add(client);
            client.start();
        }
        serverSocket.close();
    }

    public void Stop(){this.going = false;}


    public void onClientConnected(ClientObject client) throws Exception{
    }

    public void onClientDisconnected(ClientObject client) throws Exception{
    }

    public void onRecieveFromClient(ClientObject client, byte[] data) throws Exception{
    }

    public void Send(ClientObject client, String data) throws IOException{
        client.salida.writeInt(data.getBytes().length);
        client.salida.write(data.getBytes());
    }

    public void Send(ClientObject client, byte[] data) throws IOException{
        client.salida.writeInt(data.length);
        client.salida.write(data);
    }

    public void SendToAll(ClientObject client, String data) throws IOException{
        for (ClientObject clientToSend : Clients){
            clientToSend.salida.writeInt(data.getBytes().length);
            clientToSend.salida.write(data.getBytes());
        }
    }

    public void SendToAll(ClientObject client, byte[] data) throws IOException{
        for (ClientObject clientToSend : Clients){
            clientToSend.salida.writeInt(data.length);
            clientToSend.salida.write(data);
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

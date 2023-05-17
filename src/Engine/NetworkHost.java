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

    /**
     * Starts the server in the port
     * 
     * @param port
     * @param host -> Host to start
     * @throws Exception
     */
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

    /**
     * Stops the host
     */
    public void Stop(){this.going = false;}


    /**
     * Callback when client connects
     * 
     * @param client
     * @throws Exception
     */
    public void onClientConnected(ClientObject client) throws Exception{
        System.out.println("connected");
    }

    /**
    * Callback when a client disconnects
    * @param client
    * @throws Exception
    */
    public void onClientDisconnected(ClientObject client) throws Exception{
        System.out.println("disconnected");
    }

    /**
    * Callback when recieved data from a client
    * @param client
    * @throws Exception
    */
    public void onRecieveFromClient(ClientObject client, byte[] data) throws Exception{
        System.out.println("data");
    }

    /**
     * Sends data to client
     * @param client
     * @param data -> String
     * @throws IOException
     */
    public void Send(ClientObject client, String data) throws IOException{
        client.salida.writeInt(data.getBytes().length);
        client.salida.write(data.getBytes());
    }

    /**
     * Sends data to client
     * @param client
     * @param data -> byte[]
     * @throws IOException
     */
    public void Send(ClientObject client, byte[] data) throws IOException{
        client.salida.writeInt(data.length);
        client.salida.write(data);
    }

    /**
     * Sends data to all clients
     * @param data -> String
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Sends data to all clients
     * @param data -> byte[]
     * @throws IOException
     * @throws InterruptedException
     */
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

    /**
     * Gets a string from a client if it is available
     * @param client
     * @return
     * @throws IOException
     */
    public String ReceiveString(ClientObject client) throws IOException{

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

    /**
     * Gets a byte[] from a client if it is available
     * @param client
     * @return
     * @throws IOException
     */
    public byte[] ReceiveByte(ClientObject client) throws IOException{
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

    /**
     * Returns the array of connected clients
     * @return
     */
    public ClientObject[] GetClients(){return Clients.toArray(new ClientObject[Clients.size()]);}

    /**
     * Sets the timeout before kicking
     * @param Timeout
     */
    public void setTimeout(int Timeout){this.Timeout = Timeout;}


}

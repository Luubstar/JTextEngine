
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientObject extends Thread {
    public Socket clientSocket;
    public NetworkHost host;

    DataInputStream entrada;
    DataOutputStream salida;

    Charset charset = Charset.forName("UTF-8");
    
    private boolean connected = true;
    public ClientObject(Socket clientSocket, NetworkHost host) {
        this.clientSocket = clientSocket;
        this.host = host;
    }

    @Override
    public void run(){
        try {

            entrada = new DataInputStream(clientSocket.getInputStream());
            salida = new DataOutputStream(clientSocket.getOutputStream());

            int length = 1;

            while (length > 0 && connected) {
                System.out.println(entrada.available() + ":" + clientSocket.isConnected());
                if (entrada.available() > 0){
                    length = entrada.readInt();
                    byte[] mensajeByte = new byte[length];
                    entrada.readFully(mensajeByte,0,length);
                    host.onRecieveFromClient(this, mensajeByte);
                }
                else{
                    if (!clientSocket.isConnected()){Stop();}
                    Thread.sleep(10);
                }
            }
            entrada.close();
            salida.close();
            clientSocket.close();
            host.Clients.remove(this);
            host.onClientDisconnected(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Stop(){connected = false;}
}

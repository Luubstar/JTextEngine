package TextEngine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetworkClient {

    private String host;
    private int puerto;
    private Socket socket;
    DataInputStream entrada;
    DataOutputStream salida;
    int Timeout = 15000;

    /**
     * Creates a new NetworkClient with a host and port
     * @param host
     * @param puerto
     */
    public NetworkClient(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Connects with the host and port of the client
     */
    public void Connect() throws Exception {
        socket = new Socket(host, puerto);
        socket.setSoTimeout(Timeout);
        entrada = new DataInputStream(socket.getInputStream());
        salida = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Sends data to host
     * @param mensaje -> String
     * @throws Exception
     */
    public void Send(String mensaje) throws Exception {
        salida.writeInt(mensaje.getBytes().length);
        salida.write(mensaje.getBytes());
    }

    /**
     * Sends data to host
     * @param mensaje -> byte[]
     * @throws Exception
     */
    public void Send(byte[] mensaje) throws Exception {
        salida.writeInt(mensaje.length);
        salida.write(mensaje);
    }

    /**
     * Receives String from host if available
     * @return
     * @throws Exception
     */
    public String ReceiveString() throws Exception {
        if (entrada.available() > 0){
            int length = entrada.readInt();
            if (length > 0) {
                byte[] mensajeByte = new byte[length];
                entrada.readFully(mensajeByte,0,length);
                String mensaje = new String(mensajeByte,"UTF-8");
                return mensaje;
            }
            return "";
        }
        else{return "";}
    }

    /**
     * Receives byte[] from host if available
     * @return
     * @throws Exception
     */
    public byte[] ReceiveByte() throws Exception {
        if( entrada.available() > 0){
            int length = entrada.readInt();
            if (length > 0) {
                byte[] mensajeByte = new byte[length];
                entrada.readFully(mensajeByte,0,length);
                return mensajeByte;
            }
            return new byte[0];
        }
        else{return new byte[0];}
    }

    /**
     * Sets timeout before kicking
     * @param Timeout
     */
    public void setTimeout(int Timeout){this.Timeout = Timeout;}

    /**
     * Closes the connection
     * @throws Exception
     */
    public void Close() throws Exception {
        Send(new byte[0]);
        entrada.close();
        salida.close();
        socket.close();
    }

}
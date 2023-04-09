package Engine;
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

    public NetworkClient(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void Connect() throws Exception {
        socket = new Socket(host, puerto);
        socket.setSoTimeout(Timeout);
        entrada = new DataInputStream(socket.getInputStream());
        salida = new DataOutputStream(socket.getOutputStream());
    }

    public void Send(String mensaje) throws Exception {
        salida.writeInt(mensaje.getBytes().length);
        salida.write(mensaje.getBytes());
    }

    public void Send(byte[] mensaje) throws Exception {
        salida.writeInt(mensaje.length);
        salida.write(mensaje);
    }

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

    public void setTimeout(int Timeout){this.Timeout = Timeout;}

    public void Close() throws Exception {
        Send(new byte[0]);
        entrada.close();
        salida.close();
        socket.close();
    }

}
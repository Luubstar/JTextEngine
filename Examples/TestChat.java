

import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.NetworkClient;
import TextEngine.Debug.Debug;

public class TestChat extends Menu{

    private String name = "ERROR";
    private String mensajes = "";
    private String frame;
    private String futureMessage = "";
    private String IP = "127.0.0.1";
    private int port = 5555;
    private NetworkClient client;

    @Override
    public void Start() {
        try{
            
            IP = Keyboard.Scanner("Introduzca la dirección IP: ");
            port = Integer.parseInt(Keyboard.Scanner("Introduzca el puerto: "));
            name = Keyboard.Scanner("Introduzca su nombre de usuario: ");

            client = new NetworkClient(IP, port);
            client.Connect();
            client.Send("["+name+"] Se ha unido a la sala");
            
        }
        catch (Exception e){Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}

    }

    @Override
    public void Update() {
        try{

            String data = client.ReceiveString();
            if (data.length(
            ) > 0){
                mensajes += "\n" + data;
            }

            if (Keyboard.getKeyType() != null){
                if (Keyboard.IsLastKeyOfType("Character")){futureMessage += Keyboard.getKeyValue(); }
                else if (Keyboard.IsLastKeyOfType("Backspace") && futureMessage.length() > 0){futureMessage = futureMessage.substring(0,futureMessage.length()-1);}
                else if (Keyboard.IsLastKeyOfType("Enter")){
                    System.out.print("Enviando");
                    client.Send("["+name+"] "+ futureMessage);
                    futureMessage = "";
                }
                Engine.Render();
                Keyboard.Clear();
            }
        }
        catch (Exception e){Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}
    }

    @Override
    public String Frame() {
        frame = "Chat abierto, bienvenido " + name +":";
        frame += mensajes;
        frame += "\n\nEnvia tu mensaje: " + futureMessage;
        return frame;
    }
    
}

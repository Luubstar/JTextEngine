package Examples;

import java.util.Scanner;

import Engine.Keyboard;
import Engine.Menu;
import Engine.NetworkClient;
import Engine.Debug;

public class TestChat extends Menu{

    private String name = "Test";
    private String mensajes = "";
    private String frame;
    private String futureMessage = "";
    private String IP;
    private int port;
    private NetworkClient client;

    @Override
    public void Start() {
        try{
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Pulsa una tecla para continuar ");
            IP = scanner.nextLine();

            System.out.println("Introduzca la dirección IP: ");
            IP = scanner.nextLine();
            System.out.println("Introduzca el puerto: ");
            port = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Introduzca su nombre de usuario: ");
            name = scanner.nextLine();

            scanner.close();
            client = new NetworkClient(IP, port);
            client.Connect();
            client.Send("["+name+"] Se ha unido a la sala");
            
        }
        catch (Exception e){Debug.LogError(e.getMessage());}

    }

    @Override
    public void Update() {
        try{
            String data = client.ReceiveString();
            if (data.length() > 0){
                mensajes += "\n" + data;
            }

            if (Keyboard.IsLastKeyOfType("Character")){futureMessage += Keyboard.getKeyValue();}
            else if (Keyboard.IsLastKeyOfType("Backspace") && futureMessage.length() > 0){futureMessage = futureMessage.substring(0,futureMessage.length()-1);}
            else if (Keyboard.IsLastKeyOfType("Enter")){
                System.out.print("Enviando");
                client.Send("["+name+"] "+ futureMessage);
                futureMessage = "";
            }
            Keyboard.Clear();
        }
        catch (Exception e){Debug.LogError(e.getMessage());}
    }

    @Override
    public String Frame() {
        frame = "Chat abierto, bienvenido " + name +":";
        frame += mensajes;
        frame += "\n\nEnvia tu mensaje: " + futureMessage;
        return frame;
    }
    
}

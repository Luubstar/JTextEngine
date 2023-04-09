package Examples;

import java.util.Scanner;

import Engine.Engine;
import Engine.Keyboard;
import Engine.Menu;
import Engine.NetworkClient;

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
        catch (Exception e){Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);}

    }

    @Override
    public void Update() {
        try{
            String data = client.ReceiveString();
            if (data.length() > 0){
                mensajes += "\n" + data;
            }

            if (Keyboard.getKeyCode() != 0 && Keyboard.getKeyCode() != 28 && Keyboard.getKeyCode() != 14){futureMessage += String.valueOf(Keyboard.getKeyCharacter());}
            else if (Keyboard.getKeyCode() == 14 && futureMessage.length() > 0){futureMessage = futureMessage.substring(0,futureMessage.length()-1);}
            else if (Keyboard.getKeyCode() == 28){
                System.out.print("Enviando");
                client.Send("["+name+"] "+ futureMessage);
                futureMessage = "";
            }
            Keyboard.Clear();
        }
        catch (Exception e){Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);}
    }

    @Override
    public String Frame() {
        frame = "Chat abierto, bienvenido " + name +":";
        frame += mensajes;
        frame += "\n\nEnvia tu mensaje: " + futureMessage;
        return frame;
    }
    
}

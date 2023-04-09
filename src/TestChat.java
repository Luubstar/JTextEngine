import java.util.Scanner;

public class TestChat extends Menu{

    private String name;
    private String mensajes;
    private String frame;
    private String IP;
    private int port;

    @Override
    public void Start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca la dirección IP: ");
        IP = scanner.nextLine();
        System.out.println("Introduzca el puerto: ");
        port = scanner.nextInt();
        System.out.println("Introduzca su nombre de usuario: ");
        name = scanner.nextLine();
        scanner.close();


    }

    @Override
    public void Update() {
        
    }

    @Override
    public String Frame() {
        frame = "Chat abierto: ";
        return frame;
    }
    
}

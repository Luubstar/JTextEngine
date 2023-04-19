package Engine;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Engine {
    private static int tick = 50;
    private static Menu MenuActual;
    private static String DLog = "";
    private static boolean DebugMode = false;

    public static int DEBUGWARNING = 0;
    public static int DEBUGERROR = 1;
    public static int DEBUGMESSAGE = 2;
    public static int DEBUGOK = 3;

    private static int WIDTH;
    private static int HEIGHT;

    public static void Start(Menu MenuInicial){
        try{
            updateSize();

            Keyboard.Start();
            MenuActual = MenuInicial;
            MenuActual.Start();
            Tick Ticker = new Tick();
            Ticker.start();
        }
        catch(Exception e){Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);}
    }

    public static void print(String text){System.out.print(text + "\n");}
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();

        } catch (Exception e) {Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);;}
    }

    public static void SetDebugMode(boolean mode){DebugMode = mode;}
    public static boolean GetDebugMode(){return DebugMode;}

    public static int frameTime(){return tick;};
    public static void SetFrameTime(int ms){tick = ms;}
    public static void SetMenu(Menu newMenu){MenuActual = newMenu;clearConsole(); MenuActual.Start();}
    public static String Draw(){return MenuActual.Frame();}
    public static void GetInput() throws Exception{
        MenuActual.Update();
        Keyboard.DetectInput();
    }

    public static void DebugSingleLog(String log){DLog = log;}
    public static void DebugSingleLog(String log, Colors color){DLog = color.colorizeNoSane( log);}
    public static void DebugSingleLog(String log, int Modo){
        String prefix = "";
        if(Modo == DEBUGMESSAGE){prefix = "📃";}
        else if (Modo == DEBUGOK){prefix = "✅" + Colors.GREEN;}
        else if (Modo == DEBUGWARNING){prefix = "⚠️" + Colors.YELLOW;}
        else if (Modo == DEBUGERROR){prefix = "❌" + Colors.RED;}
        DLog = prefix + log;
    }

    public static void DebugLog(String log){DLog += "\n " +log;}
    public static void DebugLog(String log, Colors color){DLog +=color.colorizeNoSane("\n " + log);}
    public static void DebugLog(String log, int Modo){ 
        String prefix = "";
        if(Modo == DEBUGMESSAGE){prefix = "✉ ";}
        else if (Modo == DEBUGOK){prefix = Colors.GREEN + "✔ ";}
        else if (Modo == DEBUGWARNING){prefix = Colors.YELLOW + "⚠ ";}
        else if (Modo == DEBUGERROR){prefix = Colors.RED + "✘ ";}
        DLog += "\n " + prefix + log;
    }

    public static void ClearLog(){DLog = "";}
    public static String GetLog(){return DLog;}
    
    public static String getFileExtension(File file) {
        try{
            String name = file.getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf == -1) {
                return ""; 
            }
            return name.substring(lastIndexOf);
        }
        catch(Exception e){Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);; return "";}
    }

    public static String getSize(){
        try{
            String os = System.getProperty("os.name").toLowerCase();
            String[] command;
            if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                command = new String[] { "sh", "-c", "stty size </dev/tty" };
            } else if (os.contains("win")) {
                command = new String[] { "cmd.exe", "/c", "mode con | findstr /C:\"Columns\"" };
            } else {
                Engine.DebugLog("No se pudo determinar el sistema operativo.");
                return "";
            }

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();
            
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("");
            }
           
            String[] result = output.toString().split(" ");
            return result[1] + "x" + result[0];
        }
        catch(Exception e){Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);; return "";}
    }

    public static int getWidth(){return WIDTH;}
    public static int getHeight(){return HEIGHT;}

    public static void updateSize(){
        String[] size = getSize().split("x");
        WIDTH = Integer.parseInt(size[0]);
        HEIGHT = Integer.parseInt(size[1]);
}

    public static String[] splitString(String input, int maxLength) {

        int chunks = (int) Math.ceil((double) input.length() / maxLength);
        
        String[] result = new String[chunks];
        
        for (int i = 0; i < chunks; i++) {
            int start = i * maxLength;
            int end = Math.min(start + maxLength, input.length());
            result[i] = input.substring(start, end);
        }
        
        return result;
    }
}
 
import java.io.File;
public class Engine {
    private static int tick = 50;
    private static Menu MenuActual;
    private static String DLog = "";

    public static void Start(Menu MenuInicial){
        try{
       
            Keyboard.Start();

            MenuActual = MenuInicial;
            Tick Ticker = new Tick();
            Ticker.start();
        }
        catch(Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}
    }

    public static void print(String text){System.out.print(text + "\n");}
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();

        } catch (Exception e) {Engine.DebugLog("❌ " + e.getMessage(), Colors.Red);}
    }

    public static int frameTime(){return tick;};
    public static void SetFrameTime(int ms){tick = ms;}
    public static void SetMenu(Menu newMenu){MenuActual = newMenu;}
    public static String Draw(){return MenuActual.Frame();}
    public static void GetInput(){MenuActual.InputSystem();}

    public static void DebugSingleLog(String log){DLog = log;}
    public static void DebugSingleLog(String log, Colors color){DLog = color.colorizeNoSane( log);}

    public static void DebugLog(String log){DLog += "\n" +log;}
    public static void DebugLog(String log, Colors color){DLog +=color.colorizeNoSane("\n" + log);}

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
        catch(Exception e){Engine.DebugLog("❌ " + e.getMessage(), Colors.Red); return "";}
    }
}

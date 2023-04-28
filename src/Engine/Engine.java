package Engine;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import Engine.Debug.Debug;
import Engine.Debug.Profiler;
public class Engine {
    private static int tick = 50;
    private static Menu MenuActual;
    private static int WIDTH;
    private static int HEIGHT;

    /**
     * This function starts the program by initializing various components and starting the tick clock.
     * 
     * @param MenuInicial MenuInicial is a parameter of type Menu that is passed to the Start method.
     * It is likely used to set the initial menu that the user sees when the program starts.
     */
    public static void Start(Menu MenuInicial){
        try{
            updateSize();
            Profiler.Start();
            Keyboard.Start();
            SetMenu(MenuInicial);
            Tick Ticker = new Tick();
            Ticker.start();
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }

    /**
     * Prints text on the console
     */
    public static void print(String text){System.out.print(text + "\n");}
    /**
     * Clears the console
     */
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();

        } catch (Exception e) {Debug.LogError(e.getMessage());}
    }

    public static void SetDebugMode(boolean mode){Debug.SetDebugMode(mode);}
    public static boolean GetDebugMode(){return Debug.GetDebugMode();}

    public static void setFPSMode(boolean mode){Profiler.setFPSMode(mode);}
    public static boolean getFPSMode(){return Profiler.getFPSMode();}

    public static void setProfilerMode(boolean mode){Profiler.setProfilerMode(mode);}
    public static boolean getProfilerMode(){return Profiler.getProfilerMode();}

    public static int frameTime(){return tick;};
    public static void SetFrameTime(int ms){tick = ms;}
    
    public static void SetMenu(Menu newMenu){
        MenuActual = newMenu;
        clearConsole();
        MenuActual.Start();
    }
    public static String Draw(){return MenuActual.Frame();}

    public static void GetInput() throws Exception{
        Keyboard.DetectInput();
        MenuActual.Update();
    }
    
    /** 
     * Returns the extension of a file
    */
    public static String getFileExtension(File file) {
        try{
            String name = file.getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf == -1) {
                return ""; 
            }
            return name.substring(lastIndexOf);
        }
        catch(Exception e){Debug.LogError(e.getMessage()); return "";}
    }

    /**
     * This function gets the size of the terminal window in which the program is running.
     * 
     * @return The method is returning a string that represents the size of the console window in the
     * format "columns x rows".
     */
    public static String getSize(){
        try{
            String os = System.getProperty("os.name").toLowerCase();
            String[] command;
            if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                command = new String[] { "sh", "-c", "stty size </dev/tty" };
            } else if (os.contains("win")) {
                command = new String[] { "cmd.exe", "/c", "mode con | findstr /C:\"Columns\"" };
            } else {
                Debug.LogWarning("No se pudo determinar el sistema operativo.");
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
        catch(Exception e){Debug.LogError(e.getMessage()); return "";}
    }

    public static int getWidth(){return WIDTH;}
    public static int getHeight(){return HEIGHT;}

    /**
     * The function checks if the size of the window has been resized and updates it if necessary.
     */
    public static void CheckIfResized(){
        String[] size = getSize().split("x");
        if (Integer.parseInt(size[0]) != WIDTH || Integer.parseInt(size[1]) != HEIGHT){
            updateSize();
            MenuActual.OnRescale();
        }
    }


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

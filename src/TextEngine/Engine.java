package TextEngine;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import TextEngine.Debug.Debug;
import TextEngine.Debug.Profiler;

import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Engine {
    private static int tick = 50;
    private static Menu MenuActual;
    private static int WIDTH;
    private static int HEIGHT;

    private static final String SPACE = " ";
    private static final String VSPACE = "\n";

    static TerminalScreen screen;
	static Terminal terminal;

    public enum VAling{
        UP,
        DOWN,
        CENTER
    }

    public enum HAling{
        LEFT,
        RIGTH,
        CENTER
    }

    /**
     * This function starts the program by initializing various components and starting the tick clock.
     * 
     * @param MenuInicial MenuInicial is a parameter of type Menu that is passed to the Start method.
     * It is likely used to set the initial menu that the user sees when the program starts.
     */
    public static void Start(Menu MenuInicial){
        try{
            RedoTerminal();
            updateSize();

            Profiler.Start();
            Keyboard.Start(terminal,screen);
            SetMenu(MenuInicial);
            Tick Ticker = new Tick();
            Ticker.start();
        }
        catch(Exception e){Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}
    }

    /**
     * Updates the terminal if needed
     * @throws Exception
     */
    private static void RedoTerminal() throws Exception{
    
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null); 
    
        Engine.clearConsole();
    }

    /**
     * Prints text on the console
     */
    public static void print(String text){System.out.print(text + "\n");}

    /**
     * Renders the frame
     */
    public static void Render(){Tick.Render();}




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

        } catch (Exception e) {Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}
    }

    /**
     * Sets the Debug mode and then opens a debug menu (if it has exceptions)
     * @param mode
     */
    public static void setDebugMode(boolean mode){Debug.SetDebugMode(mode);}
    /**
     * Returns the Debug mode
     * @return
     */
    public static boolean GetDebugMode(){return Debug.GetDebugMode();}

    /**
     * Sets the FPS mode and then shows the fps of the execution
     * @param mode
     */
    public static void setFPSMode(boolean mode){Profiler.setFPSMode(mode);}
    /**
     * Returns the FPS mode
     * @return
     */
    public static boolean getFPSMode(){return Profiler.getFPSMode();}

    /**
     * Sets the Profiler mode and then shows the ms of each part of the execution
     * @param mode
     */
    public static void setProfilerMode(boolean mode){Profiler.setProfilerMode(mode);}
    /**
     * Returns the Profiler mode
     * @return
     */
    public static boolean getProfilerMode(){return Profiler.getProfilerMode();}

    /**
     * Get the time between frames
     * @return
     */
    public static int frameTime(){return tick;};
    /**
     * Sets the time between frames
     * @return
     */
    public static void SetFrameTime(int ms){tick = ms;}
    
    /**
     * Sets the Menu of the Engine
     * @param newMenu
     */
    public static void SetMenu(Menu newMenu){
        MenuActual = newMenu;
        clearConsole();
        MenuActual.Start();
        Keyboard.Clear();
        Engine.Render();
    }
    /**
     * Returns the new frame 
     * @return
     */
    public static String Draw(){return MenuActual.Frame();}

    /**
     * Gets the user input and updates the menu
     * @throws Exception
     */
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
        catch(Exception e){Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber()); return "";}
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
        catch(Exception e){Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber()); return "";}
    }

    /**
     * Returns the width of the screen
     * @return
     */
    public static int getWidth(){return WIDTH;}
    /**
     * Returns the Height of the screen
     * @return
     */
    public static int getHeight(){return HEIGHT;}

    /**
     * The function checks if the size of the window has been resized and updates it if necessary.
     */
    public static void CheckIfResized(){
        String[] size = getSize().split("x");
        if (Integer.parseInt(size[0]) != WIDTH || Integer.parseInt(size[1]) != HEIGHT){
            updateSize();
            MenuActual.OnRescale();
            Engine.Render();
        }
    }

    
    /**
     * This function updates the width and height variables based on the current size of the object.
     */
    public static void updateSize(){
        String[] size = getSize().split("x");
        WIDTH = Integer.parseInt(size[0]);
        HEIGHT = Integer.parseInt(size[1]);
    }

    /**
     * Splits the string in slices of length <= maxLength
     * @param input
     * @param maxLength
     * @return
     */
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


    /**
     * Aligns text horizontally according to the requested aligner
     * @param aling
     * @param text
     * @return
     */
    public static String HorizontalAling(HAling aling, String text){
        String[] lines = text.split("\n");
        String finalLine = "";
        for (String line: lines){
            if(line.length() >= WIDTH || line.length() == 0 || line == null){}
            else{
                String cleanText = Colors.clearColor(line);
                int SpaceNeeded = WIDTH - cleanText.length();

                if (aling == HAling.CENTER){
                    if (SpaceNeeded%2 != 0){SpaceNeeded--;}
                    line = SPACE.repeat(SpaceNeeded/2) + line + SPACE.repeat(SpaceNeeded/2);
                }
                else if (aling == HAling.RIGTH){line = SPACE.repeat(SpaceNeeded) + line;}
                else if (aling == HAling.LEFT){line = line + SPACE.repeat(SpaceNeeded);}
            }

            finalLine += line + "\n";
        }
        return finalLine;
    }

    /**
     * Aligns text vertically according to the requested aligner
     * @param aling
     * @param text
     * @return
     */
    public static String VerticalAling(VAling aling, String text){
        String[] lines = text.split("\n");
        if (lines.length <= HEIGHT){
            
            int SpaceNeeded = HEIGHT - lines.length;

            if (aling == VAling.CENTER){
                if (SpaceNeeded%2 != 0){SpaceNeeded--;}
                text = VSPACE.repeat(SpaceNeeded/2) + text + VSPACE.repeat((SpaceNeeded/2) - 2);
            }
            else if (aling == VAling.DOWN){text = VSPACE.repeat(SpaceNeeded) + text;}
            else if (aling == VAling.UP){text = text + VSPACE.repeat(SpaceNeeded-2);}
            return text;
        }
        return text;
    }

}

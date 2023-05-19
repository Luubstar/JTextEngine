package TextEngine.Debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import TextEngine.Colors;
import TextEngine.Engine;


public class Debug {

    public static int DEBUGWARNING = 0;
    public static int DEBUGERROR = 1;
    public static int DEBUGMESSAGE = 2;
    public static int DEBUGOK = 3;


    static String block = "━";
    static String verticalBlock = "┃";
    static String space = " ";
    static String title ="┃  Debug Log:";

    private static ArrayList<Log> DLog = new ArrayList<>();
    private static boolean DebugMode = false;

    /**
     * Returns the Debug mode
     * @return
     */
    public static boolean GetDebugMode(){return DebugMode;}
    /**
     * Sets the Sebug mode
     * @param mode
     */
    public static void SetDebugMode(boolean mode){DebugMode = mode;}

    /**
     * Logs a simple string
     * @param log
     */
    static void Log(String log){DLog.add(new Log(log));CheckIfRepited();}
    /**
     * Logs a simple string with a custom Color
     * @param log
     * @param color
     */
    public static void Log(String log, Colors color){DLog.add(new Log(color.colorizeNoSane("\n " + log)));CheckIfRepited();}
    /**
     * Logs a string using the static ints
     * @param log
     * @param Modo
     */
    static void Log(String log, int Modo){ 
        String prefix = "";
        if(Modo == DEBUGMESSAGE){prefix = "✉ ";}
        else if (Modo == DEBUGOK){prefix = Colors.GREEN + "✔ ";}
        else if (Modo == DEBUGWARNING){prefix = Colors.YELLOW + "⚠ ";}
        else if (Modo == DEBUGERROR){prefix = Colors.RED + "✘ ";}
        DLog.add(new Log(prefix + log));
        CheckIfRepited();
    }

    /**
     * Logs a green String. Use when something is OK
     * @param log
     */
    public static void LogOK(String log){Log(log,DEBUGOK);}
    /**
     * Logs a red String. Use when something is an Error
     * @param log
     */
    public static void LogError(String log){Log(log,DEBUGERROR);}
    /**
     * Logs a yellow String. Use when something is a Warning
     * @param log
     */
    public static void LogWarning(String log){Log(log,DEBUGWARNING);}
    /**
     * Logs a simple string. Prefer over Log()
     * @param log
     */
    public static void LogMessage(String log){Log(log,DEBUGMESSAGE);}

    /**
     * Clears all the logs in the Debug Menu
     */
    public static void ClearLog(){DLog.clear();}
    /**
     * Returns the logs of the Debug Menu
     * @return
     */
    public static ArrayList<Log> GetLog(){return DLog;}
    /**
     * Sets the logs of the Debug Menu
     * @param List
     */
    public static void SetLog(ArrayList<Log> List){DLog = List;}

    /**
     * Export logs as textfile
     * @param filepath -> file to export
     * @throws Exception
     */
    public static void ExportLogs(String filepath) throws Exception{
        
        File archivo = new File(filepath);

        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        else{LogWarning(filepath + " already exists. Overwriting...");}

        FileWriter fw = new FileWriter(archivo);
        BufferedWriter bw = new BufferedWriter(fw);

        String log = "";
        for (Log logALeer:GetLog()){
            log += Colors.clearColor(logALeer.getMessage() + logALeer.getQuantityString()) + "\n";
        }

        bw.write(log);
        bw.newLine();

        bw.close();
        fw.close();

    }

    /**
     * Checks if some log is duplicated
     */
    public static void CheckIfRepited(){
        ArrayList<Log> List = GetLog();
        ArrayList<Log> NewList = new ArrayList<>();
        
        for (Log log1 : List){

            if (log1.getQuantity() > 0){

                for (Log log2: List){
                    if (log1.getMessage().equals(log2.getMessage()) && log1 != log2){
                        log1.setQuantity(log2.getQuantity() + log1.getQuantity());
                        log2.setQuantity(-1);
                    }
                }
            
                NewList.add(log1);
            }
        } 

        SetLog(NewList);
    }

    /**
     * Exports the Debug Menu to a string (used to print the frame)
     * @return
     */
    public static String LogToString(){
        try{
            String logstring = "";
            if (Engine.getWidth() == 0 || Engine.getHeight() == 0){
                Engine.updateSize();
            }
            if (GetLog().size() > 0){
                logstring += "┏" + block.repeat(Engine.getWidth() -2) + "┓";
                logstring +=  title + space.repeat(Engine.getWidth() - title.length() - 1) + verticalBlock;
                ArrayList<Log> logs = GetLog();

                for (int i = 0; i < GetLog().size(); i++ ){
                    Log newlog = logs.get(i);
                    
                    String ColoredMessage = newlog.getMessage() + newlog.getQuantityString();
                    String message  = Colors.clearColor(ColoredMessage);
                    String Color = Colors.getColor(ColoredMessage);
                    
                    if (message.length() > Engine.getWidth()-2){
                        String[] SplitedLogs = Engine.splitString(message, Engine.getWidth() -7);
                        for (int a = 0; a < SplitedLogs.length; a++){
                            String splitedlog = SplitedLogs[a];
                            message  = Colors.clearColor(splitedlog);
                            if (a == 0){logstring += verticalBlock + "  " + Color + message + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}    
                            else{logstring += verticalBlock + "  " + Color + message + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}
                        }
                    }
                    else{logstring += verticalBlock + "  " + ColoredMessage + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}}
                logstring += "\n┗" + block.repeat(Engine.getWidth() -2) + "┛";
            }
            return logstring;
        }
        catch(Exception e){Debug.LogError(e.getMessage());return "";}
    }
}


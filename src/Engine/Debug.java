package Engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


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

    public static boolean GetDebugMode(){return DebugMode;}
    public static void SetDebugMode(boolean mode){DebugMode = mode;}

    public static void Log(String log){DLog.add(new Log(log));CheckIfRepited();}
    public static void Log(String log, Colors color){DLog.add(new Log(color.colorizeNoSane("\n " + log)));CheckIfRepited();}
    public static void Log(String log, int Modo){ 
        String prefix = "";
        if(Modo == DEBUGMESSAGE){prefix = "✉ ";}
        else if (Modo == DEBUGOK){prefix = Colors.GREEN + "✔ ";}
        else if (Modo == DEBUGWARNING){prefix = Colors.YELLOW + "⚠ ";}
        else if (Modo == DEBUGERROR){prefix = Colors.RED + "✘ ";}
        DLog.add(new Log(prefix + log));
        CheckIfRepited();
    }

    public static void LogOK(String log){Log(log,DEBUGOK);}
    public static void LogError(String log){Log(log,DEBUGERROR);}
    public static void LogWarning(String log){Log(log,DEBUGWARNING);}
    public static void LogMessage(String log){Log(log,DEBUGMESSAGE);}

    public static void ClearLog(){DLog.clear();}
    public static ArrayList<Log> GetLog(){return DLog;}
    public static void SetLog(ArrayList<Log> List){DLog = List;}

    public static void ExportLogs(String filename) throws Exception{
        
        File archivo = new File(filename);

        if (!archivo.exists()) {
            archivo.createNewFile();
        }
        else{LogWarning(filename + " already exists. Overwriting...");}

        FileWriter fw = new FileWriter(archivo);
        BufferedWriter bw = new BufferedWriter(fw);

        String log = "";
        for (Log logALeer:GetLog()){
            log += Colors.clearANSI(logALeer.getMessage() + logALeer.getQuantityString()) + "\n";
        }

        bw.write(log);
        bw.newLine();

        bw.close();
        fw.close();

    }

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
                    
                    String message  = Colors.clearANSI(newlog.getMessage() + newlog.getQuantityString())  ;
                    
                    if (message.length() > Engine.getWidth()-2){
                        String[] SplitedLogs = Engine.splitString(message, Engine.getWidth() -7);
                        for (int a = 0; a < SplitedLogs.length; a++){
                            String splitedlog = SplitedLogs[a];
                            message  = Colors.clearANSI(splitedlog);
                            if (a == 0){logstring += verticalBlock + "  " + splitedlog + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}    
                            else{logstring += verticalBlock + "     " + splitedlog + Colors.SANE+ space.repeat(Engine.getWidth() - 7 - message.length()) + verticalBlock;}
                        }
                    }
                    else{logstring += verticalBlock + "  " + message + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}}
                logstring += "\n┗" + block.repeat(Engine.getWidth() -2) + "┛";
            }
            return logstring;
        }
        catch(Exception e){Debug.LogError(e.getMessage());return "";}
    }
}


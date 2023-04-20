package Engine;

public class Debug {

    public static int DEBUGWARNING = 0;
    public static int DEBUGERROR = 1;
    public static int DEBUGMESSAGE = 2;
    public static int DEBUGOK = 3;

    private static String DLog = "";
    private static boolean DebugMode = false;

    public static boolean GetDebugMode(){return DebugMode;}
    public static void SetDebugMode(boolean mode){DebugMode = mode;}

    public static void Log(String log){DLog += "\n " +log;}
    public static void Log(String log, Colors color){DLog +=color.colorizeNoSane("\n " + log);}
    public static void Log(String log, int Modo){ 
        String prefix = "";
        if(Modo == DEBUGMESSAGE){prefix = "✉ ";}
        else if (Modo == DEBUGOK){prefix = Colors.GREEN + "✔ ";}
        else if (Modo == DEBUGWARNING){prefix = Colors.YELLOW + "⚠ ";}
        else if (Modo == DEBUGERROR){prefix = Colors.RED + "✘ ";}
        DLog += "\n " + prefix + log;
    }

    public static void LogOK(String log){Log(log,DEBUGOK);}
    public static void LogError(String log){Log(log,DEBUGERROR);}
    public static void LogWarning(String log){Log(log,DEBUGWARNING);}
    public static void LogMessage(String log){Log(log,DEBUGMESSAGE);}

    public static void ClearLog(){DLog = "";}
    public static String GetLog(){return DLog;}
}

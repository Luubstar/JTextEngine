package Engine;

public class Tick extends Thread {
    String frame = "";
    String log = "";
    String block = "━";
    String verticalBlock = "┃";
    String space = " ";
    String title ="┃  Debug Log:";
    public Tick(){}
    @Override
    public void run(){
        Engine.print("\033[?25l");
        while (true){
            try {

                Engine.GetInput();
                frame = "";
                if (Engine.GetDebugMode()){frame = AddLog(log);}

                frame += Engine.Draw();

                Engine.clearConsole();
                System.out.flush();

                Engine.print(frame);
                System.out.flush();

                Keyboard.pos++;
                if (Keyboard.pos > 5){
                    Keyboard.Clear();
                }


                Thread.sleep(Engine.frameTime());

            } catch (Exception e) {Engine.DebugLog(e.getMessage(), Engine.DEBUGERROR);;}
        }
    }

    private String AddLog(String log){
        try{
            String logstring = "";
            log = Engine.GetLog();
            if (log != ""){
                logstring += "┏" + block.repeat(Engine.getWidth() -2) + "┓";
                logstring +=  title + space.repeat(Engine.getWidth() - title.length() - 1) + verticalBlock;
                String[] logs = log.split("\n");
                for (int i = 0; i < logs.length; i++ ){
                    String newlog = logs[i];
                    
                    String clearlog  = Colors.clearANSI(newlog);
                    
                    if (clearlog.length() > Engine.getWidth()-2){
                        String[] SplitedLogs = Engine.splitString(newlog, Engine.getWidth() -7);
                        for (int a = 0; a < SplitedLogs.length; a++){
                            String splitedlog = SplitedLogs[a];
                            clearlog  = Colors.clearANSI(splitedlog);
                            if (a == 0){logstring += verticalBlock + "  " + splitedlog + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - clearlog.length()) + verticalBlock;}    
                            else{logstring += verticalBlock + "     " + splitedlog + Colors.SANE+ space.repeat(Engine.getWidth() - 7 - clearlog.length()) + verticalBlock;}
                        }
                    }
                    else{logstring += verticalBlock + "  " + newlog + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - clearlog.length()) + verticalBlock;}}
                logstring += "\n┗" + block.repeat(Engine.getWidth() -2) + "┛";
            }
            return logstring;
        }
        catch(Exception e){Engine.DebugSingleLog(e.getMessage() + " " + e.getCause(), Engine.DEBUGERROR);return "";}
    }



}

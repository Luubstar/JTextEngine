
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
                frame = "";
                if (Engine.GetDebugMode()){frame = AddLog(log);}

                frame += Engine.Draw();

                Engine.clearConsole();
                System.out.flush();

                Engine.print(frame);
                System.out.flush();

                Keyboard.pos++;
                if (Keyboard.pos > 5){
                    Keyboard.pos = 0;
                    Keyboard.LastKey = 0;
                }

                Engine.GetInput();

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

                    if (newlog.length() > Engine.getWidth()-2){
                        newlog = "Error, Log is too big to print (WIP)";
                    }
                    logstring += verticalBlock + "  " + newlog+ space.repeat(Engine.getWidth() - 4 - newlog.length()) + verticalBlock; }

                logstring += "\n┗" + block.repeat(Engine.getWidth() -2) + "┛";
            }
            return logstring;
        }
        catch(Exception e){Engine.print(e.getMessage() + e.getCause());return "";}
    }

}

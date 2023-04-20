package Engine;

public class Tick extends Thread {
    String frame = "";
    String log = "";
    public Tick(){}
    @Override
    public void run(){
        Engine.print("\033[?25l");
        while (true){
            try {

                Engine.GetInput();
                
                frame = "";
                if (Engine.GetDebugMode()){frame = Debug.LogToString();}

                frame += Engine.Draw();
                Engine.clearConsole();

                Engine.print(frame);
                System.out.flush();

                Keyboard.pos++;
                if (Keyboard.pos > 5){
                    Keyboard.Clear();
                }

                Engine.CheckIfResized();
                Thread.sleep(Engine.frameTime());

            } catch (Exception e) {Debug.LogError(e.getMessage());}
        }
    }

}


public class Tick extends Thread {
    String frame = "";
    public Tick(){}
    @Override
    public void run(){
        Engine.print("\033[?25l");
        while (true){
            try {
                frame = Engine.GetLog() + Engine.Draw();

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

            } catch (Exception e) {Engine.DebugLog(e.getMessage(), Colors.Red);}
        }
    }
}

package Engine;

import Engine.Debug.Debug;
import Engine.Debug.Profiler;

public class Tick extends Thread {
    String frame = "";
    String log = "";
    public Tick(){}
    @Override
    public void run(){
        Engine.print("\033[?25l");
        while (true){
            try {
                Profiler.StartMeasure("TickTime");
                Engine.GetInput();
                
                frame = "";
                if (Profiler.getProfilerMode()){frame = Profiler.ProfilerToString();}
                if (Engine.GetDebugMode()){frame += "\n"+ Debug.LogToString();}

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
                Profiler.EndMeasure("TickTime");

            } catch (Exception e) {Debug.LogError(e.getMessage());}
        }
    }

}

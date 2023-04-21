package Engine;

import Engine.Debug.Debug;
import Engine.Debug.Profiler;

public class Tick extends Thread {
    String frame = "";
    String log = "";

    String lastFrame = "";

    long startTime;
    long endTime;
    long elapsed;
    public Tick(){}
    @Override
    public void run(){
        Engine.print("\033[?25l");
        while (true){

            try {
                startTime = System.currentTimeMillis();
                Profiler.StartMeasure("TickTime");

                Profiler.StartMeasure("Other");

                Engine.GetInput();
                Keyboard.pos++;
                if (Keyboard.pos > 5){
                    Keyboard.Clear();
                }

                Engine.CheckIfResized();
                
                Profiler.EndMeasure("Other");

                frame = "";

                Profiler.StartMeasure("Profiler");
                if (Profiler.getProfilerMode()){frame = Profiler.ProfilerToString();}
                if (Profiler.getFPSMode()){frame += Profiler.getFPS();}
                Profiler.EndMeasure("Profiler");
                
                Profiler.StartMeasure("DebugLogger");
                if (Engine.GetDebugMode()){
                    frame += "\n"+ Debug.LogToString();}
                Profiler.EndMeasure("DebugLogger");

                
                Profiler.StartMeasure("Render");
                frame += Engine.Draw();
                if  (frame != lastFrame){
                    Engine.clearConsole();

                    Engine.print(frame);
                    System.out.flush();
                }
                Profiler.EndMeasure("Render");

                endTime = System.currentTimeMillis();
                elapsed = endTime - startTime;
                Profiler.StartMeasure("WaitTime");
                if (elapsed > 0 && elapsed < Engine.frameTime()){
                    Thread.sleep(Engine.frameTime() - elapsed);
                }
                Profiler.EndMeasure("WaitTime");
                Profiler.EndMeasure("TickTime");

            } catch (Exception e) {Debug.LogError(e.getMessage());}
        }
    }

}

package TextEngine;

import TextEngine.Debug.Debug;
import TextEngine.Debug.Profiler;

public class Tick extends Thread {
    String frame = "";
    String log = "";
    String Eframe = "";
    String lastFrame = "";

    static boolean PrintNextFrame = false;

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

                Profiler.StartMeasure("Input");
                Engine.GetInput();
                Keyboard.pos++;
                if (Keyboard.pos > 5){
                    Keyboard.Clear();
                }
                Profiler.EndMeasure("Input");

                RenderF();

                endTime = System.currentTimeMillis();
                elapsed = endTime - startTime;
                Profiler.StartMeasure("WaitTime");
                if (elapsed > 0 && elapsed < Engine.frameTime()){
                    Thread.sleep(Engine.frameTime() - elapsed);
                }
                Profiler.EndMeasure("WaitTime");
                Profiler.EndMeasure("TickTime");
            } catch (Exception e) {Debug.LogError(e.getMessage() + " " + e.getStackTrace()[0].getLineNumber());}
        }
    }


    static String ProfilerF(String frame){
        Profiler.StartMeasure("Profiler");
        if (Profiler.getProfilerMode()){frame = Profiler.ProfilerToString();}
        if (Profiler.getFPSMode()){frame += Profiler.getFPS();}
        Profiler.EndMeasure("Profiler");
        return frame;
    }

    static String DebugF(String frame){
        Profiler.StartMeasure("DebugLogger");
        if (Engine.GetDebugMode()){
            frame += "\n"+ Debug.LogToString();}
        Profiler.EndMeasure("DebugLogger");
        return frame;
    }

    static String MainF(String frame, String lastFrame, String extra){
    
        frame = Engine.Draw();
        if  (!frame.equals( extra + lastFrame)){
            Engine.clearConsole();
            Engine.print(extra + frame);
            System.out.flush();
            lastFrame = frame;
        }
        return lastFrame;
        }

    void RenderF(){
        Profiler.StartMeasure("Render");
        Engine.CheckIfResized();

        if (PrintNextFrame){
            frame = "";
            Eframe = "";
            Eframe += ProfilerF(frame);
            Eframe += DebugF(frame);
            lastFrame = MainF(frame, lastFrame, Eframe);
            PrintNextFrame = false;
        }
        Profiler.EndMeasure("Render");
    }

    public static void Render(){
        PrintNextFrame = true;
    }
}

package Examples;
import Engine.Engine;
import Engine.Debug.Profiler;

public class Start
{
   public static void main(String[] args){
      Engine.SetDebugMode(true);
      Engine.SetFrameTime(20);
      Engine.setProfilerMode(true);
      Profiler.setFilter("All");
      Engine.Start(new StartMenu());
   }
}

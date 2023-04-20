package Examples;
import Engine.Engine;
import Engine.Debug.Profiler;

public class Start
{
   public static void main(String[] args){
      Engine.SetDebugMode(true);
      Engine.SetFrameTime(20);
      Engine.setProfilerMode(true);
      Engine.setFPSMode(true);
      Profiler.setFilter("All");
      try {
         Profiler.ExportPieChart("test.png", "all");
      } catch (Exception e) {
         // TODO: handle exception
      }
      Engine.Start(new StartMenu());
   }
}

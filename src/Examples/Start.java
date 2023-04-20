package Examples;
import Engine.Debug;
import Engine.Engine;

public class Start
{
   public static void main(String[] args){
      Engine.SetDebugMode(true);
      Engine.SetFrameTime(50);
      Debug.Log("error", Debug.DEBUGERROR);
      Engine.Start(new StartMenu());
   }
}

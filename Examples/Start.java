
import TextEngine.Engine;

public class Start
{
   public static void main(String[] args){
      Engine.setDebugMode(true);
      Engine.SetFrameTime(20);
      Engine.Start(new StartMenu());
   }
}
 
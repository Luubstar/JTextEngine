package Examples;
import Engine.Engine;

public class Start
{
   public static void main(String[] args){
      Engine.SetDebugMode(true);
      
      Engine.SetFrameTime(20);
      Engine.Start(new StartMenu());
   }
}

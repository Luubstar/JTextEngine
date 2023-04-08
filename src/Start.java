
public class Start
{
   public static void main(String[] args){
      Engine.SetDebugMode(true);
      Engine.DebugLog("holisqasddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
      Engine.SetFrameTime(50);
      Engine.Start(new StartMenu());
   }
}
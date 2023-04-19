package Engine;
 
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


public class Keyboard  {
    private static int LastKey = 0;
    public static int pos = 0;
	private static char LastChar;

	static TerminalScreen screen;
	static Terminal terminal;
	public static void Start() throws Exception{

		terminal = new DefaultTerminalFactory().createTerminal();
		screen = new TerminalScreen(terminal);
		screen.startScreen();
		screen.setCursorPosition(null); 
	}

	public static void DetectInput() throws Exception{
		KeyStroke keyStroke = screen.pollInput();
		if (keyStroke != null) {
			if (keyStroke.getKeyType() == KeyType.Character) {
				LastChar = keyStroke.getCharacter();
				LastKey = (int) LastChar;
				pos = 0;
			}
		}
	}
	

    public static int getKeyCode(){return LastKey;}
    public static String getKeyValue(){return new String(new char[] {LastChar});}
	public static char getKeyCharacter() {return LastChar;}
	public static void Clear(){
		LastKey = 0;
		pos = 0;
	}

}
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
	private static KeyType LastKeyType;

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
			if (keyStroke.getKeyType() != null) {
				LastKeyType = keyStroke.getKeyType();
				pos = 0;
				if (keyStroke.getCharacter() != null){
				LastChar = keyStroke.getCharacter();
				LastKey = (int) LastChar;}
				while (screen.pollInput() != null) {
					// no hace nada, solo vacía la cola
				}
			}
			else{Clear();}
		}
	}
	

    public static int getKeyCode(){return LastKey;}
    public static String getKeyValue(){
		return new String(new char[] {LastChar});}
	public static char getKeyCharacter() {return LastChar;}
	public static KeyType getKeyType() {return LastKeyType;}
	public static void Clear(){
		LastKey = 0;
		LastChar = '\0';
		LastKeyType = null;
		pos = 0;
	}

	public static KeyType KeyTypeByString(String keyString) {
		for (KeyType keyType : KeyType.values()) {
			if (keyType.name().equalsIgnoreCase(keyString)) {
				return keyType;
			}
		}
		return null;
    }

	public static boolean IsLastKeyOfType(String keyType){
		return getKeyType() == KeyTypeByString(keyType);
	}

	public static boolean IsLastKeyValue(String keyValue){
		return getKeyValue().toLowerCase().equals(keyValue.toLowerCase());
	}


}

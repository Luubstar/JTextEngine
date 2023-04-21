package Engine;
 
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import Engine.Debug.Debug;


public class Keyboard  {

	public static final char ESCAPECHAR = ' ';
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

		Debug.LogMessage("Detectado " + keyStroke.getKeyType());
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
		LastChar = ESCAPECHAR;
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

	public static String Scanner(String prefix)throws Exception{
		String res = "";
		while(getKeyType() != KeyTypeByString("Enter")){
			if (Keyboard.IsLastKeyOfType("Character")){res += Keyboard.getKeyValue();}
            else if (Keyboard.IsLastKeyOfType("Backspace") && res.length() > 0){res = res.substring(0,res.length()-1);}
            else if (Keyboard.IsLastKeyOfType("Enter")){break;}
			System.out.print(prefix + res);
			Thread.sleep(Engine.frameTime());
			Engine.clearConsole();
			Clear();
			DetectInput();
		}
		Clear();
		DetectInput();
		return res;
	}

}

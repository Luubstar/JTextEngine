package Engine;
 
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;



public class Keyboard  {

	public static final char ESCAPECHAR = ' ';
    private static int LastKey = 0;
    public static int pos = 0;
	private static char LastChar;
	private static KeyType LastKeyType;

	static TerminalScreen screen;
	static Terminal terminal;

	/**
	 * Starts the keyboard system
	 * @throws Exception
	 */
	public static void Start() throws Exception{

		terminal = new DefaultTerminalFactory().createTerminal();
		screen = new TerminalScreen(terminal);
		screen.startScreen();
		screen.setCursorPosition(null); 
	}

	/**
	 * Reads the input of the keyboard
	 * @throws Exception
	 */
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
	
	/**
	 * Returns the KeyCode of the LastKey
	 * @return
	 */
    public static int getKeyCode(){return LastKey;}
	/**
	 * Returns the value of the LastKey
	 * @return
	 */
    public static String getKeyValue(){
		return new String(new char[] {LastChar});}
	/**
	 * Returns the character of the LastKey
	 * @return
	 */
	public static char getKeyCharacter() {return LastChar;}
	/**
	 * Returns the type of the LastKey
	 * @return
	 */
	public static KeyType getKeyType() {return LastKeyType;}

	/**
	 * Cleans the keyboard and lastKey is now null
	 */
	public static void Clear(){
		LastKey = 0;
		LastChar = ESCAPECHAR;
		LastKeyType = null;
		pos = 0;
	}

	/**
	 * Returns the keytype as a String
	 * @param keyString
	 * @return
	 */
	public static KeyType KeyTypeByString(String keyString) {
		for (KeyType keyType : KeyType.values()) {
			if (keyType.name().equalsIgnoreCase(keyString)) {
				return keyType;
			}
		}
		return null;
    }

	/**
	 * Returns if the type of the last key equals keyValue
	 * @param keyValue
	 * @return
	 */
	public static boolean IsLastKeyOfType(String keyType){
		return getKeyType() == KeyTypeByString(keyType);
	}

	/**
	 * Returns if the last key is equals to keyValue
	 * @param keyValue
	 * @return
	 */
	public static boolean IsLastKeyValue(String keyValue){
		return getKeyValue().toLowerCase().equals(keyValue.toLowerCase());
	}

	/**
	 * This function reads user input from the keyboard and returns it as a string.
	 * 
	 * @param prefix The prefix parameter is a string that will be printed before the user input. It can
	 * be used to prompt the user for input or provide context for the expected input.
	 * @return The String result 
	 */
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

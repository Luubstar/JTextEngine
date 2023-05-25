package TextEngine;
 
import java.io.IOException;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

public class Keyboard  {

	public static final char ESCAPECHAR = ' ';
    private static int LastKey = 0;
    public static int pos = 0;
	private static char LastChar;
	private static KeyType LastKeyType;
	private static boolean Shift;
	private static boolean Control;
	private static boolean Alt;

	static TerminalScreen screen;
	static Terminal terminal;

	/**
	 * Starts the keyboard system
	 */
	public static void Start(Terminal t, TerminalScreen s){
		terminal = t;
		screen = s;
	}

	/**
	 * Reads the input of the keyboard
	 * @throws IOException
	 */
	public static void DetectInput() throws IOException{
		KeyStroke keyStroke = screen.pollInput();
		if (keyStroke != null) {
			Shift = keyStroke.isShiftDown();
			Control = keyStroke.isCtrlDown();
			Alt = keyStroke.isAltDown();
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

	public static boolean isShiftPressed(){return Shift;}
	public static boolean isControlPressed(){return Control;}
	public static boolean isAltPressed(){return Alt;}

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
	 * This function reads user input from the keyboard and returns it as a string. ¿Bugs?
	 * 
	 * @param prefix The prefix parameter is a string that will be printed before the user input. It can
	 * be used to prompt the user for input or provide context for the expected input.
	 * @return The String result 
	 */
	public static String Scanner(String prefix) throws InterruptedException, IOException{
		String res = "";
		String laststring = "------------------------------";
		while(getKeyType() != KeyTypeByString("Enter")){

			if (IsLastKeyOfType("Character")){res += getKeyValue();}
			else if (IsLastKeyOfType("Backspace") && res.length() > 0){res = res.substring(0,res.length()-1);}
			else if (IsLastKeyOfType("Escape")){throw new InterruptedException("Action interrupted by user");}
			if (laststring != res) {
				laststring = res;
				Engine.clearConsole();
				Engine.print(prefix + res);
			}

			Thread.sleep(Engine.frameTime());
			Clear();
			DetectInput();
		}
		Clear();
		return res;
	}

}

package Engine;
 
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.unix.X11.Window;


public class Keyboard implements NativeKeyListener {
    private static int LastKey = 0;
    public static int pos = 0;
	private static char LastChar;

	public static void Start() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new Keyboard());
	}

	public void nativeKeyPressed(NativeKeyEvent e) {

        LastKey = e.getKeyCode();
		pos = 0;
	}

    public void nativeKeyTyped(NativeKeyEvent e) {
        
		LastChar = e.getKeyChar();
    }

    public static int getKeyCode(){return LastKey;}
    public static String getKeyValue(){return NativeKeyEvent.getKeyText(LastKey);}
	public static char getKeyCharacter() {return LastChar;}
	public static void Clear(){
		LastKey = 0;
		pos = 0;
	}

}
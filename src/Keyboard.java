 
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class Keyboard implements NativeKeyListener {
    public static int LastKey = 0;
    public static int pos = 0;

	public void nativeKeyPressed(NativeKeyEvent e) {
        LastKey = e.getKeyCode();
		pos = 0;
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            		try {
                		GlobalScreen.unregisterNativeHook();
            		} catch (NativeHookException nativeHookException) {
                		nativeHookException.printStackTrace();
            		}
        	}
	}

    public static int getKeyCode(){return LastKey;}
    public static String getKeyValue(){return NativeKeyEvent.getKeyText(LastKey);}
	public static void Clear(){
		LastKey = 0;
		pos = 0;}

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
}

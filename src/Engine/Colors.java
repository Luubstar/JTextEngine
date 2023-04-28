package Engine;
 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Usage:
 * <li>String msg = Colors.Red.and(Colors.BgYellow).format("Hello %s", name)</li>
 * <li>String msg = Colors.Blink.colorize("BOOM!")</li>
 * 
 * Or, if you are adverse to that, you can use the constants directly:
 * <li>String msg = new Colors(Colors.ITALIC, Colors.GREEN).format("Green money")</li>
 * Or, even:
 * <li>String msg = Colors.BLUE + "scientific"</li>
 * 
 * NOTE: Nothing stops you from combining multiple FG colors or BG colors, 
 *       but only the last one will display.
 * 
 * (Thx dain ;))
 *
 */
public final class Colors {

	// Color code strings from:
	// http://www.topmudsites.com/forums/mud-coding/413-java-Colors.html
	public static final String	SANE				= "\u001B[0m";

	public static final String	HIGH_INTENSITY		= "\u001B[1m";
	public static final String	LOW_INTENSITY		= "\u001B[2m";

	public static final String  BOLD 				= "\u001B[1m";
	public static final String	ITALIC				= "\u001B[3m";
	public static final String	UNDERLINE			= "\u001B[4m";
	public static final String	BLINK				= "\u001B[5m";
	public static final String	RAPID_BLINK			= "\u001B[6m";
	public static final String	REVERSE_VIDEO		= "\u001B[7m";
	public static final String	INVISIBLE_TEXT		= "\u001B[8m";

	public static final String	BLACK				= "\u001B[30m";
	public static final String	RED					= "\u001B[31m";
	public static final String	GREEN				= "\u001B[32m";
	public static final String	YELLOW				= "\u001B[33m";
	public static final String	BLUE				= "\u001B[34m";
	public static final String	MAGENTA				= "\u001B[35m";
	public static final String	CYAN				= "\u001B[36m";
	public static final String	WHITE				= "\u001B[37m";

	public static final String	BACKGROUND_BLACK	= "\u001B[40m";
	public static final String	BACKGROUND_RED		= "\u001B[41m";
	public static final String	BACKGROUND_GREEN	= "\u001B[42m";
	public static final String	BACKGROUND_YELLOW	= "\u001B[43m";
	public static final String	BACKGROUND_BLUE		= "\u001B[44m";
	public static final String	BACKGROUND_MAGENTA	= "\u001B[45m";
	public static final String	BACKGROUND_CYAN		= "\u001B[46m";
	public static final String	BACKGROUND_WHITE	= "\u001B[47m";

	public static final String[] ListOfColors = {SANE, HIGH_INTENSITY,LOW_INTENSITY,BOLD,ITALIC,UNDERLINE,BLINK,RAPID_BLINK,REVERSE_VIDEO,INVISIBLE_TEXT,BLACK,RED,GREEN,YELLOW, BLUE, MAGENTA, CYAN, WHITE, BACKGROUND_BLACK,BACKGROUND_RED,BACKGROUND_GREEN,BACKGROUND_YELLOW,BACKGROUND_BLUE,BACKGROUND_MAGENTA,BACKGROUND_CYAN,BACKGROUND_WHITE};

	public static final Colors HighIntensity = new Colors(HIGH_INTENSITY);
	public static final Colors Bold = HighIntensity;
	public static final Colors LowIntensity = new Colors(LOW_INTENSITY);
	public static final Colors Normal = LowIntensity;
	
	public static final Colors Italic = new Colors(ITALIC);
	public static final Colors Underline = new Colors(UNDERLINE);
	public static final Colors Blink = new Colors(BLINK);
	public static final Colors RapidBlink = new Colors(RAPID_BLINK);
	
	public static final Colors Black = new Colors(BLACK);
	public static final Colors Red = new Colors(RED);
	public static final Colors Green = new Colors(GREEN);
	public static final Colors Yellow = new Colors(YELLOW);
	public static final Colors Blue = new Colors(BLUE);
	public static final Colors Magenta = new Colors(MAGENTA);
	public static final Colors Cyan = new Colors(CYAN);
	public static final Colors White = new Colors(WHITE);
	
	public static final Colors BgBlack = new Colors(BACKGROUND_BLACK);
	public static final Colors BgRed = new Colors(BACKGROUND_RED);
	public static final Colors BgGreen = new Colors(BACKGROUND_GREEN);
	public static final Colors BgYellow = new Colors(BACKGROUND_YELLOW);
	public static final Colors BgBlue = new Colors(BACKGROUND_BLUE);
	public static final Colors BgMagenta = new Colors(BACKGROUND_MAGENTA);
	public static final Colors BgCyan = new Colors(BACKGROUND_CYAN);
	public static final Colors BgWhite = new Colors(BACKGROUND_WHITE);
	
	final private String[] codes;
	final private String codes_str; 
	public Colors(String... codes) {
		this.codes = codes;
		String _codes_str = "";
		for (String code : codes) {
			_codes_str += code;
		}
		codes_str = _codes_str;
	}
	
	public Colors and(Colors other) {
		List<String> both = new ArrayList<String>();
	    Collections.addAll(both, codes);
	    Collections.addAll(both, other.codes);
		return new Colors(both.toArray(new String[] {}));
	}

	public String colorize(String original) {
		return codes_str + original + SANE;
	}


	public String colorizeNoSane(String original) {
		return codes_str + original;
	}
	
	public String format(String template, Object... args) {
		return colorize(String.format(template, args));
	}

	public static String clearColor(String input){
		for (String cadena: ListOfColors){
			if (input.contains(cadena)){input = input.replace(cadena, "");}
		}
		return input;
	}

	public static String getColor(String input){
		for (String cadena: ListOfColors){
			if (input.contains(cadena)){return cadena;}
		}
		return "";
	}
}
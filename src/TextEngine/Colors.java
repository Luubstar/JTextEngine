package TextEngine;
 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public final class Colors implements Serializable{

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
	
	private String[] codes;
	private String codes_str; 

	public Colors(String... ANSICodes) {
		this.codes = ANSICodes;
		String _codes_str = "";
		for (String code : codes) {
			_codes_str += code;
		}
		codes_str = _codes_str;
	}

	/**
	 * Creates a color with RGB values (between 0 and 255)
	 * @param R
	 * @param G
	 * @param B
	 */
	private Colors(int T, int R, int G, int B){
		if (R < 0){R = 0;}
		if (G < 0){G = 0;}
		if (B < 0) {B = 0;}
		if (R > 255){R = 255;}
		if (G > 255){G = 255;}
		if (B > 255){B = 255;}
		if (T == 1){
			this.codes = new String[]{"\033[38;2;"+R+";"+G+";"+B+"m"}; 
			this.codes_str = this.codes[0];
		}
		else{
			this.codes = new String[]{"\033[48;2;"+R+";"+G+";"+B+"m"}; 
			this.codes_str = this.codes[0];
		}
	}

	/**
	 * Creates a text color using the RGB parameters (may be uncompatible with all terminals)
	 * @param R
	 * @param G
	 * @param B
	 * @return
	 */
	public static Colors CreateTextColor(int R, int G, int B){return new Colors(1, R, G, B);}

	/**
	 * Creates a background color using the RGB parameters (may be uncompatible with all terminals)
	 * @param R
	 * @param G
	 * @param B
	 * @return
	 */
	public static Colors CreateBackgroundColor(int R, int G, int B){return new Colors(0, R, G, B);}
	
	/**
	 * Adds a color (for use multiple when colorize)
	 * @param other
	 * @return
	 */
	public Colors and(Colors other) {
		List<String> both = new ArrayList<String>();
	    Collections.addAll(both, codes);
	    Collections.addAll(both, other.codes);
		return new Colors(both.toArray(new String[] {}));
	}

	/**
	 * Colorize the string
	 * @param original
	 * @return
	 */
	public String colorize(String original) {
		return codes_str + original + SANE;
	}


	/**
	 * Colorize the string but without stopping the color at the end
	 * @param original
	 * @return
	 */
	public String colorizeNoSane(String original) {
		return codes_str + original;
	}
	
	/**
	 * Formats the string and then gives it colors
	 * @param template
	 * @param args
	 * @return
	 */
	public String format(String template, Object... args) {
		return colorize(String.format(template, args));
	}

	/**
	 * Cleans the color from the string
	 * @param input
	 * @return
	 */
	public static String clearColor(String input){
		Pattern patron = Pattern.compile("\\\\u.*?m");
		Matcher matcher = patron.matcher(input);
		return matcher.replaceAll("");	
	}

	public static String getColor(String input){
		Pattern patron = Pattern.compile("\\\\u.*?m");
		Matcher matcher = patron.matcher(input);
		return matcher.toString();
	}
}
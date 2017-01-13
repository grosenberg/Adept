package net.certiv.adept.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.LexerNoViableAltException;

/**
 * Base class for the Lexer. Combines functionality for <br>
 * <ul>
 * <li>lexer error strategy</li>
 * <li>various helper routines</li>
 * <ul>
 * 
 * @author Gbr
 * 
 */
public abstract class LexerErrorStrategy extends Lexer {

	// ///// Error strategy /////////////////////////////
	public LexerErrorStrategy(CharStream input) {
		super(input);
	}

	public void recover(LexerNoViableAltException e) {
		// throw new RuntimeException(e); // Bail out
		super.recover(e);
	}

	// ///// Parse stream management ////////////////////

}

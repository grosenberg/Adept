package net.certiv.adept.model.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.aspect.Line.LinePart;

/**
 * Line aspect is used to describe a feature in terms of the line of occurrence including the margin
 * context of the line.
 */
public class Line extends Aspect<Line, LinePart> {

	@Expose private boolean isFirst;	// line, ignoring non-visible and comment tokens
	@Expose private boolean isLast;

	@Expose private int blanksAbove;	// num blank lines above
	@Expose private int blanksBelow;	// num blank lines below

	@Expose private boolean atLineBeg;	// of line, ignoring non-visible and comment tokens
	@Expose private boolean atLineEnd;

	@Expose private int indented;		// absolute line indent
	@Expose private int reldents;		// relative line indent

	@Expose private int wsLead;			// num leading space char equivs
	@Expose private int wsTrail;		// num trailing space char equivs

	@Expose private int tokenOff;		// token offset in line, ignoring non-visible tokens and comments
	@Expose private int tokenCnt;		// count of tokens in line, ignoring non-visible tokens and comments

	@Expose private int commentBefore;	// follows a comment, ignoring non-visible tokens
	@Expose private int commentAfter;	// leads a comment, ignoring non-visible tokens

	public static enum LinePart {
		INDENTED,
		REL_INDENT,
		REL_POSITION,
		ISOLATION;
	}

	private Map<LinePart, SimilarityCalc> calcs;

	@Override
	public List<LinePart> getAspectParts() {
		return Arrays.asList(LinePart.values());
	}

	@Override
	public double similarity(Feature other, LinePart part) {
		 SimilarityCalc calc = calcs.get(part);
		
		return 0;
	}
}

package net.certiv.adept.format;

public class LineBreakProcessor extends AbstractProcessor {

	public LineBreakProcessor(FormatterOps ops) {
		super(ops);
	}

	// TODO
	public void breakLongLines() {
		ops.buildLinesIndexes();
	}
}

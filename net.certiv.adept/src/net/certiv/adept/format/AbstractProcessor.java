package net.certiv.adept.format;

public abstract class AbstractProcessor {

	protected FormatterOps ops;

	public AbstractProcessor(FormatterOps ops) {
		this.ops = ops;
	}

	public void dispose() {
		ops = null;
	}
}

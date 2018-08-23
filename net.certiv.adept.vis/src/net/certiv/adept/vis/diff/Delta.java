package net.certiv.adept.vis.diff;

public class Delta {

	private Chunk source;
	private Chunk result;
	private Action action;
	private Transform transform;

	public Delta(Chunk source, Chunk result) {
		this.source = source;
		this.result = result;

		if (source.getLen() > 0 && result.getLen() == 0) {
			action = Action.DEL;
		} else if (source.getLen() == 0 && result.getLen() > 0) {
			action = Action.ADD;
		} else {
			action = Action.MOD;
		}
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public Action getAction() {
		return action;
	}

	public Chunk getSource() {
		return source;
	}

	public Chunk getResult() {
		return result;
	}

	public boolean isAdd() {
		return action == Action.ADD;
	}

	public boolean isDel() {
		return action == Action.DEL;
	}

	public boolean isMod() {
		return action == Action.MOD;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Delta)) return false;
		Delta d = (Delta) o;
		if (transform != d.transform) return false;
		if (!source.equals(d.source) || !result.equals(d.result)) return false;
		return true;
	}

	@Override
	public String toString() {
		String[] txt = transform.getText(source, result, true);
		return String.format("%s %s (%s) -> %s (%s)", action, source, txt[0], result, txt[1]);
	}
}

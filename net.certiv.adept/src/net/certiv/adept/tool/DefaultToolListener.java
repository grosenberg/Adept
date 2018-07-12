package net.certiv.adept.tool;

import net.certiv.adept.IToolListener;
import net.certiv.adept.util.Log;

public class DefaultToolListener implements IToolListener {

	private Level level;

	public DefaultToolListener() {
		this.level = Level.INFO;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public void info(Object source, String msg) {
		info(source, msg, null);
	}

	@Override
	public void warn(Object source, String msg) {
		warn(source, msg, null);
	}

	@Override
	public void error(Object source, String msg) {
		error(source, msg, null);
	}

	@Override
	public void info(Object source, String msg, Throwable e) {
		if (skip(Level.INFO)) return;
		Log.info(source, msg, e);
	}

	@Override
	public void warn(Object source, String msg, Throwable e) {
		if (skip(Level.WARN)) return;
		Log.info(source, msg, e);
	}

	@Override
	public void error(Object source, String msg, Throwable e) {
		if (skip(Level.ERROR)) return;
		Log.error(source, msg, e);
	}

	private boolean skip(Level target) {
		return level.ordinal() > target.ordinal() ? true : false;
	}
}

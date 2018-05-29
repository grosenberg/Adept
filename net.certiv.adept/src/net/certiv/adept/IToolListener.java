package net.certiv.adept;

public interface IToolListener {

	void info(String msg);

	void warn(String msg);

	void error(String msg);

	void info(Object source, String msg, Throwable e);

	void warn(Object source, String msg, Throwable e);

	void error(Object source, String msg, Throwable e);

	/** Return first non ErrorManager code location for generating messages */
	default StackTraceElement getLocation(Object source, Throwable e) {
		if (source == null || e == null) return null;
		String name = source.getClass().getName();
		StackTraceElement[] stack = e.getStackTrace();
		int idx = 0;
		for (; idx < stack.length; idx++) {
			StackTraceElement elem = stack[idx];
			if (elem.getClassName().equals(name)) break;
		}
		StackTraceElement location = stack[idx];
		return location;
	}
}

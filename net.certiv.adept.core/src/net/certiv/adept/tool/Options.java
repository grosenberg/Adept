package net.certiv.adept.tool;

import java.util.ArrayList;
import java.util.List;

import net.certiv.adept.Tool;
import net.certiv.adept.util.Reflect;

public class Options {

	public static enum OptionType {
		BOOL,
		INT,
		STRING
	}

	public String fieldName;
	public String name;
	public OptionType argType;
	public String description;

	private static List<String> remainder;

	public Options(String fieldName, String name, String description) {
		this(fieldName, name, OptionType.BOOL, description);
	}

	public Options(String fieldName, String name, OptionType argType, String description) {
		this.fieldName = fieldName;
		this.name = name;
		this.argType = argType;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static boolean process(Tool tool, Options[] optionDefs, String[] args) {
		remainder = new ArrayList<String>();

		boolean ok = true;
		for (int idx = 0; idx < args.length; idx++) {
			String arg = args[idx];
			if (arg.charAt(0) != '-') { // must be a file name
				if (!remainder.contains(arg)) remainder.add(arg);
				continue;
			}

			boolean found = false;
			for (Options o : optionDefs) {
				if (arg.equals(o.name)) {
					found = true;
					switch (o.argType) {
						case STRING:
							idx++;
							String argValue = args[idx];
							Reflect.set(tool, o.fieldName, argValue);
							break;
						case BOOL:
							Boolean value = (Boolean) Reflect.get(tool, o.fieldName);
							if (value == null) value = false;
							Reflect.set(tool, o.fieldName, !value);
							break;
						case INT:
							idx++;
							int val;
							try {
								val = Integer.valueOf(args[idx]);
							} catch (NumberFormatException e) {
								val = 4;
							}
							Reflect.set(tool, o.fieldName, val);
							break;
					}
				}
			}
			if (!found) {
				Tool.errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, arg);
				ok = false;
			}
		}
		return ok;
	}

	public static List<String> getRemainder() {
		return remainder;
	}
}

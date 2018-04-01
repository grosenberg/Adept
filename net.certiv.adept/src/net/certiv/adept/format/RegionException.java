package net.certiv.adept.format;

public class RegionException extends RuntimeException {

	private Region o1;
	private Region o2;

	public RegionException(Region o1, Region o2, String msg) {
		super(msg);
		this.o1 = o1;
		this.o2 = o2;
	}

	@Override
	public String getMessage() {
		return String.format("%s: %s %s", super.getMessage(), o1, o2);
	}
}

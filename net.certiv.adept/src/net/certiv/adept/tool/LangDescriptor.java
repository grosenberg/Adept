package net.certiv.adept.tool;

public class LangDescriptor {

	public String name;					// lang identifier name
	public String ext;					// corpus file extension
	public String corpusRoot;			// location of the corpus
	public int tabWidth;				// of indents in the corpus files

	public LangDescriptor() {}

	public LangDescriptor(String name, String ext, String corpusRoot, int tabWidth) {
		this.name = name;
		this.ext = ext;
		this.corpusRoot = corpusRoot;
		this.tabWidth = tabWidth;
	}
}

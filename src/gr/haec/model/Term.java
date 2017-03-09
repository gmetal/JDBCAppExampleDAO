package gr.haec.model;

public class Term {

	// Constants
	public static final int INVALID_ID = -1;
	public static final int DEFAULT_TERM_GROUP = 0;

	// Attributes
	protected int id;
	protected String termName;
	protected String termSlug;
	protected int termGroup;

	// Constructor
	public Term() {
		this.id = INVALID_ID;
		this.termName = "";
		this.termSlug = "";
		this.termGroup = DEFAULT_TERM_GROUP;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getTermSlug() {
		return termSlug;
	}

	public void setTermSlug(String termSlug) {
		this.termSlug = termSlug;
	}

	public int getTermGroup() {
		return termGroup;
	}

	public void setTermGroup(int termGroup) {
		this.termGroup = termGroup;
	}

	public String toString() {
		return "Term[id =  " + id + ", Term Name = " + termName + ", Term Slug = " + termSlug + ", Term Group = "
				+ termGroup + "]";
	}
}

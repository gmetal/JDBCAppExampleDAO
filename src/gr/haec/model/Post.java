package gr.haec.model;

// Post DTO
public class Post {

	// Constants
	public static final int INVALID_ID = -1;

	// Attributes
	private int id;
	private String postTitle;
	private String postAuthor;

	// Constructor
	public Post() {
		this.id = INVALID_ID;
		this.postAuthor = "";
		this.postTitle = "";
	}

	// Properties
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostAuthor() {
		return postAuthor;
	}

	public void setPostAuthor(String postAuthor) {
		this.postAuthor = postAuthor;
	}

	public String toString() {
		return "Post[id =  " + id + ", Post Author = " + postAuthor + ", Post Title = " + postTitle + "]";
	}
}

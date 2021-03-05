import java.util.ArrayList;

public class Post extends Event {
	private String Post;
	private ArrayList<Comment> Comments;

	public Post(int teacherID, String post) {
		super(teacherID, -1);
		Post = post;
		Comments = new ArrayList<Comment>();
	}

	public Post(int teacherID, int ID, String post, ArrayList<Comment> comments) {
		super(teacherID, -1, ID);
		Post = post;
		Comments = comments;
	}

	public String getPost() {
		return Post;
	}

	public void setPost(String post) {
		Post = post;
	}

	public ArrayList<Comment> getComments() {
		return Comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		Comments = comments;
	}

	public String postt_to_String() {
		String submitteds = "-1";
		for (int i = 0; i < Comments.size(); i++) {
			if (submitteds.equals("-1")) {
				submitteds = "";
			}
			submitteds = submitteds + Comments.get(i).getID() + "+" + Comments.get(i).getComment().replace(" ", "=")
					+ "-";
		}
		String s = getPost().replace(" ", "_");
		String info = getOwnerID() + " " + getID() + " " + s + " " + submitteds;
		return info;
	}
}

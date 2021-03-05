
public class Message extends Event {
	private String content;

	public Message(int ownerID, int responsibleID, String content) {
		super(ownerID, responsibleID);
		this.content = content;
	}

	public Message(int ownerID, int responsibleID, int ID, String content) {
		super(ownerID, responsibleID, ID);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		String result = "";
		result = getOwnerID() + " " + getResponsibleID() + " " + getID() + " -" + getContent();
		return result;
	}
}

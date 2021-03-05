
public class Announcement extends Event {
	private String announcement;

	public Announcement(int teacherID, int courseID) {
		super(teacherID, courseID);
		announcement = null;
	}

	public Announcement(int teacherID, int courseID, int ID, String announcement) {
		super(teacherID, courseID, ID);
		this.announcement = announcement;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String toString() {
		String result = "";
		
		result=getOwnerID()+" "+getResponsibleID()+" "+getID()+" "+getAnnouncement();
		
		return result;
	}
}

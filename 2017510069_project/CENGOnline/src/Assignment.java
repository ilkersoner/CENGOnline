import java.util.HashMap;
import java.util.Map;

public class Assignment extends Event {
	private String explanation;
	private Map<Integer, String> submitted_Works;

	public Assignment(int ownerID, int courseID) {
		super(ownerID, courseID);
		explanation = null;
		submitted_Works = new HashMap<Integer, String>();
	}

	public Assignment(int ownerID, int courseID, int ID, String explanation, Map<Integer, String> submitted_Works) {
		super(ownerID, courseID, ID);
		this.explanation = explanation;
		this.submitted_Works = submitted_Works;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public Map<Integer, String> getSubmitted_Works() {
		return submitted_Works;
	}

	public void setSubmitted_Works(Map<Integer, String> submitted_Works) {
		this.submitted_Works = submitted_Works;
	}

	public String submitted_Works_to_String() {
		String submitteds = "-1";
		for (Map.Entry<Integer, String> entry : submitted_Works.entrySet()) {
			if (submitteds.equals("-1")) {
				submitteds = "";
			}
			submitteds = submitteds + entry.getKey() + "+" + entry.getValue() + "-";

		}

		String info = getOwnerID() + " " + getResponsibleID() + " " + getID() + " " + getExplanation() + " "
				+ submitteds;
		return info;
	}
}

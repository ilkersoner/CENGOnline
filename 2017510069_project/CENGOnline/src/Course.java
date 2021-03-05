import java.util.ArrayList;

public class Course {
	private int teacherID;
	private ArrayList<Integer> Assignments;
	private ArrayList<Integer> Students;
	private ArrayList<Integer> Announcements;
	static int ID_Count;
	private int ID;
	private String name;

	public Course(int teacherID, String name) {
		this.teacherID = teacherID;
		ID = ID_Count;
		ID_Count++;
		this.name = name;
		Assignments = new ArrayList<Integer>();
		Students = new ArrayList<Integer>();
		Announcements = new ArrayList<Integer>();

	}

	public Course(String name, int ID, int teacherID, ArrayList<Integer> assignments, ArrayList<Integer> students,
			ArrayList<Integer> announcements) {
		this.name = name;
		this.ID = ID;
		this.teacherID = teacherID;
		this.Assignments = assignments;
		this.Students = students;
		this.Announcements = announcements;

	}

	public int getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}

	public ArrayList<Integer> getAssignments() {
		return Assignments;
	}

	public void setAssignments(ArrayList<Integer> assignments) {
		Assignments = assignments;
	}

	public ArrayList<Integer> getStudents() {
		return Students;
	}

	public void setStudents(ArrayList<Integer> students) {
		Students = students;
	}

	public ArrayList<Integer> getAnnouncements() {
		return Announcements;
	}

	public void setAnnouncements(ArrayList<Integer> announcements) {
		Announcements = announcements;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String assignments_to_String() {
		String assignments = "-1";
		for (int i = 0; i < Assignments.size(); i++) {
			if (assignments.equals("-1"))
				assignments = "";
			if (i == Assignments.size() - 1)
				assignments = assignments + Integer.toString(Assignments.get(i));
			else
				assignments = assignments + Assignments.get(i) + "/";

		}
		return assignments;
	}

	public String students_to_String() {
		String students = "-1";
		for (int i = 0; i < Students.size(); i++) {
			if (students.equals("-1"))
				students = "";
			if (i == Students.size() - 1)
				students = students + Integer.toString(Students.get(i));
			else
				students = students + Students.get(i) + "-";

		}
		return students;
	}

	public String announcements_to_String() {
		String announcements = "-1";
		for (int i = 0; i < Announcements.size(); i++) {
			if (announcements.equals("-1"))
				announcements = "";
			if (i == Announcements.size() - 1)
				announcements = announcements + Integer.toString(Announcements.get(i));
			else
				announcements = announcements + Announcements.get(i) + "+";
		}

		return announcements;
	}
}

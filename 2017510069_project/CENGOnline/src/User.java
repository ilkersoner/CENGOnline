import java.util.ArrayList;

public abstract class User {
	static int ID_Count = 0;
	private String name;
	private String surname;
	private String type;
	private int ID;
	private String username;
	private String password;
	private String status;
	private ArrayList<Integer> Courses;
	private ArrayList<Integer> Messages;

	static final String ACTIVE = "0";
	static final String DELETED = "1";

	static final String TEACHER = "Teacher";
	static final String STUDENT = "Student";

	public User(String name, String surname, String type, String username, String password) {
		this.name = name;
		this.surname = surname;
		this.type = type;
		this.username = username;
		this.password = password;
		this.status = ACTIVE;
		ID = ID_Count;
		ID_Count++;
		Courses = new ArrayList<Integer>();
		Messages = new ArrayList<Integer>();
	}

	public User(String name, String surname, String type, String username, String password, int ID, String status,
			ArrayList<Integer> courses, ArrayList<Integer> messages) {
		this.name = name;
		this.surname = surname;
		this.type = type;
		this.username = username;
		this.password = password;
		this.status = status;
		this.ID = ID;
		Courses = courses;
		Messages = messages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Integer> getCourses() {
		return Courses;
	}

	public void setCourses(ArrayList<Integer> courses) {
		Courses = courses;
	}

	public ArrayList<Integer> getMessages() {
		return Messages;
	}

	public void setMessages(ArrayList<Integer> messages) {
		Messages = messages;
	}

	public String courses_to_String() {
		String courses = "";
		for (int i = 0; i < Courses.size(); i++) {
			if (i == Courses.size() - 1)
				courses = courses + Integer.toString(Courses.get(i));
			else
				courses = courses + Courses.get(i)+ "+";
		}
		return courses;
	}

	public String messages_to_String() {
		String messages = "";
		for (int i = 0; i < Messages.size(); i++) {
			if (i == Messages.size() - 1)
				messages = messages + Integer.toString(Messages.get(i));
			else
				messages = messages + Messages.get(i) + "-";

		}
		return messages;
	}
}

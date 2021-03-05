import java.util.ArrayList;

public class Teacher extends User {

	public Teacher(String name, String surname, String username, String password) {
		super(name, surname, TEACHER, username, password);
	}

	public Teacher(String name, String surname, String type, String username, String password, int ID, String status,
			ArrayList<Integer> courses, ArrayList<Integer> messages) {
		super(name, surname, type, username, password, ID, status, courses, messages);
	}

}

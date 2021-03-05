import java.util.ArrayList;

public class Student extends User {

	public Student(String name, String surname, String username, String password) {
		super(name, surname, STUDENT, username, password);
	}

	public Student(String name, String surname, String type, String username, String password, int ID, String status,
			ArrayList<Integer> courses, ArrayList<Integer> messages) {
		super(name, surname, type, username, password, ID, status, courses, messages);
	}

}

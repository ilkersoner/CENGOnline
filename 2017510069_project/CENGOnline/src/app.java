import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class app {
	public static ArrayList<User> Users = new ArrayList<User>();
	public static ArrayList<Course> Courses = new ArrayList<Course>();
	public static ArrayList<Message> Message_Logs = new ArrayList<Message>();
	public static ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	public static ArrayList<Announcement> Announcements = new ArrayList<Announcement>();
	public static ArrayList<Post> Posts = new ArrayList<Post>();

	public static void create_DB_File(String file) throws IOException {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			fr.close();
		} catch (FileNotFoundException fe) {
			FileWriter fw = new FileWriter(file);
			System.out.println(file + " created.");
			fw.close();
		}
	}

	public static void init_DB() throws IOException {
		create_DB_File("Accounts.txt");
		create_DB_File("Courses.txt");
		create_DB_File("Assignments.txt");
		create_DB_File("Announcements.txt");
		create_DB_File("Message_Logs.txt");
		create_DB_File("Posts.txt");
		fill_Lists();
	}

	public static User string_to_User(String u) {
		User result;
		String[] parts = u.split("\\s+");
		ArrayList<Integer> courses = new ArrayList<Integer>();
		ArrayList<Integer> messages = new ArrayList<Integer>();

		if (parts.length == 9) {
			String[] message = parts[8].split("-");
			if (!message[0].equalsIgnoreCase("")) {
				for (int i = 0; i < message.length; i++) {
					messages.add(Integer.parseInt(message[i]));
				}
			}
		}

		if (parts[2].equals(User.TEACHER)) {
			for (int i = 0; i < Courses.size(); i++) {
				if (Courses.get(i).getTeacherID() == Integer.parseInt(parts[3])) {
					courses.add(Courses.get(i).getID());
				}
			}

			result = new Teacher(parts[0], parts[1], parts[2], parts[5], parts[6], Integer.parseInt(parts[3]), parts[4],
					courses, messages);

		} else {
			for (int i = 0; i < Courses.size(); i++) {
				for (int j = 0; j < Courses.get(i).getStudents().size(); j++) {
					if (Courses.get(i).getStudents().size() != 0
							&& Courses.get(i).getStudents().get(j) == Integer.parseInt(parts[3])) {
						courses.add(Courses.get(i).getID());
					}
				}
			}
			result = new Student(parts[0], parts[1], parts[2], parts[5], parts[6], Integer.parseInt(parts[3]), parts[4],
					courses, messages);
		}

		return result;
	}

	public static String user_to_String(User u) {
		String result;

		result = u.getName() + " " + u.getSurname() + " " + u.getType() + " " + u.getID() + " " + u.getStatus() + " "
				+ u.getUsername() + " " + u.getPassword() + " " + u.courses_to_String() + " " + u.messages_to_String();

		return result;
	}

	public static String message_to_String(Message a) {
		String result;

		result = a.toString();

		return result;
	}

	public static Message string_to_Message(String a) {
		Message result = null;
		String[] parts = a.split("-");
		String[] parts2 = parts[0].split("\\s+");

		result = new Message(Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]), Integer.parseInt(parts2[2]),
				parts[1]);

		return result;
	}

	public static Assignment string_to_Assignment(String a) {
		Assignment result = null;
		String[] parts = a.split("\\s+");
		Map<Integer, String> submitted_Works = new HashMap<Integer, String>();

		if (!parts[4].equals(("-1"))) {
			String[] submitteds = parts[4].split("-");
			if (!submitteds[0].equalsIgnoreCase("")) {
				for (int i = 0; i < submitteds.length; i = i + 2) {
					String[] submitteds2 = submitteds[i].split("\\+");
					submitted_Works.put(Integer.parseInt(submitteds2[0]), submitteds2[1]);
				}
			}
		}

		result = new Assignment(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
				parts[3], submitted_Works);

		return result;
	}

	public static Post string_to_Post(String a) {
		Post result = null;
		String[] parts = a.split("\\s+");
		ArrayList<Comment> comments = new ArrayList<Comment>();

		if (!parts[3].equals(("-1"))) {
			String[] submitteds = parts[3].split("-");
			if (!submitteds[0].equalsIgnoreCase("")) {
				for (int i = 0; i < submitteds.length; i++) {
					String[] submitteds2 = submitteds[i].split("\\+");
					Comment c = new Comment(Integer.parseInt(submitteds2[0]), submitteds2[1].replace("=", " "));
					comments.add(c);
				}
			}
		}

		result = new Post(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].replace("_", " "), comments);

		return result;
	}

	public static Announcement string_to_Announcement(String a) {
		Announcement result = null;
		String[] parts = a.split("\\s+");

		result = new Announcement(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]),
				parts[3]);

		return result;
	}

	public static String assignment_to_String(Assignment a) {
		String result;

		result = a.submitted_Works_to_String();

		return result;
	}

	public static String announcement_to_String(Announcement a) {
		String result;

		result = a.toString();

		return result;
	}

	public static String post_to_String(Post p) {
		String result;

		result = p.postt_to_String();
		return result;
	}

	public static void fill_Lists() throws IOException {
		// Announcements
		BufferedReader br = new BufferedReader(new FileReader(new File("Announcements.txt")));
		String line;
		line = br.readLine();
		if (line != null) {
			Announcement.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Announcements.add(string_to_Announcement(line));
			}
		}
		br.close();
		// Messages
		br = new BufferedReader(new FileReader(new File("Message_Logs.txt")));
		line = br.readLine();
		if (line != null) {
			Message.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Message_Logs.add(string_to_Message(line));
			}
		}
		br.close();
		// Posts
		br = new BufferedReader(new FileReader(new File("Posts.txt")));
		line = br.readLine();
		if (line != null) {
			Post.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Posts.add(string_to_Post(line));
			}
		}
		br.close();
		// Assignments
		br = new BufferedReader(new FileReader(new File("Assignments.txt")));
		line = br.readLine();
		if (line != null) {
			Assignment.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Assignments.add(string_to_Assignment(line));
			}
		}
		br.close();
		// Courses
		br = new BufferedReader(new FileReader(new File("Courses.txt")));
		line = br.readLine();
		if (line != null) {
			Course.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Courses.add(string_to_Course(line));
			}
		}
		br.close();
		// Users
		br = new BufferedReader(new FileReader(new File("Accounts.txt")));
		line = br.readLine();
		if (line != null) {
			User.ID_Count = Integer.parseInt(line);
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				Users.add(string_to_User(line));
			}
		}
		br.close();
	}

	public static void update_DB() throws IOException {
		// Accounts.txt
		FileWriter fw = new FileWriter("Accounts.txt");
		String Info = "Name Surname Type ID Status Username Password Courses Messages\r\n";
		String static_variable = Integer.toString(User.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Users.size(); i++) {
			String current = user_to_String(Users.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// Courses.txt
		fw = new FileWriter("Courses.txt");
		Info = "Name ID Teacher Assignments Students Announcements\r\n";
		static_variable = Integer.toString(Course.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Courses.size(); i++) {
			String current = course_to_String(Courses.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// Assignment.txt
		fw = new FileWriter("Assignments.txt");
		Info = "TeacherID CourseID ID Explanation Works\r\n";
		static_variable = Integer.toString(Assignment.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Assignments.size(); i++) {
			String current = assignment_to_String(Assignments.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// Announcement.txt
		fw = new FileWriter("Announcements.txt");
		Info = "TeacherID CourseID ID Announcement\r\n";
		static_variable = Integer.toString(Announcement.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Announcements.size(); i++) {
			String current = announcement_to_String(Announcements.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// Message_Logs.txt
		fw = new FileWriter("Message_Logs.txt");
		Info = "FromID toID ID Message\r\n";
		static_variable = Integer.toString(Message.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Message_Logs.size(); i++) {
			String current = message_to_String(Message_Logs.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// Posts.txt
		fw = new FileWriter("Posts.txt");
		Info = "TeacherID ID Post Comments\r\n";
		static_variable = Integer.toString(Post.ID_Count);
		for (int i = 0; i < static_variable.length(); i++)
			fw.write(static_variable.charAt(i));
		fw.write("\r\n");

		for (int i = 0; i < Info.length(); i++)
			fw.write(Info.charAt(i));

		for (int i = 0; i < Posts.size(); i++) {
			String current = post_to_String(Posts.get(i));
			for (int j = 0; j < current.length(); j++) {
				fw.write(current.charAt(j));
			}
			fw.write("\r\n");
		}
		fw.close();
		// End
		System.out.println("DB updated succesfully.");

	}

	public static Course string_to_Course(String c) {

		String[] parts = c.split("\\s+");

		ArrayList<Integer> assignments = new ArrayList<Integer>();
		ArrayList<Integer> students = new ArrayList<Integer>();
		ArrayList<Integer> announcements = new ArrayList<Integer>();

		if (!parts[3].equals("-1")) {
			String[] assignment = parts[3].split("/");
			if (!assignment[0].equalsIgnoreCase("")) {
				for (int i = 0; i < assignment.length; i++) {
					assignments.add(Integer.parseInt(assignment[i]));
				}
			}
		}

		if (!parts[4].equals("-1")) {
			String[] student = parts[4].split("-");
			if (!student[0].equalsIgnoreCase("")) {
				for (int i = 0; i < student.length; i++) {
					students.add(Integer.parseInt(student[i]));
				}
			}
		}

		if (!parts[5].equals("-1")) {
			String[] announcement = parts[5].split("\\+");
			if (!announcement[0].equalsIgnoreCase("")) {
				for (int i = 0; i < announcement.length; i++) {
					announcements.add(Integer.parseInt(announcement[i]));
				}
			}
		}

		Course result = null;
		result = new Course(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), assignments, students,
				announcements);
		return result;
	}

	public static String course_to_String(Course c) {
		String result;

		result = c.getName() + " " + c.getID() + " " + c.getTeacherID() + " " + c.assignments_to_String() + " "
				+ c.students_to_String() + " " + c.announcements_to_String();

		return result;
	}

	public static void add_User_to_DB(User u) throws IOException {
		if (!search_Username(u.getUsername())) {
			Users.add(u);
			System.out.println("User added to the DB succesfully.");
			update_DB();
		} else {
			System.out.println("Username already exists.");
		}

	}

	public static void add_Course_to_DB(Course c) throws IOException {
		if (!search_Course_Name(c.getName())) {
			Courses.add(c);
			System.out.println("Course added to the DB succesfully.");
			update_DB();
		} else {
			System.out.println("Course name already existsDB.");
		}
	}

	public static void add_Assignment_to_DB(Assignment a) throws IOException {
		Assignments.add(a);
		System.out.println("Assignment added to the DB succesfully.");
		update_DB();

	}

	public static void add_Announcement_to_DB(Announcement a) throws IOException {
		Announcements.add(a);
		System.out.println("Announcement added to the DB succesfully.");
		update_DB();

	}

	public static boolean search_Username(String username) {
		boolean result = false;
		for (int i = 0; i < Users.size(); i++) {
			if (Users.get(i).getUsername().equals(username)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static boolean search_Password(String password) {
		boolean result = false;
		for (int i = 0; i < Users.size(); i++) {
			if (Users.get(i).getUsername().equals(password)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static boolean search_Course_Name(String name) {
		boolean result = false;
		for (int i = 0; i < Courses.size(); i++) {
			if (Courses.get(i).getName().equals(name)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static int search_Assignment_ID(int ID) {
		int result = -1;
		for (int i = 0; i < Assignments.size(); i++) {
			if (Assignments.get(i).getID() == ID) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static int search_Course_ID(int ID) {
		int result = -1;
		for (int i = 0; i < Courses.size(); i++) {
			if (Courses.get(i).getID() == ID) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static int search_User_ID(int ID) {
		int result = -1;
		for (int i = 0; i < Users.size(); i++) {
			if (Users.get(i).getID() == ID) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static int search_Announcement_ID(int ID) {
		int result = -1;
		for (int i = 0; i < Announcements.size(); i++) {
			if (Announcements.get(i).getID() == ID) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static int search_Post_ID(int ID) {
		int result = -1;
		for (int i = 0; i < Posts.size(); i++) {
			if (Posts.get(i).getID() == ID) {
				result = i;
				break;
			}
		}
		return result;
	}

	public static void message_History(int from, int to) {
		for (int i = 0; i < Message_Logs.size(); i++) {
			if (Message_Logs.get(i).getOwnerID() == from && Message_Logs.get(i).getResponsibleID() == to) {
				System.out.println("You: " + Message_Logs.get(i).getContent());
			} else if (Message_Logs.get(i).getResponsibleID() == from && Message_Logs.get(i).getOwnerID() == to) {
				System.out
						.println(Users.get(search_User_ID(to)).getUsername() + ": " + Message_Logs.get(i).getContent());

			}
		}

	}

	public static void post_View_TeacherUI(int teacherID, int postID) throws IOException {
		System.out.println(Posts.get(search_Post_ID(postID)).getPost());
		System.out.println("--------------------------------------");
		for (int i = 0; i < Posts.get(search_Post_ID(postID)).getComments().size(); i++) {
			System.out.println(Users.get(search_User_ID(Posts.get(search_Post_ID(postID)).getComments().get(i).getID()))
					.getUsername() + ": " + Posts.get(search_Post_ID(postID)).getComments().get(i).getComment());
		}
		System.out.println("--------------------------------------");

		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- Edit Post\n2.- Delete Post\n3.- Comment to Post\n");
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				choice = 0;
				break;
			case 1:
				System.out.print("Enter the new post:\n");
				String post = scan.next();
				Posts.get(search_Post_ID(postID)).setPost(post);
				System.out.println("Post succesfully edited.");
				update_DB();
				break;
			case 2:
				Posts.remove(search_Post_ID(postID));
				update_DB();
				System.out.println("Post succesfully deleted.");
				choice = 0;
				break;
			case 3:
				System.out.print("Enter the comment:\n");
				scan.nextLine();
				String comment = scan.nextLine();
				Comment c = new Comment(search_User_ID(teacherID), comment);
				Posts.get(search_Post_ID(postID)).getComments().add(c);
				update_DB();
				System.out.println("Comment succesfully added.");
				break;
			}
		}

	}

	public static void post_View_StudentUI(int studentID, int postID) throws IOException {
		System.out.println(Posts.get(search_Post_ID(postID)).getPost());
		System.out.println("--------------------------------------");
		for (int i = 0; i < Posts.get(search_Post_ID(postID)).getComments().size(); i++) {
			System.out.println(Users.get(search_User_ID(Posts.get(search_Post_ID(postID)).getComments().get(i).getID()))
					.getUsername() + ": " + Posts.get(search_Post_ID(postID)).getComments().get(i).getComment());
		}
		System.out.println("--------------------------------------");

		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- Comment to Post\n");
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				choice = 0;
				break;
			case 1:
				System.out.print("Enter the comment:\n");
				scan.nextLine();
				String comment = scan.nextLine();
				Comment c = new Comment(search_User_ID(studentID), comment);
				Posts.get(search_Post_ID(postID)).getComments().add(c);
				update_DB();
				System.out.println("Comment succesfully added.");
				break;
			}
		}

	}

	public static boolean authentication(String username, String password) {
		boolean result = false;
		for (int i = 0; i < Users.size(); i++) {
			if (Users.get(i).getPassword().equalsIgnoreCase(password)) {
				if (Users.get(i).getUsername().equals(username)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static void delete_Course(Course course) throws IOException {
		for (int i = 0; i < Courses.size(); i++) {
			if (Courses.get(i).getID() == course.getID()) {
				Courses.remove(i);
			}
		}
		for (int i = 0; i < Users.size(); i++) {
			if (Users.get(i).getCourses().contains(course.getID())) {
				for (int j = 0; j < Users.get(i).getCourses().size(); j++) {
					if (Users.get(i).getCourses().get(j) == course.getID()) {
						Users.get(i).getCourses().remove(j);
					}
				}
			}
		}

		for (int i = 0; i < course.getAssignments().size(); i++) {
			if (search_Assignment_ID(course.getAssignments().get(i)) != -1) {
				delete_Assignment(Assignments.get(search_Assignment_ID(course.getAssignments().get(i))));
			}
		}

		for (int i = 0; i < course.getAnnouncements().size(); i++) {
			if (search_Announcement_ID(course.getAnnouncements().get(i)) != -1) {
				delete_Announcement(Announcements.get(search_Announcement_ID(course.getAnnouncements().get(i))));
			}
		}
		update_DB();
	}

	public static void delete_Assignment(Assignment assignment) throws IOException {
		for (int i = 0; i < Assignments.size(); i++) {
			if (Assignments.get(i).getID() == assignment.getID()) {
				Assignments.remove(i);
			}
		}

		for (int i = 0; i < Users.size(); i++) {
			for (int j = 0; j < Users.get(i).getCourses().size(); j++) {
				if (search_Course_ID(Users.get(i).getCourses().get(j)) != -1
						&& Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAssignments()
								.contains(assignment.getID())) {
					for (int k = 0; k < Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAssignments()
							.size(); k++) {
						if (Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAssignments()
								.get(k) == assignment.getID()) {
							Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAssignments().remove(k);
						}
					}
				}
			}
		}
		update_DB();
	}

	public static void delete_Announcement(Announcement announcement) throws IOException {
		for (int i = 0; i < Announcements.size(); i++) {
			if (Announcements.get(i).getID() == announcement.getID()) {
				Announcements.remove(i);
			}
		}

		for (int i = 0; i < Users.size(); i++) {
			for (int j = 0; j < Users.get(i).getCourses().size(); j++) {
				if (search_Course_ID(Users.get(i).getCourses().get(j)) != -1
						&& Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAnnouncements()
								.contains(announcement.getID())) {
					for (int k = 0; k < Courses.get(search_Course_ID(Users.get(i).getCourses().get(j)))
							.getAnnouncements().size(); k++) {
						if (Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAnnouncements()
								.get(k) == announcement.getID()) {
							Courses.get(search_Course_ID(Users.get(i).getCourses().get(j))).getAnnouncements()
									.remove(k);
						}
					}
				}
			}
		}
		update_DB();
	}

	public static void non_MemberUI() throws IOException {
		init_DB();
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		boolean input_flag = true;

		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- Create Account\n2.- Login\n");
			flag = true;
			input_flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				break;
			case 1:
				System.out.print("Name:\n");
				String name = scan.next();
				System.out.print("Surname:\n");
				String surname = scan.next();
				System.out.print("Please select a username:\n");
				String username = scan.next();
				while (search_Username(username)) {
					System.out.println("Username already exists.");
					username = scan.next();
				}
				System.out.print("Please select a password:\n");
				String password = scan.next();
				System.out.println("Are you a student or teacher ?");
				System.out.println("\n0.- Exit\n1.- Teacher\n2.- Student\n");
				flag = true;
				input_flag = true;
				while (input_flag) {
					while (flag) {
						try {
							choice = scan.nextInt();
							flag = false;
						} catch (Exception e) {
							System.out.println("Please enter a number");
							scan.nextLine();
						}
					}
					switch (choice) {
					case 0:
						input_flag = false;
						flag = false;
						choice = -1;
						break;
					case 1:
						Teacher T = new Teacher(name, surname, username, password);
						add_User_to_DB(T);
						input_flag = false;
						flag = false;
						break;
					case 2:
						Student S = new Student(name, surname, username, password);
						add_User_to_DB(S);
						input_flag = false;
						flag = false;
						break;
					default:
						System.out.println("Please choose an option.0 for exit.");
						input_flag = true;
						flag = true;
						break;
					}
				}
				break;
			case 2:
				System.out.print("Username:\n");
				username = scan.next();
				while (!search_Username(username)) {
					System.out.println("Username does not exists.");
					username = scan.next();
				}
				System.out.print("Password:\n");
				password = scan.next();
				while (!authentication(username, password)) {
					System.out.println("Password incorrect.'Exit' for exit.");
					password = scan.next();
					if (password.equalsIgnoreCase("exit")) {
						break;
					}
				}
				if (!password.equalsIgnoreCase("exit")) {
					User us = null;
					for (int i = 0; i < Users.size(); i++) {
						if (Users.get(i).getUsername().equals(username)) {
							us = Users.get(i);
							break;
						}
					}
					MemberUI(us);

				}
				break;
			default:
				System.out.println("This is not a valid option.");
				break;
			}

		}
		scan.close();
	}

	public static void MemberUI(User current_User) throws IOException {
		System.out.println("Logged In");
		System.out.println("Hello " + current_User.getName());
		Scanner scan = new Scanner(System.in);
		if (current_User.getType().equals(User.TEACHER)) {
			int choice = 1;
			boolean flag = true;
			while (choice != 0) {
				System.out.println(
						"\n0.- Exit\n1.- Add Course\n2.- See Courses\n3.- Send Message\n4.- See Messages\n5.- Add Post\n6.- See Posts\n");
				flag = true;
				while (flag) {
					try {
						choice = scan.nextInt();
						flag = false;
					} catch (Exception e) {
						System.out.println("Please enter an number");
						scan.nextLine();
					}
				}
				switch (choice) {
				case 0:
					choice = 0;
					break;
				case 1:
					System.out.print("Course Name:\n");
					String name = scan.next();
					while (search_Course_Name(name)) {
						System.out.println("Course name already exists.");
						name = scan.next();
					}
					Course c = new Course(current_User.getID(), name);
					current_User.getCourses().add(c.getID());
					add_Course_to_DB(c);
					break;

				case 2:
					Teacher_CourseUI((Teacher) current_User);
					break;
				case 3:
					System.out.print("To(Username): \n");
					String receiver = scan.next();
					while (!search_Username(receiver)) {
						System.out.println("User not found.");
						receiver = scan.next();
					}
					int index = -1;
					for (int i = 0; i < Users.size(); i++) {
						if (Users.get(i).getUsername().equals(receiver)) {
							index = i;
							break;
						}
					}
					message_History(current_User.getID(), Users.get(index).getID());
					scan.nextLine();
					System.out.print("Please enter the message: \n");
					String message = scan.nextLine();
					Message m = new Message(current_User.getID(), Users.get(index).getID(), message);
					Message_Logs.add(m);
					update_DB();

					break;
				case 4:
					choice = 1;
					while (choice != 0) {
						System.out.println("0. - Exit");
						for (int i = 0; i < Users.size(); i++) {
							System.out.println(i + 1 + ". - " + Users.get(i).getUsername());
						}
						flag = true;
						while (flag) {
							try {
								choice = scan.nextInt();
								if (choice >= 0 && choice <= Users.size())
									flag = false;
								else
									System.out.println("This is not a valid option.");
							} catch (Exception e) {
								System.out.println("Please enter an number.");
								scan.nextLine();
							}

						}
						if (choice != 0) {
							message_History(current_User.getID(), Users.get(choice - 1).getID());
							choice = 0;
						}

					}
					choice = 1;
					break;
				case 5:
					scan.nextLine();
					System.out.println("Post: ");
					String post = scan.nextLine();
					Post p = new Post(current_User.getID(), post);
					Posts.add(p);
					update_DB();
					break;
				case 6:
					choice = 1;
					while (choice != 0) {
						System.out.println("0. - Exit");
						for (int i = 0; i < Posts.size(); i++) {
							System.out.println(i + 1 + ". - " + Posts.get(i).getPost());
						}
						flag = true;
						while (flag) {
							try {
								choice = scan.nextInt();
								if (choice >= 0 && choice <= Posts.size())
									flag = false;
								else
									System.out.println("This is not a valid option.");
							} catch (Exception e) {
								System.out.println("Please enter an number.");
								scan.nextLine();
							}

						}
						if (choice != 0) {
							post_View_TeacherUI(current_User.getID(), Posts.get(choice - 1).getID());
							choice = 0;
						}

					}
					choice = 1;

					break;
				default:
					System.out.println("This is not a valid option.");
					break;
				}

			}

		} else {
			int choice = 1;
			boolean flag = true;
			while (choice != 0) {
				System.out.println(
						"\n0.- Exit\n1.- See Enrolled Courses\n2.- Send Message\n3.- See Messages\n4.- See Posts\n");
				flag = true;
				while (flag) {
					try {
						choice = scan.nextInt();
						flag = false;
					} catch (Exception e) {
						System.out.println("Please enter an number");
						scan.nextLine();
					}
				}
				switch (choice) {
				case 0:
					choice = 0;
					break;
				case 1:
					Student_CourseUI((Student) current_User);
					break;

				case 2:
					System.out.print("To(Username): \n");
					String receiver = scan.next();
					while (!search_Username(receiver)) {
						System.out.println("User not found.");
						receiver = scan.next();
					}
					int index = -1;
					for (int i = 0; i < Users.size(); i++) {
						if (Users.get(i).getUsername().equals(receiver)) {
							index = i;
							break;
						}
					}
					message_History(current_User.getID(), Users.get(index).getID());
					scan.nextLine();
					System.out.print("Please enter the message: \n");
					String message = scan.nextLine();
					Message m = new Message(current_User.getID(), Users.get(index).getID(), message);
					Message_Logs.add(m);
					update_DB();

					break;
				case 3:
					choice = 1;
					while (choice != 0) {
						System.out.println("0. - Exit");
						for (int i = 0; i < Users.size(); i++) {
							System.out.println(i + 1 + ". - " + Users.get(i).getUsername());
						}
						flag = true;
						while (flag) {
							try {
								choice = scan.nextInt();
								if (choice >= 0 && choice <= Users.size())
									flag = false;
								else
									System.out.println("This is not a valid option.");
							} catch (Exception e) {
								System.out.println("Please enter an number.");
								scan.nextLine();
							}

						}
						if (choice != 0) {
							message_History(current_User.getID(), Users.get(choice - 1).getID());
							choice = 0;
						}

					}
					choice = 1;
					break;
				case 4:
					choice = 1;
					while (choice != 0) {
						System.out.println("0. - Exit");
						for (int i = 0; i < Posts.size(); i++) {
							System.out.println(i + 1 + ". - " + Posts.get(i).getPost());
						}
						flag = true;
						while (flag) {
							try {
								choice = scan.nextInt();
								if (choice >= 0 && choice <= Posts.size())
									flag = false;
								else
									System.out.println("This is not a valid option.");
							} catch (Exception e) {
								System.out.println("Please enter an number.");
								scan.nextLine();
							}

						}
						if (choice != 0) {
							post_View_StudentUI(current_User.getID(), Posts.get(choice - 1).getID());
							choice = 0;
						}

					}
					choice = 1;

					break;
				default:
					System.out.println("This is not a valid option.");
					break;
				}

			}
		}

		System.out.println("Logged Out");
	}

	public static void Student_CourseUI(Student student) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		while (choice != 0) {
			System.out.println("0. - Exit");
			for (int i = 0; i < student.getCourses().size(); i++) {
				System.out
						.println(i + 1 + ". - " + Courses.get(search_Course_ID(student.getCourses().get(i))).getName());
			}
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					if (choice >= 0 && choice <= student.getCourses().size())
						flag = false;
					else
						System.out.println("This is not a valid option.");
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}

			}
			if (choice != 0)
				student_Course_SelectedUI(student, Courses.get(search_Course_ID(student.getCourses().get(choice - 1))));

		}
	}

	public static void student_Course_SelectedUI(Student student, Course course) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		System.out.println("Selected course " + course.getName());
		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- See Assignments\n2.- See Announcements\n3.- Upload Work\n");
			flag = true;
			while (flag) {

				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				break;
			case 1:// See Assignments
				for (int i = 0; i < course.getAssignments().size(); i++) {
					System.out.println(i + 1 + ". - "
							+ Assignments.get(search_Assignment_ID(course.getAssignments().get(i))).getExplanation());
				}
				break;
			case 2:// See Announcements
				for (int i = 0; i < course.getAnnouncements().size(); i++) {
					System.out.println(i + 1 + ". - " + Announcements
							.get(search_Announcement_ID(course.getAnnouncements().get(i))).getAnnouncement());
				}

				break;

			case 3:// submit work
				for (int i = 0; i < course.getAssignments().size(); i++) {
					System.out.println(i + 1 + ". - "
							+ Assignments.get(search_Assignment_ID(course.getAssignments().get(i))).getExplanation());
				}
				if (course.getAssignments().size() != 0) {
					flag = true;
					while (flag) {
						try {
							choice = scan.nextInt();
							if (choice >= 1 && choice <= course.getAssignments().size())
								flag = false;
							else
								System.out.println("This is not a valid option.");
						} catch (Exception e) {
							System.out.println("Please enter an number.");
							scan.nextLine();
						}

					}
					if (choice != 0) {
						System.out.println("Enter the work : ");
						String work = scan.next();
						Assignments.get(search_Assignment_ID(course.getAssignments().get(choice - 1)))
								.getSubmitted_Works().put(student.getID(), work);
						System.out.println("Your work is submitted.");
						update_DB();
					}
				}

				break;
			default:
				System.out.println("This is not a valid option.");
				break;
			}
		}

	}

	public static void course_SelectedUI(Teacher teacher, Course course) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		System.out.println("Selected course " + course.getName());
		while (choice != 0) {
			System.out.println(
					"\n0.- Exit\n1.- Delete Course\n2.- Edit Course\n3.- Add Assignment\n4.- See Assignments\n5.- Add Announcement\n6.- See Annoucements\n7.- Enroll Student\n");
			flag = true;
			while (flag) {

				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				break;
			case 1:// Course delete

				delete_Course(course);
				System.out.println("Course deleted.");
				choice = 0;
				break;

			case 2:// Course edit
				System.out.println("Enter the new course name: ");
				String Course_name = scan.next();

				for (int i = 0; i < Courses.size(); i++) {
					if (Courses.get(i).getID() == course.getID()) {
						Courses.get(i).setName(Course_name);
					}
				}
				update_DB();
				System.out.println("Course name edited.");

				break;
			case 3:// Assignment add
				Assignment a = new Assignment(teacher.getID(), course.getID());
				System.out.println("Enter the explanation of the assignment: ");
				String explanation = scan.next();
				a.setExplanation(explanation);
				for (int i = 0; i < Courses.size(); i++) {
					if (Courses.get(i).getID() == course.getID()) {
						Courses.get(i).getAssignments().add(a.getID());
					}
				}
				add_Assignment_to_DB(a);
				System.out.println("Assignment added.");

				break;

			case 4:// See Assignments
				while (choice != 0) {
					System.out.println("0. - Exit");
					for (int i = 0; i < course.getAssignments().size(); i++) {
						System.out.println(i + 1 + ". - " + Assignments
								.get(search_Assignment_ID(course.getAssignments().get(i))).getExplanation());
					}

					flag = true;
					while (flag) {
						try {
							choice = scan.nextInt();
							if (choice >= 0 && choice <= course.getAssignments().size())

								flag = false;
							else
								System.out.println("This is not a valid option.");
						} catch (Exception e) {
							System.out.println("Please enter an number.");
							scan.nextLine();
						}

					}
					if (choice != 0)
						assignment_SelectedUI(teacher,
								Assignments.get(search_Assignment_ID(course.getAssignments().get(choice - 1))));
				}
				break;
			case 5:// Announcement add
				Announcement announ = new Announcement(teacher.getID(), course.getID());
				System.out.println("Enter the announcement : ");
				String announcement = scan.next();
				announ.setAnnouncement(announcement);

				for (int i = 0; i < Courses.size(); i++) {
					if (Courses.get(i).getID() == course.getID()) {
						Courses.get(i).getAnnouncements().add(announ.getID());
					}
				}
				add_Announcement_to_DB(announ);
				System.out.println("Announcement added.");

				break;

			case 6:// See Announcements
				while (choice != 0) {
					System.out.println("0. - Exit");
					for (int i = 0; i < course.getAnnouncements().size(); i++) {
						System.out.println(i + 1 + ". - " + Announcements
								.get(search_Announcement_ID(course.getAnnouncements().get(i))).getAnnouncement());
					}

					flag = true;
					while (flag) {
						try {
							choice = scan.nextInt();
							if (choice >= 0 && choice <= course.getAnnouncements().size())
								flag = false;
							else
								System.out.println("This is not a valid option.");
						} catch (Exception e) {
							System.out.println("Please enter an number.");
							scan.nextLine();
						}

					}
					if (choice != 0)
						announcement_SelectedUI(teacher,
								Announcements.get(search_Announcement_ID(course.getAnnouncements().get(choice - 1))));
				}
				break;
			case 7:// Student Add
				Map<Integer, Integer> studs = new HashMap<Integer, Integer>();
				int count = 1;
				while (choice != 0) {
					System.out.println("0. - Exit");
					for (int i = 0; i < Users.size(); i++) {
						if (Users.get(i).getClass() == Student.class
								&& !Users.get(i).getCourses().contains(course.getID())) {
							studs.put(count, i);
							System.out.println(count + ". - " + Users.get(i).getName());
							count++;
						}
					}

					flag = true;
					while (flag) {
						try {
							choice = scan.nextInt();
							if (choice >= 0 && choice < count)
								flag = false;
							else
								System.out.println("This is not a valid option.");
						} catch (Exception e) {
							System.out.println("Please enter an number.");
							scan.nextLine();
						}

					}
					if (choice != 0) {
						Courses.get(search_Course_ID(course.getID())).getStudents().add(studs.get(choice));
						Users.get(search_User_ID(studs.get(choice))).getCourses().add(course.getID());
						System.out.println("Student succesfully added to the course.");
						update_DB();
					}
					choice = 0;
				}

				break;
			default:
				System.out.println("This is not a valid option.");
				break;
			}
		}

	}

	public static void Teacher_CourseUI(Teacher teacher) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		while (choice != 0) {
			System.out.println("0. - Exit");
			for (int i = 0; i < teacher.getCourses().size(); i++) {
				System.out
						.println(i + 1 + ". - " + Courses.get(search_Course_ID(teacher.getCourses().get(i))).getName());
			}
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					if (choice >= 0 && choice <= teacher.getCourses().size())
						flag = false;
					else
						System.out.println("This is not a valid option.");
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}

			}
			if (choice != 0)
				course_SelectedUI(teacher, Courses.get(search_Course_ID(teacher.getCourses().get(choice - 1))));

		}
	}

	public static void assignment_SelectedUI(Teacher teacher, Assignment assignment) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		System.out.println("Selected assignment " + assignment.getExplanation());
		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- Delete Assignment\n2.- Edit Assignment\n3.- See Submitted Works\n");
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				break;
			case 1:
				delete_Assignment(assignment);
				System.out.println("Assignment deleted.");
				choice = 0;
				break;

			case 2:
				System.out.println("Enter the new assignment explanation: ");
				String Assignment_explanation = scan.next();
				Assignments.get(search_Assignment_ID(assignment.getID())).setExplanation(Assignment_explanation);
				update_DB();
				System.out.println("Assignment explanation edited.");
				break;

			case 3:
				int count = 1;
				for (Map.Entry<Integer, String> entry : assignment.getSubmitted_Works().entrySet()) {
					System.out.println(count + " Student ID: " + entry.getKey() + " " + " Work: " + entry.getValue());
					count++;
				}

				break;

			}
		}
	}

	public static void announcement_SelectedUI(Teacher teacher, Announcement announcement) throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = 1;
		boolean flag = true;
		System.out.println("Selected announcement " + announcement.getAnnouncement());
		while (choice != 0) {
			System.out.println("\n0.- Exit\n1.- Delete Assignment\n2.- Edit Assignment\n");
			flag = true;
			while (flag) {
				try {
					choice = scan.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Please enter an number.");
					scan.nextLine();
				}
			}
			switch (choice) {
			case 0:
				break;
			case 1:
				delete_Announcement(announcement);
				System.out.println("Announcement deleted.");
				choice = 0;
				break;

			case 2:
				System.out.println("Enter the new announcement: ");
				String announcementt = scan.next();
				Announcements.get(search_Announcement_ID(announcement.getID())).setAnnouncement(announcementt);
				update_DB();
				System.out.println("Announcement edited.");
				break;

			}
		}
	}

	public static void main(String[] args) throws IOException {
		non_MemberUI();

	}

}

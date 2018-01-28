package Middle;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Student {
	private String name;
	private String studentId;
	private ArrayList<Subject> subjects;

	public Student() {
	}

	public Student(String name, String studentId, ArrayList<Subject> subjects) {
		super();
		this.name = name;
		this.studentId = studentId;
		this.subjects = subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	public void save(DataOutputStream dos) throws IOException {
		dos.writeUTF(this.name);
		dos.writeUTF(this.studentId);
		dos.writeInt(subjects.size());
		for (Subject subject : subjects) {
			subject.save(dos);
		}
	}

}

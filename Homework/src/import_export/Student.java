package import_export;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private int id;
	private String name;
	private List<Subject> subjects;

	public Student(int id, String name, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.subjects = subjects;
	}
	
	public Student() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	
	public String exportToTxt() {
		String result = id + "\t" + name + "\t";
		for (Subject subject : subjects) {
			result += subject.getName() + "\t" + subject.getScore() + "\t";
		}
		return result;
	}
	
	public void importFromTxt(String text) {
		String[] value = text.split("\t");
		id = Integer.parseInt(value[0]);
		name = value[1];
		subjects = new ArrayList<>();
		int numSubjects = (value.length-2)/2;
		for (int i = 0; i < numSubjects; i++) {
			Subject subject = new Subject(value[2+2*i + 0], Double.parseDouble(value[2+2*i + 1]));
			subjects.add(subject);
		}
	}
	
	public void importInfor(String text) {
		String[] value = text.split("\t");
		id = Integer.parseInt(value[0]);
		name = value[1];
	}
	public void importSubject(String text) {
		String[] value = text.split("\t");
		id = Integer.parseInt(value[0]);
		name = value[1];
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", subjects=" + subjects + "]";
	}
	
}

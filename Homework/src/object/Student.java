package object;

import java.io.*;
import java.util.*;

public class Student {
	private int id;
	private String name;
	private List<Subject> subjects;

	public Student() {
	}

	public Student(int id, String name, List<Subject> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.subjects = subjects;
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

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", subjects=" + subjects + "]";
	}

	public void save(DataOutput dataOutput) throws IOException {
		while (name.length() < 50) {
			name+=" ";
		}
		dataOutput.writeInt(id);
		dataOutput.writeChars(name);
		dataOutput.writeInt(subjects.size());
		for (Subject subject : subjects) {
			subject.save(dataOutput);
		}
	}

	public void load(DataInput dataInput) throws IOException {
		id = dataInput.readInt();
		name = "";
		for (int i = 0; i < 50; i++) {
			name += dataInput.readChar();
		}
		name = name.trim();
		int subjectsSize = dataInput.readInt();
		subjects = new ArrayList<>();
		for (int i = 0; i < subjectsSize; i++) {
			Subject subject = new Subject();
			subject.load(dataInput);
			subjects.add(subject);
		}
	}
}

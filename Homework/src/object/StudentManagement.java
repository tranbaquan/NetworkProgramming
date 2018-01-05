package object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement {
	public void saveList(List<Student> students, String fileName) throws IOException {
		if (students == null)
			return;
		OutputStream os = new FileOutputStream(fileName);
		DataOutputStream dataOutput = new DataOutputStream(os);
		dataOutput.writeInt(students.size());
		for (Student student : students) {
			student.save(dataOutput);
		}
		dataOutput.close();
	}

	public List<Student> loadList(String fileName) throws IOException {
		List<Student> students = new ArrayList<>();
		InputStream is = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(is);
		int size = dis.readInt();
		for (int i = 0; i < size; i++) {
			Student student = new Student();
			student.load(dis);
			students.add(student);
		}
		dis.close();
		return students;
	}

	public Student loadStudent(String fileName, int index) throws IOException {
		InputStream is = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(is);
		int size = dis.readInt();
		if (index > size || index < 0) {
			dis.close();
			return null;
		}
		Student student = null;
		for (int i = 1; i <= index; i++) {
			student = new Student();
			student.load(dis);
		}
		dis.close();
		return student;
	}
	public Student loadStudentRandomAccessFile(String fileName, int index) throws IOException {
		File file = new File(fileName);
		RandomAccessFile rar = new RandomAccessFile(file, "rw");
		int size = rar.readInt();
		if (index > size || index < 0) {
			rar.close();
			return null;
		}
		Student student = new Student();
		long length = (file.length()-4)/size;
		long pos = length*(index-1) + 4;
		rar.seek(pos);
		student.load(rar);
		rar.close();
		return student;
	}
}

package object;

import java.io.IOException;
import java.util.*;

public class Test {
	public static void main(String[] args) throws IOException {
		Subject sj1 = new Subject("Toán", 10);
		Subject sj2 = new Subject("Lý", 8.8);
		Subject sj3 = new Subject("Hóa", 7);
		Subject sj4 = new Subject("Văn", 6);
		Subject sj5 = new Subject("Anh", 5);
		List<Subject> sjs1 = new ArrayList<>();
		sjs1.add(sj1);
		sjs1.add(sj2);
		sjs1.add(sj3);
		List<Subject> sjs2 = new ArrayList<>();
		sjs2.add(sj4);
		sjs2.add(sj5);
		List<Subject> sjs3 = new ArrayList<>();
		sjs3.add(sj2);
		Student student1 = new Student(12345, "Nguyễn Văn A", sjs1);
		Student student2 = new Student(12346, "Nguyễn Văn B", sjs2);
		Student student3 = new Student(12347, "Nguyễn Văn C", sjs3);
		List<Student> students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		
		StudentManagement studentManagement = new StudentManagement();
		String file = "e:\\student.txt";
		studentManagement.saveList(students, file);
//		System.out.println(studentManagement.loadList(file));
//		System.out.println(studentManagement.loadStudent(file, 3));
		System.out.println(studentManagement.loadStudentRandomAccessFile(file, 3));
	}
}

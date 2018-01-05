package import_export;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<Subject> subjects1 = new ArrayList<>();
		subjects1.add(new Subject("Toán", 10));
		subjects1.add(new Subject("Lý", 7));
		Student student1 = new Student(123, "Nguyễn Văn A", subjects1);
		List<Subject> subjects2 = new ArrayList<>();
		subjects2.add(new Subject("LTM", 7));
		subjects2.add(new Subject("Lý", 7));
		Student student2 = new Student(124, "Nguyễn Văn B", subjects2);
		List<Student> students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		StudentManagement sd = new StudentManagement();
		// sd.exportToTxt(students,
		// "e:\\NetworkProgrammingTest\\import_export\\student.txt");
		System.out.println(sd.importFromTxt("e:\\NetworkProgrammingTest\\import_export\\student.txt"));
	}
}

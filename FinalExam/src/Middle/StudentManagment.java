package Middle;
import java.io.*;
import java.util.*;

public class StudentManagment {
	ArrayList<Student> students;
	public StudentManagment() {
		// mình tạo data ở đây
		data();
	}
	public void data() {
		this.students = new ArrayList<>();
		ArrayList<Subject> subjects1 = new ArrayList<>();
		subjects1.add(new Subject("Lập Trình Mạng", 6.0));
		subjects1.add(new Subject("Toán", 7.0));
		subjects1.add(new Subject("Lí", 8.0));
		ArrayList<Subject> subjects2 = new ArrayList<>();
		subjects2.add(new Subject("Lập Trình Mạng", 6.4));
		subjects2.add(new Subject("Văn", 7.7));
		subjects2.add(new Subject("Sử", 8.8));
		subjects2.add(new Subject("Địa", 9.9));
		ArrayList<Subject> subjects3 = new ArrayList<>();
		subjects3.add(new Subject("LTNC", 5.0));
		subjects3.add(new Subject("PTTKHTTT", 7.0));
		subjects3.add(new Subject("Sử", 8.8));
		subjects3.add(new Subject("Hóa", 8.8));
		subjects3.add(new Subject("Địa", 9.9));
		ArrayList<Subject> subjects4 = new ArrayList<>();
		subjects4.add(new Subject("Lập Trình Mạng", 6.4));
		subjects4.add(new Subject("Anh Văn", 7.5));
		Student student1 = new Student("Nguyễn Văn A", "123", subjects1);
		Student student2 = new Student("Nguyễn Văn B", "124", subjects2);
		Student student3 = new Student("Trần Bá Quan", "125", subjects3);
		Student student4 = new Student("Đặng Văn B", "126", subjects4);
		this.students.add(student1);
		this.students.add(student2);
		this.students.add(student3);
		this.students.add(student4);
	}

	public void save(String df) throws IOException {
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(df)));
		dos.writeInt(this.students.size());
		for (Student student : students) {
			student.save(dos);
		}
		dos.flush();
		dos.close();
	}
	public static void main(String[] args) throws IOException {
		StudentManagment studentManagment = new StudentManagment();
		studentManagment.save("E:\\Test\\student.txt");
	}
}

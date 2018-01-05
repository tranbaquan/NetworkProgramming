package import_export;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagement {
	public void exportToTxt(List<Student> students, String df) {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(df), "UTF-8"));
			for (Student student : students) {
				pw.println(student.exportToTxt());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Student> importFromTxt(String sf) {
		List<Student> students = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sf), "UTF-8"));
			String data;
			while ((data = br.readLine()) != null) {
				Student student = new Student();
				student.importFromTxt(data);
				students.add(student);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public List<Student> importAsyn(String studentSource, String subjectSource){
		List<Student> students = new ArrayList<>();
		try {
			BufferedReader studentReader = new BufferedReader(new InputStreamReader(new FileInputStream(studentSource), "UTF-8"));
			String data;
			while ((data = studentReader.readLine()) != null) {
				Student student = new Student();
				student.importInfor(data);
				students.add(student);
			}
			studentReader.close();
			BufferedReader subjectReader = new BufferedReader(new InputStreamReader(new FileInputStream(subjectSource), "UTF-8"));
			while ((data = subjectReader.readLine()) != null) {
				
			}
			subjectReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}
}

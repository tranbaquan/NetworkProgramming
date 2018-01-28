package test_skill;

import java.io.*;

public class MultiFile {
	public static void multiFile(String studentInforSource, String studentScoreSource, String df) throws IOException {
		// mo file infor
		BufferedReader br1 = new BufferedReader(
				new InputStreamReader(new FileInputStream(studentInforSource), "UTF-8"));
		// tao file dich
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(df), "UTF-16"), true);
		String line;
		// doc du lieu tu file 1
		while ((line = br1.readLine()) != null) {
			Student student = new Student();
			// lay thong tin student
			student.findInfor(line);
			// mo file thu 2 de tim thong tin
			BufferedReader br2 = new BufferedReader(
					new InputStreamReader(new FileInputStream(studentScoreSource), "UTF-8"));
			// tim diem trung binh cua student
			student.findScores(student.getId(), br2);
			// ghi thong tin ra file dich
			student.writeInfor(pw);
			// dong file
			br2.close();
		}
		br1.close();
		pw.flush();
		pw.close();
	}
	public static void main(String[] args) throws IOException {
		multiFile("E:\\LTDTData\\f1.txt", "E:\\LTDTData\\f2.txt", "E:\\LTDTData\\f3.txt");
	}
}

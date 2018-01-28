package test_skill;

import java.io.*;

public class Student {
	private String name;
	private int id;
	private double avg;

	public Student(String name, int id, double avg) {
		super();
		this.name = name;
		this.id = id;
		this.avg = avg;
	}

	public Student() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public void findInfor(String line) {
		String[] value = line.split(" ");
		this.name = value[0];
		this.id = Integer.parseInt(value[1]);
	}

	public void findScores(int id, BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			String[] value = line.split(" ");
			if ((id + "").equals(value[0])) {
				double sum = 0;
				for (int i = 1; i < value.length; i++) {
					sum += Double.parseDouble(value[i]);
				}
				this.avg = sum/(value.length-1);
				break;
			}
		}
	}
	
	public void writeInfor(PrintWriter pw) {
		pw.println(this.name + " " + this.id + " " + this.avg);
	}
}

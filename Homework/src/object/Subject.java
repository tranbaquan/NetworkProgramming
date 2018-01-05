package object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Subject {
	private String name;
	private double score;
	public Subject() {
	}
	public Subject(String name, double score) {
		super();
		this.name = name;
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Subject [name=" + name + ", score=" + score + "]";
	}
	public void save(DataOutput dataOutput) throws IOException {
		while (name.length() < 20) {
			name += " ";
		}
		dataOutput.writeChars(name);
		dataOutput.writeDouble(score);
	}
	public void load(DataInput dataInput) throws IOException {
		name = "";
		for (int i = 0; i < 50; i++) {
			name += dataInput.readChar();
		}
		name = name.trim();
		score = dataInput.readDouble();
	}
	
}

package Middle;
import java.io.DataOutputStream;
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

	public void save(DataOutputStream dos) throws IOException {
		dos.writeUTF(this.name);
		dos.writeDouble(this.score);
	}

}

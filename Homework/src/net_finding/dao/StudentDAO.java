package net_finding.dao;

import java.util.*;

import net_finding.model.Student;

public class StudentDAO {
	List<Student> students;

	public StudentDAO() {
		setVirtualData();
	}

	private void setVirtualData() {
		Student student1 = new Student("123", "Nguyen Van A", 15, 9);
		Student student2 = new Student("124", "Nguyen Van B", 16, 8);
		Student student3 = new Student("125", "Tran Van A", 18, 9);
		Student student4 = new Student("126", "Tran Ba Quan", 20, 10);
		students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		students.add(student4);
	}

	public List<Student> findById(String id) {
		List<Student> res = new ArrayList<>();
		for (Student student : students) {
			if (student.getId().equals(id))
				res.add(student);
		}
		return res;
	}

	public List<Student> findByName(String name) {
		List<Student> res = new ArrayList<>();
		for (Student student : students) {
			if (student.getName().equals(name))
				res.add(student);
		}
		return res;
	}

	public List<Student> findByAge(int age) {
		List<Student> res = new ArrayList<>();
		for (Student student : students) {
			if (student.getAge() == age)
				res.add(student);
		}
		return res;
	}

	public List<Student> findByScore(double score) {
		List<Student> res = new ArrayList<>();
		for (Student student : students) {
			if (student.getScore() >= score)
				res.add(student);
		}
		return res;
	}
}

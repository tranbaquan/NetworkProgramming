package net_finding.server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import net_finding.dao.*;
import net_finding.model.*;

public class ServerProcess extends Thread {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private boolean isLogged;
	private boolean isCorrectUsername;
	private String username = "";
	private String password = "";
	private List<Student> res;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		this.netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.isLogged = false;
		this.isCorrectUsername = false;
	}

	@Override
	public void run() {
		try {
			netOut.println("Connected to server!");
			netOut.flush();
			String line;
			while (true) {
				try {
					if (!isCorrectUsername) {
						netOut.println("Please login to process!");
						netOut.flush();
					}
					// 1.read command from user
					netOut.print(isLogged ? username + "> " : "> ");
					netOut.flush();
					line = netIn.readLine();
					// 2.request analysis
					if ("QUIT".equals(line.trim().toUpperCase()))
						break;
					if (!isLogged) {
						requestAnalysis(line);
					} else {
						requestAnalysis(line);
						// 3.request processing

						// 4.send response
						if (res.isEmpty()) {
							netOut.println("Not Found!");
							netOut.flush();
						} else {
							for (Student student : res) {
								netOut.println(student);
								netOut.flush();
							}
						}
					}

				} catch (Exception e) {
					netOut.println(e.getMessage());
					netOut.flush();
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void requestAnalysis(String line) throws EOFException, SQLException {
		try {
			StringTokenizer st = new StringTokenizer(line);
			String command = st.nextToken().toUpperCase();
			DAO dao = new DAO();
			this.res = new ArrayList<>();
			switch (command) {
			case "USER":
				username = st.nextToken();
				isCorrectUsername = dao.findUsername(username);
				if(isCorrectUsername) {
					netOut.println("Correct username!");
				}else {
					netOut.println("Incorrect username!");
				}
				netOut.flush();
				break;
			case "PASS":
				password = st.nextToken();
				isLogged = dao.checkLogin(username, password);
				if(isCorrectUsername) {
					netOut.println("Correct password!");
					netOut.println("Login success!");
				}else {
					netOut.println("Incorrect password!");
				}
				break;
			case "LOGOUT":
				username = "";
				password = "";
				isLogged = false;
				isCorrectUsername = false;
				break;
			case "FIND_BY_ID":
				String id = st.nextToken();
				res = dao.findById(id);
				break;
			case "FIND_BY_NAME":
				String name = line.substring(command.length() + 1);
				res = dao.findByName(name);
				break;
			case "FIND_BY_AGE":
				String ageTxt = st.nextToken();
				int age = Integer.parseInt(ageTxt);
				res = dao.findByAge(age);
				break;
			case "FIND_BY_SCORE":
				String scoreTxt = st.nextToken();
				double score = Double.parseDouble(scoreTxt);
				res = dao.findByScore(score);
				break;
			case "UPDATE_SCORE":
				String idStudent = st.nextToken();
				String scoreUpdateTxt = st.nextToken();
				double scoreUpdate = Double.parseDouble(scoreUpdateTxt);
				if(dao.updateScore(idStudent, scoreUpdate)) {
					netOut.println("Update score success!");
				}else {
					netOut.println("Update score failed!");
				}
				netOut.flush();
				break;

			default:
				throw new EOFException("Command not found!");
			}
		} catch (NoSuchElementException e) {
			throw new EOFException("Syntax command error!");
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Parameter is invalid!");
		}
	}

}

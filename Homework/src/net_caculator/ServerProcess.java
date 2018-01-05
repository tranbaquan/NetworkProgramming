package net_caculator;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.activity.ActivityCompletedException;

public class ServerProcess extends Thread {
	private Socket socket;
	private BufferedReader netIn;
	private PrintWriter netOut;
	private char operator;
	private double num1, num2;

	public ServerProcess(Socket socket) throws IOException {
		this.socket = socket;
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
	}

	@Override
	public void run() {
		try {
			netOut.println("Welcome....");
			String command;
			while (true) {

				// 1.read command from user
				netOut.print("> ");
				netOut.flush();
				command = netIn.readLine();
				if ("EXIT".equals(command.toUpperCase())) {
					break;
				}

				// 2.request analysis
				try {
					requestAnlysis(command);


					// 4.send response
					double result = getResult();
					if (Double.isInfinite(result)) {
						throw new NumberFormatException("103: Value is finite! Please check the expression!");
					}
					netOut.println(command + " = " + result);
				} catch (Exception e) {
					netOut.println(e.getMessage());
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double getResult() {
		double result = 0;
		switch (operator) {
		case '*':
			result = num1 * num2;
			break;
		case '/':
			result = num1 / num2;
			break;
		case '-':
			result = num1 - num2;
			break;
		case '+':
			result = num1 + num2;
			break;

		default:
			break;
		}
		return result;
	}

	private void requestAnlysis(String command)
			throws NumberFormatException, NoSuchElementException, ActivityCompletedException {
		try {
			StringTokenizer st = new StringTokenizer(command, "*/+-");
			if (st.countTokens() > 2) {
				throw new ActivityCompletedException("105: multi expression unsupported!");
			}
			String snum1 = st.nextToken();
//			System.out.println("num1: " + snum1);
			operator = command.charAt(snum1.length());
			String snum2 = st.nextToken();
//			System.out.println("operator: " + operator);
//			System.out.println("num2: " + snum2);
			int len = snum1.length() + snum2.length() + 1;
			if (len < command.length()) {
				snum2 = command.substring(snum1.length() + 1, snum1.length() + 1 + command.length() - len) + snum2;
			}
			num1 = Double.parseDouble(snum1.trim());
			num2 = Double.parseDouble(snum2.trim());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("101: operand is not number!");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("102: no such operand in expression!");
		}
	}
}

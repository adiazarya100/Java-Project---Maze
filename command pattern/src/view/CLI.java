package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;

public class CLI {

	private HashMap<String, Command> commandsMap;
	private PrintWriter out;
	private BufferedReader in;
	
	
	public CLI(String file) throws IOException{
		super();
		try {
			this.out = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readDate() throws IOException{
		System.out.println("reading data");
		System.out.println("enter command:");
		String line = in.readLine(); //receiving from user
		while(!line.equals("exit"))
		{
			String[] cm = line.split(" ");
			commandsMap.get(line);
		}
	in.close();
	}
	
	
}

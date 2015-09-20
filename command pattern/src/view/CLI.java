package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import javax.activation.CommandMap;

import model.Model;
import model.MyModel;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import controller.Command;
import controller.MyController;

public class CLI implements Runnable{

		
	protected HashMap<String, Command> commandsMap = new HashMap<>();
	private PrintWriter out;
	private BufferedReader in;
	private MyController mc;
	private Command command;
	
	public CLI(PrintWriter out, BufferedReader in /*, MyController mc*/) {
		
		this.out=out;
		this.in=in;
		/*this.mc = mc;*/
		
	}

	public CLI(String file) throws IOException{
		
		
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
	
	 
	
/*	public void readDate() throws IOException{
		System.out.println("reading data");
		System.out.println("enter command:");
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
		String line = in.readLine(); //receiving from user
		
		while(!line.equals("exit"))
		{
			String[] args = line.split(" ");
			if(args[0].equals("dir"))
			{
				System.out.println("bla");
				command=commandsMap.get(line);
				command.doCommand(args);
			}
			
		}
		
	in.close();
		} catch (IOException e) {
			// handle this
			e.printStackTrace();
	}
	}*/
	
	
	
	/** Start the CLI. "exit" will end it.
	 * 
	 */
	public void start(){
		this.run();
	}
	
	@Override
	public void run(){
		
		//commandsMap=mc.getCommandsMap();
		out.print("Enter command: ");
		out.flush();
		try {
			String line = in.readLine();
			
			while (!line.equals("exit"))
			{
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				if(commandName.equals("display"))
				{
					if(sp[1].equals("cross"))
						commandName="cross";
					else if(sp[1].equals("solution"))
						commandName="solution";
						
				}
				
				//String[] arg = null;
				/*if (sp.length > 1)
					arg[0] = sp[1];*/
				/*for(int i=0;i<sp.length;i++){
					arg[i]=sp[i+1];
				}*/
				// Invoke the command
				command=commandsMap.get(commandName);
				if(command!=null /*&& arg!=null*/)
				{
					command.doCommand(sp);
					
				}
					
				out.flush();
				
				out.println("Enter command: ");
				out.flush();
				line = in.readLine();
			}
			out.write("Goodbye");
						
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}			
		}	
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
	

//	public static void main(String[] args) throws IOException {
//		
//		PrintWriter w=new PrintWriter(System.out);
//		CLI cli = new CLI(w,new BufferedReader(new InputStreamReader(System.in)));/*, new MyController(w)*/
//		MyView view = new MyView(cli);
//		MyModel model = new MyModel();
//		MyController controller = new MyController(model, view, w);
//		view.start();
//	}
	
	public void setHashMap(HashMap<String, Command> hm) {
		this.commandsMap=hm;
		
	}

	
}

	
	



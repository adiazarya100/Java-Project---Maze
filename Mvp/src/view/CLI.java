package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import presenter.Command;

// TODO: Auto-generated Javadoc
/**
 * The Class CLI.
 */
public class CLI implements Runnable {

	/** The runing. */
	boolean runing = true;
	

	/** The commands map. */
	protected HashMap<String, Command> commandsMap = new HashMap<>();
	
	/** The out. */
	private PrintWriter out;
	
	/** The in. */
	private BufferedReader in;
	
	
	/** The command. */
	private Command command;
	
	/** The view. */
	protected View view;
	
	
	/**
	 * Instantiates a new CLI.
	 * @param out the out
	 * @param in the in
	 * @param v the view
	 */
	public CLI(PrintWriter out, BufferedReader in, View v) {
		this.out=out;
		this.in=in;
		this.view=v;
		}

	
	/**
	 * Gets the out.
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;}
	
	/**
	 * Sets the out.
	 * @param out the new out
	 */
	public void setOut(PrintWriter out) {
		this.out = out;}
	
	/**
	 * Gets the in.
	 *
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}

	/**
	 * Sets the in.
	 *
	 * @param in the new in
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()  {
		out.flush();
		String line=null;
		String [] args=null;
		String [] args1={"exit"};
		printCommands();
		

		try {	
			while (runing){
				out.println("Please Enter command: ");
				out.flush();
				
				line= in.readLine();
				args= line.split(" ");
				if(commandsMap.containsKey(args[0])){
					if(args[0].toLowerCase().equals("exit")){
						runing=false;
					}
					command= commandsMap.get(args[0]);		
					command.setArguments(args);
					view.setUserCommand(command);

				}
				else{
					out.println("Invalid parameters");
					out.println();
					out.flush();
				}
			}
		} catch (IOException e) {
			out.println("Error! Exception! ");
		}
		
		out.println("bye bye.");

	}
	
	/**
	 * Sets the commandsMap.
	 *
	 * @param commands the new commandsMap
	 */
	public void setCommands(HashMap<String, Command> commands) {

		this.commandsMap = commands; 
	}
	
	
	/**
	 * Checks if is runing.
	 *
	 * @return true, if is runing
	 */
	public boolean isRuning() {
		return runing;
	}

	/**
	 * Sets the runing.
	 *
	 * @param runing the new runing
	 */
	public void setRuning(boolean runing) {
		this.runing = runing;
	}
	
	
	/**
	 * Prints the commands menu.
	 */
	public void printCommands(){

		System.out.println("--------------welcome!--------------");
		System.out.println();
		System.out.println("please enter one of the following command without the < > symbol!!!");
		System.out.println();
		System.out.println("command 1: dir <path> ----------------------------------------- print all the files in the current folder");
		System.out.println("command 2: generate 3d maze <name> <x, y, z> ------------------ generates new 3d maze");
		System.out.println("command 3: display <name> ------------------------------------- display the maze");
		System.out.println("command 4: display cross section by <axis> <index> for <name> - display maze cross section  ");
		System.out.println("command 5: save maze <name> <file name> ----------------------- save the compressed maze in requested file ");
		System.out.println("command 6: load maze <file name> <name> ----------------------- load the requested maze");
		System.out.println("command 7: maze size <name> ----------------------------------- display the maze size in memory");
		System.out.println("command 8: file size <name> ----------------------------------- display the maze size in file");
		System.out.println("command 9: solve <name> <algorithm> --------------------------- solve the maze using BFS or ASTAR algorithm");
		System.out.println("command 10: display solution <name> --------------------------- display the solution nodes");
		System.out.println("command 11: exit ---------------------------------------------- exit the program without any open threads or files");
		System.out.println();
	}
}

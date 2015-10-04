package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;
import algorithms.search.Solution;


/**
 * The Class MyView.
 */
public class MyView extends Observable implements View   {
	
	/** The out. */
	public PrintWriter out;
	
	/** The command. */
	private Command command;
	
	/** The cli. */
	private CLI cli;
	
	/** The Maze2d displayer adapter. */
	private Maze2dDisplayer Maze2dDisplayerAdapter;
	
	/** The Maze3d displayer adapter. */
	private Maze3dDisplayer Maze3dDisplayerAdapter;
	
	
	
	/**
	 * constructor
	 * Instantiates a new my view.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public MyView(PrintWriter out, BufferedReader in) {
		this.cli = new CLI(out, in, this);
		this.out = out;
		Maze2dDisplayerAdapter = new Maze2dDisplayer(out);
		Maze3dDisplayerAdapter = new Maze3dDisplayer(out);
	}
	
	/**
	 * Dir command.
	 *
	 * @param fileName the file name
	 */
	@Override
	public void dirCommand(String fileName) {
		File folder = new File(fileName);
		File[] listOfFiles = folder.listFiles();
		if(folder.exists())
		{
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		}
		else
			System.out.println("path not exists, try again.");
	}
		
	/**
	 * Display solution.
	 *
	 * @param <T> the generic type
	 * @param s the s
	 */
	@Override
	public <T> void displaySolution(Solution<T> s) {
		System.out.println(s);
	}
	
	
	/**
	 * Display string.
	 *
	 * @param printString the print string
	 */
	@Override
	public void displayString(String printString) {
		
		out.println(printString);
		out.flush();
	}
	
	
	/**
	 * Gets the user command.
	 *
	 * @return the user command
	 */
	@Override
	public Command getUserCommand() {
		// TODO Auto-generated method stub
		return this.command;
	}
	
	
	
	/**
	 * Sets the user command.
	 *
	 * @param command the new user command
	 */
	@Override
	public void setUserCommand(Command command) {
		this.command=command;
		setChanged();
		notifyObservers("new command accepted");

	}
	
	
	/**
	 * Start.
	 */
	@Override
	public void start() {
		//start the cli in new thread
		Thread myThread = new Thread(cli);
		myThread.start();

	}
	

	/**
	 * Sets the commands.
	 *
	 * @param commands the commands
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		cli.setCommands(commands); //set the commands in the cli class
	}



	/**
	 * Exit.
	 * close the program and exit the threads
	 */
	@Override
	public void exit() {
		
		cli.setRuning(false);
	}

	/**
	 * Display model.
	 * 
	 * @param <T> the generic type
	 * @param draw the draw
	 */
	@Override
	public <T> void displayModel(Adapter<T> draw) {
		Maze3dDisplayerAdapter.getDisplayer((Adapter<int[][][]>) draw);
		Maze3dDisplayerAdapter.display();

		
	}

	/**
	 * Display cross section by.
	 *
	 * @param <T> the generic type
	 * @param draw the draw
	 */
	@Override
	public <T> void displayCrossSectionBy(Adapter<T> draw) {
		Maze2dDisplayerAdapter.getDisplayer((Adapter<int[][]>) draw);
		Maze2dDisplayerAdapter.display();

	}


}

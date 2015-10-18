package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import controller.Command;
import controller.MyController;

/**
 * The Class CLI.
 */
public class CLI implements Runnable{
	/** The commands map. */
	protected HashMap<String, Command> commandsMap = new HashMap<>();
	
	/** The out. */
	private PrintWriter out;
	
	/** The in. */
	private BufferedReader in;
	
	/** The mc. */
	private MyController mc;
	
	/** The command. */
	private Command command;
	
	/**
	 * Instantiates a new cli.
	 * @param out the out
	 * @param in the in
	 */
	public CLI(PrintWriter out, BufferedReader in) {
		this.out=out;
		this.in=in;
		}

	/**
	 * Instantiates a new cli.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
	
	/** Start the CLI. "exit" will end it.
	 * 
	 */
	public void start(){
		this.run();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		
		String line = null;
		do{
		out.print("Enter command: ");
		out.flush();
		try {
			
			line = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				String[] sp = line.split(" ");
								
				String commandName = sp[0];
				if(commandName.equals("display"))
				{
					if(sp[1].equals("cross"))
						commandName="cross";
					else if(sp[1].equals("solution"))
						commandName="solution";
						
				}
				
				// Invoke the command
				command=commandsMap.get(commandName);
				if(command!=null)
				{
					try {
						command.doCommand(sp);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		while(!line.equals("exit"));
			out.write("Goodbye");
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
	 * Sets the hash map.
	 * @param hm the hm
	 */
	public void setHashMap(HashMap<String, Command> hm) {
		this.commandsMap=hm;}	
}

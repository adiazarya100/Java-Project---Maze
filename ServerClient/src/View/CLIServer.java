package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import Presenter.RemoteControlCommand;

public class CLIServer implements Runnable {

	/**
	 * The Class CLI.
	 * this class also implements Runnable in order to later be run as a thread
	 */


		/** The runing. */
		boolean runing = true;
		

		/** The commands map. */
		protected ConcurrentHashMap<String, RemoteControlCommand> commandsMap = new ConcurrentHashMap<>();
		
		/** The out. */
		private PrintWriter out;
		
		/** The in. */
		private BufferedReader in;
		
		
		/** The command. */
		private RemoteControlCommand remoteControlcommand;
	
		/** The view. */
		protected View view;
		
		
		/**
		 * Instantiates a new CLI.
		 * @param out the out
		 * @param in the in
		 * @param v the view
		 */
		public CLIServer(PrintWriter out, BufferedReader in, View v) {
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
						remoteControlcommand= commandsMap.get(args[0]);		
						remoteControlcommand.setArguments(args);
						view.setUserCommand(remoteControlcommand);

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
		public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commands) {

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
		 * Sets the running.
		 *
		 * @param runing the new running
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
			System.out.println("please enter one of the following commands!!");
			System.out.println();
			System.out.println("command 1: start server ----------------- start the server");
			System.out.println("command 2: stop server ------------------ stop the server");
			System.out.println("command 3: display message -------------- display messages");
			System.out.println("command 3: exit ------------------------- exit");
			System.out.println();
		}
	}

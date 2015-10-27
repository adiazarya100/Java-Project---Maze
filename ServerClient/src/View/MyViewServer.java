package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import Presenter.RemoteControlCommand;

/**
 * The Class MyViewServer.
 */
public class MyViewServer extends Observable implements View{
	
	/** The out. */
	public PrintWriter out;
	
	/** The cli. */
	private CLIServer cliServer;
	
	/** The remote control command. */
	RemoteControlCommand remoteControlCommand;;
	
	/**
	 * Instantiates a new my view server.
	 *
	 * @param out the out
	 * @param in the in
	 */
	public MyViewServer(PrintWriter out, BufferedReader in) {
		this.cliServer = new CLIServer(out, in, this);
		this.out = out;
	
	}

	/* (non-Javadoc)
	 * @see View.View#setUserCommand(Presenter.RemoteControlCommand)
	 */
	@Override
	public void setUserCommand(RemoteControlCommand userCommand) {
		this.remoteControlCommand = userCommand;
		setChanged(); 
		notifyObservers();
		
	}
	
	/* (non-Javadoc)
	 * @see View.View#getCommand()
	 */
	@Override
	public RemoteControlCommand getCommand() {
		
		return this.remoteControlCommand;
	}

	/* (non-Javadoc)
	 * @see View.View#setCommands(java.util.concurrent.ConcurrentHashMap)
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap) {
		cliServer.setCommands(commandMap);
	}

	/* (non-Javadoc)
	 * @see View.View#Display(java.lang.String)
	 */
	@Override
	public void Display(String msg) {
		out.println(msg);
		out.flush();
		
	}
	
	/* (non-Javadoc)
	 * @see View.View#start()
	 */
	@Override
	public void start() {
		//start the cli in new thread
		Thread myThread = new Thread(cliServer);
		myThread.start();

	}
	
	/* (non-Javadoc)
	 * @see View.View#exit()
	 */
	@Override
	public void exit() {
		
		cliServer.setRuning(false);
	}

/*	@Override
	public void saveData(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClient(String Client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeClient(String Client) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void DisplayStatus(String msg) {
		// TODO Auto-generated method stub
		
	}*/

}

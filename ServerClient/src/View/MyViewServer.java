package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import Presenter.RemoteControlCommand;


public class MyViewServer extends Observable implements View{
	
	/** The out. */
	public PrintWriter out;
	
	/** The cli. */
	private CLIServer cliServer;
	
	RemoteControlCommand remoteControlCommand;;
	
	public MyViewServer(PrintWriter out, BufferedReader in) {
		this.cliServer = new CLIServer(out, in, this);
		this.out = out;
	
	}

	@Override
	public void setUserCommand(RemoteControlCommand userCommand) {
		this.remoteControlCommand = userCommand;
		setChanged(); 
		notifyObservers();
		
	}
	
	@Override
	public RemoteControlCommand getCommand() {
		
		return this.remoteControlCommand;
	}

	@Override
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap) {
		cliServer.setCommands(commandMap);
	}

	@Override
	public void Display(String msg) {
		out.println(msg);
		out.flush();
		
	}
	
	@Override
	public void start() {
		//start the cli in new thread
		Thread myThread = new Thread(cliServer);
		myThread.start();

	}
	
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

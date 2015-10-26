package Presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import Model.Enums;
import Model.Model;
import View.View;

public class Presenter implements Observer {


	/** The my model. */
	Model MyModelServer;
	
	/** The my view. */
	View myView;
	
	
	
	
	/**
	 * Instantiates a new presenter.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public Presenter(Model model, View view)
	{
		this.MyModelServer=model;
		this.myView=view;
		ConcurrentHashMap<String, RemoteControlCommand> commandMap=new ConcurrentHashMap<String, RemoteControlCommand>(); 
		commandMap.put("connection", new CheckConnectionStatus());
		commandMap.put("disconnect", new DisconnectUser());
		commandMap.put("start",new StartServer());
		commandMap.put("stop", new StopServer());
		commandMap.put("exit", new exit());

		myView.setCommands(commandMap);
	}
	
	
	

	/**
	 * The Class CheckConnectionStatus.
	 */
	public class CheckConnectionStatus implements RemoteControlCommand {

		/** The params. */
		String[] params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			String client = params[1];
			MyModelServer.getStatusClient(client);

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String[] args) {
			this.params = args;

		}

	}

	/**
	 * The Class DisconnectUser.
	 */
	public class DisconnectUser implements RemoteControlCommand{

		/** The params. */
		String[] params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			String client = params[1];
			MyModelServer.DisconnectClient(client);

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String[] args) {
			this.params = args;

		}

	}

	/**
	 * The Class StartServer.
	 */
	public class StartServer implements RemoteControlCommand{

		/** The params. */
		String[] params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {

			MyModelServer.run();
		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */


		@Override
		public void setArguments(String[] args) {
			this.params = args;
			
		}

	}

	/**
	 * The Class StopServer.
	 */
	public class StopServer implements RemoteControlCommand{

		/** The params. */
		String[] params;
		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			MyModelServer.DisconnectServer();

		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String[] args) {
			this.params = args;
			
		}

	}

	/**
	 * The Class exit.
	 */
	public class exit implements RemoteControlCommand{

		/** The params. */
		String[] params;

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#doCommand()
		 */
		@Override
		public void doCommand() {
			MyModelServer.exit();
		}

		/* (non-Javadoc)
		 * @see presenter.RemoteControlCommand#setArguments(java.lang.String)
		 */
		@Override
		public void setArguments(String[] args) {
			this.params = args;
			
		}

	}



	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {


		if(o instanceof View){

			myView.getCommand().doCommand();
		}

		if(o instanceof Model ){

			//String [] args = MyModelServer.getData();
			String[] args = (String[])arg;
			if(args != null){
				switch(args[0]){

				case Enums.CANNOT_REMOVE_CLIENT:

					if(args[1] == null)
						myView.Display(args[0]);
					else
						myView.Display(args[1]);
					break;

				case Enums.CANNOT_START_SERVER:

					if(args[1] == null)
						myView.Display(args[0]);
					else
						myView.Display(args[1]);
					break;

				case Enums.SERVER_START:
					if(args[1] == null)
						myView.Display(args[0]);
					else
					//	myView.DisplayStatus(args[0] + " " +args[1]);
					break;

				case Enums.CANNOT_DISCONNECT_SERVER:

					if(args[1] == null)
						myView.Display(args[0]);
					else
						myView.Display(args[1]);
					break;

				case Enums.DISCONNECT_SERVER:

					myView.Display(args[0]);
					break;
				case Enums.CLIENT_STATUS:

			/*		if(args[1] != null)
						myView.DisplayStatus(args[0]+ " " + args[1]);
					else*/
						myView.Display(args[0]);
					break;

				case Enums.CLIENT_ADDED:
/*
					myView.addClient(args[1]); */
					break;

				case Enums.CLIENT_REMOVED:

					/*myView.removeClient(args[1]);*/
					break;

		/*		case Enums.CLIENT_UPDATE:
					if(args[1] == null)
						myView.DisplayStatus(args[0]);
					else
						myView.DisplayStatus(args[0] + "\n" + args[1]);

					break;*/
				case Enums.MODEL_ERROR:
					if(args[0] != null)
						myView.Display(args[0]);
					
				case Enums.MODEL_SAVED:
					if(args[0] != null)
						myView.Display(args[0]);
					
				

				case Enums.EXIT:

					if(args[1] == null)
						myView.Display(args[0]);
					else
						myView.Display(args[1]);
					break;

				}
			}
		}


	}



}

package View;

import java.util.concurrent.ConcurrentHashMap;

import Presenter.RemoteControlCommand;

public interface View {



		/**
		 * Gets the command.
		 *
		 * @return the command
		 */
		public RemoteControlCommand getCommand();

		/**
		 * Sets the commands.
		 *
		 * @param commandMap the command map
		 */
		public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap);

		/**
		 * Display.
		 *
		 * @param msg the msg
		 */
		public void Display(String msg);
		
		/**
		 * Sets the user command.
		 *
		 * @param userCommand the new user command
		 */
		public void setUserCommand(RemoteControlCommand userCommand);

		void exit();

		void start();
		

/*		*//**
		 * Save data.
		 *
		 * @param data the data
		 *//*
		public void saveData(String data);

		*//**
		 * Adds the client.
		 *
		 * @param Client the client
		 *//*
		public void addClient(String Client);

		*//**
		 * Removes the client.
		 *
		 * @param Client the client
		 *//*
		public void removeClient(String Client);
*/


/*		*//**
		 * Display status.
		 *
		 * @param msg the msg
		 *//*
		void DisplayStatus(String msg);
*/
		
}

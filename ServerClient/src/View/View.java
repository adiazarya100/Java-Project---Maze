package View;

import java.util.concurrent.ConcurrentHashMap;

import Presenter.RemoteControlCommand;

/**
 * The Interface View.
 */
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

		/**
		 * Exit.
		 */
		void exit();

		/**
		 * Start.
		 */
		void start();
		


}

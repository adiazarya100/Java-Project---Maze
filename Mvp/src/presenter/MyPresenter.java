package presenter;

import generic.Enums;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.demo.searchMaze2DAdapter;
import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * The Class MyPresenter.
 * implementation the behavior of controller
 */
public class MyPresenter implements Presenter {

	/** The model. */
	private Model model;

	/** The view. */
	private View  view;

	/** The commands. */
	HashMap<String, Command> commandsMap;

	/**
	 * Instantiates a new my presenter.
	 *
	 * @param myView the my view
	 * @param myModel the my model
	 */
	public MyPresenter(View myView, Model myModel) {

		this.view = myView;
		this.model = myModel;
		view.setCommands(getCommands()); 
	}


	/* (non-Javadoc)
	 * @see presenter.Presenter#getCommands()
	 */
	@Override
	public HashMap<String, Command> getCommands() {

		this.commandsMap = new HashMap<String, Command>();

		commandsMap.put("dir", new DirCommand());
		commandsMap.put("solve", new SolveModelCommand());
		commandsMap.put("generate", new GenerateModelCommand());
		commandsMap.put("display", new DisplayModelCommand());
		commandsMap.put("save", new SaveModelCommand());
		commandsMap.put("load", new LoadModelCommand());
		commandsMap.put("file", new ModelSizeInFileCommand());//size
		commandsMap.put("maze", new ModelSizeInMemoryCommand());//size
		commandsMap.put("exit", new ExitCommand());

		return commandsMap;
	}



	/**
	 * Gets the commands map.
	 * Will return all the HashMap of String commands.
	 * @return the commands map
	 */
	public HashMap<String, Command> getCommandsMap() {
		return commandsMap;}



	/**
	 * Sets the commands map.
	 * set the commands map in the VIEW LAYER(!) from the controller
	 * @param commandsMap the commands map
	 */
	public void setCommandsMap(HashMap<String, Command> commandsMap) {
		view.setCommands(commandsMap);}



	/**
	 * The Class DirCommand.
	 * this command displays every files in the current path
	 */
	public class DirCommand implements Command {
		
		/** The args. */
		String[] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(){
			try{
				if(args[1] != null){
					String path= args[1];
					view.dirCommand(path);
				}
			}
			catch (ArrayIndexOutOfBoundsException e){
				view.displayString("Error, no arguments");}
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args){
			this.args = args;}
	}



	/**
	 * The Class solve.
	 * this command solve given maze using the BFS or ASTAR algorithm
	 */
	public class SolveModelCommand implements Command{
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {

			if(model.getNameToModel(args[1]) == null){
				view.displayString("The model " + args[1] + " does not exist." );}

			model.ModelSolveing(args[1],args[2]);
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}



	/**
	 * The Class generate3DMaze.
	 * this command generates Maze3d, sending name and 3 dimensions 
	 */
	public class GenerateModelCommand implements Command {
		
		/** The args. */
		String [] args;

		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			//call the generate method & IsNumeric check if the args are integers.
			if(args.length==7 && (isNumeric(args[4])==true) && (isNumeric(args[5])==true) && (isNumeric(args[6])==true))
				model.generateModel(args[3] , Integer.parseInt(args[4]),Integer.parseInt(args[5]),Integer.parseInt(args[6])); 
			else
				System.out.println("Wrong input, please enter command + <name> + 3 argument stand for maze dimensions");}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}

	
	
	/**
	 * The Class saveMaze.
	 * this command gets name and file name and save the compressed maze
	 */
	public class SaveModelCommand implements Command {
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			model.saveModel(args[2], args[3]);
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}
	
	
	
	/**
	 * The Class loadMaze.
	 * this command load compressed maze by given file name and maze name
	 */
	public class LoadModelCommand implements Command {
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			model.loadModel(args[2],args[3]);	
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}
	
	
	
	/**
	 * The Class fileSize.
	 * this command ask for the maze size in file
	 */
	public class ModelSizeInFileCommand implements Command {
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			try{
			long size = model.getModelSizeInFile(args[2]);
			view.displayString("Maze size in file is: "+ size);
			}catch(ArrayIndexOutOfBoundsException e){
				view.displayString("Invalid args");
			}
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}	
	}
	
	
	
	/**
	 * The Class mazeSize.
	 * this command ask for the maze size in memory
	 */
	public class ModelSizeInMemoryCommand implements Command {
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			if(model.getNameToModel(args[2]) != null){
			try {
				long size = model.getModelSizeInMemory(args[2]);
				view.displayString("Maze size in memory is: "+size);
			} catch (IOException e) {
				view.displayString("Invalid arguments");
			} 
			}
			else{
				view.displayString("No such name");
			}
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}
	
	
	
	/**
	 * The Class exit.
	 */
	public class ExitCommand implements Command{
		
		/** The args. */
		String[] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand(){
			if(args[0].equals("exit"))
				model.exit(args[0]);
			else
				System.out.println("wrong input");}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}
	
	
	
	/**
	 * The method start ->from the presenter interface.
	 */
	@Override
	public void start() {
		view.start();

	}
	
	
	
	/**
	 * Checks if is numeric.
	 * this is validation that the user send integer arguments 
	 *
	 * @param str the str
	 * @return true, if is numeric
	 */
	public boolean isNumeric(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	
	
	
	/**
	 * The Class displayMaze.
	 * this command prints the given maze or solution or crossby
	 */
	public class DisplayModelCommand implements Command {
		
		/** The args. */
		String [] args;
		
		/* (non-Javadoc)
		 * @see presenter.Command#doCommand()
		 */
		@Override
		public void doCommand() {
			Searchable<Position> Maze3DSearchable; 
			Searchable<Position> Maze2DSearchable;
			Maze3dAdapter myMaze3D;
			Maze2dAdapter myMaze2D;

			
			switch (args[1]) {
			case "solution":
				if(model.getNameToModel(args[2]) == null){
					view.displayString("No record of " + args[2]+ ". Try to create it first");
				}
				else{
					Solution<Position> solution= model.getSolution(args[2]);
					if (solution!=null){
						view.displaySolution(solution);
					}
					else{
						view.displayString("No solution for " + args[2]+ ". Try to create it first");
					}

				}

				break;

			case "cross":

				String name = args[7];
				String dimention = args[4];
				int section = Integer.parseInt(args[5]);


				Maze2DSearchable =  model.CrossSectionBy(dimention, section, name );
				if(Maze2DSearchable != null){
					Maze2d myMaze2d = ((searchMaze2DAdapter) Maze2DSearchable).getMyMaze(); //searchMaze2DAdapter->see Algorithm Adapters.
					myMaze2D = new Maze2dAdapter(myMaze2d); 								//doing cast to Maze2DSearchable: type Searchable<Position>
					view.displayCrossSectionBy(myMaze2D);
				}
				else{
					view.displayString("Invalid values");	
				}
				break;

			default:
				Maze3DSearchable = model.getNameToModel(args[1]); //this method will return Searchable<T> argument.
				if(Maze3DSearchable != null){
					Maze3d myMaze = ((searchMaze3DAdapter) Maze3DSearchable).getSearchableMaze(); //searchMaze3DAdapter->see Algorithm Adapters.
					myMaze3D = new Maze3dAdapter(myMaze); 			                       		//doing cast to Maze3DSearchable: type Searchable<Position>
					view.displayModel(myMaze3D);
				}
				else{
					view.displayString("Invalid values");	
				}
				break;
			}
		}
		
		/* (non-Javadoc)
		 * @see presenter.Command#setArguments(java.lang.String[])
		 */
		@Override
		public void setArguments(String[] args) {
			this.args = args;}
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		//System.out.println("zzzz");
		if(o instanceof View)
		{
			//System.out.println("vvv");
			if(arg!=null && !arg.equals(Enums.MODEL_ERROR))
			{

				Command command= view.getUserCommand();
				command.doCommand();
			}

		}
		else if(o instanceof Model){

			//System.out.println("mmm");
			String [] args = (String[])arg;
			switch (args[0]){

			case Enums.MODEL_SAVED:

				view.displayString(args[1]+" was saved");

				break;

			case Enums.FILE_NOT_FOUND:

				view.displayString(Enums.FILE_NOT_FOUND);

				break;
			case Enums.NO_MODEL_FOUND:

				view.displayString(Enums.NO_MODEL_FOUND);

				break;
			case Enums.ERROR_CLOSING_FILE:

				view.displayString(Enums.ERROR_CLOSING_FILE);

				break;

			case Enums.MODEL_SOLVED:

				view.displayString(Enums.MODEL_SOLVED);
				view.displaySolution(model.getSolution(args[1]));

				break;

			case Enums.MODEL_LOADED:

				view.displayString(Enums.MODEL_LOADED);


				break;

			case Enums.PROPERTIES_ARE_NO_SET:

				view.displayString(Enums.PROPERTIES_ARE_NO_SET);

				break;

			case Enums.MODEL_GENERATED:
				searchMaze3DAdapter maze = new searchMaze3DAdapter(model.getHM().get(args[1]));
				Maze3dAdapter mazeDrew = new Maze3dAdapter(maze.getSearchableMaze());
				view.displayModel(mazeDrew);

				break;

			case Enums.MODEL_EXIT:

				view.displayString(Enums.MODEL_EXIT);

				break;

			}
		}
	}


}

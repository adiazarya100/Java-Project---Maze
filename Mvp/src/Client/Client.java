package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;


import presenter.Maze2dAdapter;
import presenter.Maze3dAdapter;
import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.Solution;
import generic.Enums;
import generic.ServerEnums;
import model.Model;

/**
 * The Class Client.
 */
public class Client extends Observable implements Model {

	/** The my maze. */
	Maze3d myMaze;
	
	/** The my solution. */
	Solution<Position> mySolution;

	/** The constant args. */
	String [] constantArgs;
	
	/** The preferences. */
	private Properties properties;

	/**
	 * Instantiates a new client model.
	 *
	 * @param properties the properties
	 */
	public Client(Properties properties) {

		this.properties = properties;
		this.constantArgs = new String[2];
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInMemory(java.lang.String)
	 */
	@Override
	public long getModelSizeInMemory(String name) throws IOException {

		String data = name;

		return (int)queryServer(properties.serverIP, properties.serverPort, ServerEnums.GET_MODEL_SIZE_IN_MEMORY, data, "");
	}

	/* (non-Javadoc)
	 * @see model.Model#getModelSizeInFile(java.lang.String)
	 */
	@Override
	public long getModelSizeInFile(String name) {

		String data = name;

		return (int)queryServer(properties.serverIP, properties.serverPort, ServerEnums.GET_MODEL_SIZE_IN_FILE, data, "");
	}

	/* (non-Javadoc)
	 * @see model.Model#saveModel(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveModel(String name, String fileName) {

		String data = name + " " + fileName;

		String [] valid = (String[])queryServer(properties.serverIP, properties.serverPort, ServerEnums.SAVE_MAZE, data, "");
		constantArgs[0] = valid[0];
		constantArgs[1] = fileName;
		setChanged();
		notifyObservers(constantArgs);
	}

	/* (non-Javadoc)
	 * @see model.Model#loadModel(java.lang.String, java.lang.String)
	 */
	@Override
	public Maze3d loadModel(String fileName, String name){
		this.myMaze = null;
		String data = name + " " + fileName;
		this.myMaze = (Maze3d)queryServer(properties.serverIP, properties.serverPort, ServerEnums.LOAD_MAZE, data, "");
		constantArgs[0] = Enums.MODEL_LOADED;
		constantArgs[1]= name;
		setChanged();
		notifyObservers(constantArgs);
		return this.myMaze;

	}

	/* (non-Javadoc)
	 * @see model.Model#solveModel(java.lang.String)
	 */
	@Override
	public void ModelSolveing(String name, String algorithm) {

		String property=null;
		switch(properties.getSolver())
		{
		case BFS:
			property="BFS";
			break;
		case ManhattanASTAR:
			property="MANHATTAN_ASTAR";
			break;
		case EuclidianASTAR:
			property="EUCLIDIAN_ASTAR";
			break;
		default:
			return;
		}

		@SuppressWarnings("unchecked")
		Solution<Position> solution=(Solution<Position>)queryServer(properties.getServerIP(),properties.getServerPort(),ServerEnums.SOLVE_MAZE,name,property);
		if(solution==null)
		{
			constantArgs[0] = ServerEnums.DISCONNECT;
			constantArgs[1] = name;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}

		System.out.println(solution);
		this.mySolution = solution;

		constantArgs[0] = Enums.MODEL_SOLVED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

	}

	/* (non-Javadoc)
	 * @see model.Model#generateModel(java.lang.String, java.lang.String[])
	 */
	@Override
	public void generateModel(String name, int x, int y, int z) {

		String generator=null;
		switch(properties.getGenerator())
		{
		case DFS:
			generator="DFS";
			break;
		case RANDOM:
			generator="RANDOM";
			break;
		default:
			return;
		}
		String line = Integer.toString(x) ;
		String floor = Integer.toString(y) ;
		String column = Integer.toString(z) ;//z x y


		Maze3d myMaze=(Maze3d)queryServer(properties.getServerIP(),properties.getServerPort(),ServerEnums.GENERATE_MAZE,name+","+line+","+floor+","+column ,generator);
		if(myMaze==null)
		{
			constantArgs[0] = ServerEnums.DISCONNECT;
			constantArgs[1] = name;
			setChanged();
			notifyObservers(constantArgs);
			return;
		}
		this.myMaze = myMaze;

		constantArgs[0] = Enums.MODEL_GENERATED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

	}


	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Solution<Position> getSolution(String name) {

		return (Solution<Position>)this.mySolution;
	}

	/* (non-Javadoc)
	 * @see model.Model#getNameToModel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  Searchable<Position> getNameToModel(String name) {


		Maze3dAdapter myMazeAdapter = new Maze3dAdapter(myMaze);

		return (Searchable<Position>) myMazeAdapter; //cast has been made
	}

	/* (non-Javadoc)
	 * @see model.Model#CrossSectionBy(java.lang.String, java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	
	public Searchable<Position> CrossSectionBy(String section, int index, String name) {

		//Maze2d myMaze = (Maze2d)queryServer(preferences.serverIP, preferences.serverPort, ServerConstant.GET_CROSS_SECTION, name, dimention + " "  + section);
		Maze2dAdapter myMazeAdapter = (Maze2dAdapter)queryServer(properties.serverIP, properties.serverPort, ServerEnums.GET_CROSS_SECTION, section, index + "for"  + name);
		constantArgs[0] = Enums.MODEL_GENERATED;
		constantArgs[1] = name;
		setChanged();
		notifyObservers(constantArgs);

		return (Searchable<Position>) myMazeAdapter; //cast has been made
	}

	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */


	/**
	 * Query server.
	 *
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param command the command
	 * @param data the data
	 * @param property the property
	 * @return the object
	 */
	private Object queryServer(String serverIP,int serverPort,String command,String data,String property)
	{
		Object result=null;
		Socket server;			
		try {
			System.out.println("Trying to connect server, IP: " + serverIP + " " + serverPort);
			server = new Socket(serverIP,serverPort);
			PrintWriter writerToServer=new PrintWriter((new OutputStreamWriter(server.getOutputStream())));
			writerToServer.println(command);
			writerToServer.flush();
			writerToServer.println(property);
			writerToServer.flush();
			writerToServer.println(data);
			writerToServer.flush();
			ObjectInputStream inputDecompressed;
			inputDecompressed = new ObjectInputStream(server.getInputStream());
			result=inputDecompressed.readObject();
			if(result.toString().contains("disconnect"))
			{
				setChanged();
				notifyObservers(ServerEnums.DISCONNECT);
			}
			writerToServer.close();
			inputDecompressed.close();
			server.close();
		} catch (ClassNotFoundException | IOException  e) {
			e.printStackTrace();

		}

		return result;

	}



	/* (non-Javadoc)
	 * @see model.Model#getHM()
	 */
	@Override
	public HashMap<String, Maze3d> getHM() {
		// TODO Auto-generated method stub
		return null;
	}



	/* (non-Javadoc)
	 * @see model.Model#exit()
	 */
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}

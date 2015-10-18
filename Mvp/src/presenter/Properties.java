package presenter;

import generic.Enums;

import java.io.Serializable;

/**
 * The Class Properties.
 * This class contains all the properties one should desire to choose.
 * This class implements Serializable since Properties are meant to be written and read and XML's are readable as well.
 */
public class Properties implements Serializable {

/** The Constant serialVersionUID. */
private static final long serialVersionUID = 1L;
	
	

	/**
	 * The Enum MazeGenerator.
	 */
	public enum MazeGenerator{
		
		
		DFS,RANDOM
	}

	/**
	 * The Enum UI.
	 */
	public enum UI{
		
	
		CLI,GUI
	}

	/**
	 * The Enum MazeSolver.
	 */
	public enum MazeSolver{
		
		
		MANHATTAN_ASTAR,EUCLIDIAN_ASTAR,BFS
	}

	/** The pool size. */
	public int poolSize;

	/** The server ip. */
	public String serverIP;

	/** The server port. */
	public int serverPort;

	/** The generator. */
	public MazeGenerator generator;

	/** The ui. */
	public UI ui;

	/** The solver. */
	public MazeSolver solver;
	
	

	/**
	 * Instantiates a new properties.
	 */
	public Properties() {
		
		this.poolSize = Enums.DEFAULT_POOL_SIZE;
		this.ui = UI.GUI;
		this.generator = MazeGenerator.DFS;
		this.solver = MazeSolver.BFS;
	}
	
	

	/**
	 * Instantiates a new properties.
	 *
	 * @param poolSize the pool size
	 * @param serverIP the server ip
	 * @param serverPort the server port
	 * @param generator the generator
	 * @param ui the ui
	 * @param solver the solver
	 */
	public Properties(int poolSize, String serverIP, int serverPort,
			MazeGenerator generator, UI ui, MazeSolver solver) {
		 
		this.poolSize = poolSize;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.generator = generator;
		this.ui = ui;
		this.solver = solver;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Preferences [poolSize=" + poolSize + ", serverIP=" + serverIP
				+ ", serverPort=" + serverPort + ", generator=" + generator
				+ ", ui=" + ui + ", solver=" + solver + "]";
	}


	/**
	 * Gets the server ip.
	 *
	 * @return the server ip
	 */
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * Sets the server ip.
	 *
	 * @param serverIP the new server ip
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * Gets the server port.
	 *
	 * @return the server port
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Gets the generator.
	 *
	 * @return the generator
	 */
	public MazeGenerator getGenerator() {
		return generator;
	}

	/**
	 * Sets the generator.
	 *
	 * @param generator the new generator
	 */
	public void setGenerator(MazeGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Gets the ui.
	 *
	 * @return the ui
	 */
	public UI getUi() {
		return ui;
	}

	/**
	 * Sets the ui.
	 *
	 * @param ui the new ui
	 */
	public void setUi(UI ui) {
		this.ui = ui;
	}

	/**
	 * Gets the solver.
	 *
	 * @return the solver
	 */
	public MazeSolver getSolver() {
		return solver;
	}

	/**
	 * Sets the solver.
	 *
	 * @param solver the new solver
	 */
	public void setSolver(MazeSolver solver) {
		this.solver = solver;
	}

	/**
	 * Gets the pool size.
	 *
	 * @return the pool size
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * Sets the pool size.
	 *
	 * @param poolSize the new pool size
	 */
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}



	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	
}
}


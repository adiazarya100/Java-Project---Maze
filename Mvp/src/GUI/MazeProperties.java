package GUI;


/**
 * The Class MazeProperties.
 */
public class MazeProperties {
	
	/** Number of columns in maze. */
	private int Columns;
	/** Maze Name. */
	private String MazeName;
	
	
	/** Number of rows in maze. */
	private int Rows;
	
	/** The Floors. */
	private int Floors;
	
	
	
	
/*	*//**
 * row source in the maze.
 *
 * @param mazeName the maze name
 * @param rows the rows
 * @param floors the floors
 * @param columns the columns
 *//*
	private int rowSource;
	*//**
	 * column source in the maze
	 *//*
	private int colSource;
	*//**
	 *  row goal in maze
	 *//*
	private int rowGoal;
	*//**
	 * col goal in maze
	 *//*
	private int colGoal;*/
	
	public MazeProperties(String mazeName, int rows, int floors, int columns) {
		MazeName = mazeName;
		Rows = rows;
		Floors = floors;
		Columns = columns;
		
	}
	
	/**
	 * Instantiates a new maze properties.
	 */
	public MazeProperties() {
		MazeName = "Deafult";
		Rows = 5;
		Columns = 5;
		Floors = 5;
	}
	
	
	/**
	 * getter.
	 *
	 * @return the maze name
	 */
	public String getMazeName() {
		return MazeName;
	}
	
	/**
	 * setter.
	 *
	 * @param mazeName the new maze name
	 */
	public void setMazeName(String mazeName) {
		this.MazeName = mazeName;
	}
	
	/**
	 *  
	 * 
	 * getter.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return Rows;
	}
	
	/**
	 * setter.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows) {
		this.Rows = rows;
	}
	
	/**
	 * getter.
	 *
	 * @return the columns
	 */
	public int getColumns() {
		return Columns;
	}
	
	/**
	 * setter.
	 *
	 * @param cols the new columns
	 */
	public void setColumns(int cols) {
		this.Columns = cols;
	}
	
	
	/**
	 * Gets the floors.
	 *
	 * @return the floors
	 */
	public int getFloors() {
		return Floors;
	}

	/**
	 * Sets the floors.
	 *
	 * @param floors the new floors
	 */
	public void setFloors(int floors) {
		Floors = floors;
	}
}
/*
	*//**
	 * getter.
	 *
	 * @return the row source
	 *//*
	public int getRowSource() {
		return rowSource;
	}
	
	*//**
	 * setter.
	 *
	 * @param rowSource the new row source
	 *//*
	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}
	
	*//**
	 * getter.
	 *
	 * @return the col source
	 *//*
	public int getColSource() {
		return colSource;
	}
	
	*//**
	 * setter.
	 *
	 * @param colSource the new col source
	 *//*
	public void setColSource(int colSource) {
		this.colSource = colSource;
	}
	
	*//**
	 * getter.
	 *
	 * @return the row goal
	 *//*
	public int getRowGoal() {
		return rowGoal;
	}
	
	*//**
	 * setter.
	 *
	 * @param rowGoal the new row goal
	 *//*
	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}
	
	*//**
	 * getter.
	 *
	 * @return the col goal
	 *//*
	public int getColGoal() {
		return colGoal;
	}
	
	*//**
	 * setter.
	 *
	 * @param colGoal the new col goal
	 *//*
	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}
}
*/
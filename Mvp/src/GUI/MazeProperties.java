package GUI;

public class MazeProperties {
	/**
	 * Maze Name
	 */
	private String MazeName;
	/**
	 * Number of rows in maze
	 */
	private int Rows;
	/**
	 * Number of columns in maze
	 */
	private int Columns;
	
	
	private int Floors;
	/**
	 * row source in the maze
	 */
	private int rowSource;
	/**
	 * column source in the maze
	 */
	private int colSource;
	/**
	 *  row goal in maze
	 */
	private int rowGoal;
	/**
	 * col goal in maze
	 */
	private int colGoal;
	
	public MazeProperties(String mazeName, int rows, int floors, int columns) {
		MazeName = mazeName;
		Rows = rows;
		Floors = floors;
		Columns = columns;
		
	}
	
	public MazeProperties() {
		MazeName = "Deafult";
		Rows = 5;
		Columns = 5;
		Floors = 5;
	}
	
	
	/**
	 * 
	 * getter
	 * 
	 */
	public String getMazeName() {
		return MazeName;
	}
	/**
	 * setter
	 * 
	 */
	public void setMazeName(String mazeName) {
		this.MazeName = mazeName;
	}
	/** 
	 * 
	 * getter
	 */
	public int getRows() {
		return Rows;
	}
	/**
	 * setter
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
	public int getFloors() {
		return Floors;
	}

	public void setFloors(int floors) {
		Floors = floors;
	}

	/**
	 * 
	 *setter
	 */
	public void setColumns(int cols) {
		this.Columns = cols;
	}
	/**
	 * getter
	 */
	public int getRowSource() {
		return rowSource;
	}
	/**
	 * setter
	 */
	public void setRowSource(int rowSource) {
		this.rowSource = rowSource;
	}
	/**
	 * getter
	 */
	public int getColSource() {
		return colSource;
	}
	/**
	 * setter
	 */
	public void setColSource(int colSource) {
		this.colSource = colSource;
	}
	/**
	 * getter
	 */
	public int getRowGoal() {
		return rowGoal;
	}
	/**
	 * setter
	 */
	public void setRowGoal(int rowGoal) {
		this.rowGoal = rowGoal;
	}
	/**
	 * getter
	 */
	public int getColGoal() {
		return colGoal;
	}
	
	/**
	 * setter.
	 *
	 * @param colGoal the new col goal
	 */
	public void setColGoal(int colGoal) {
		this.colGoal = colGoal;
	}
}

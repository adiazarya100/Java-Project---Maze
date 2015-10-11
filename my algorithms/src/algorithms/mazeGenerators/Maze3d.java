package algorithms.mazeGenerators;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;



// TODO: Auto-generated Javadoc
/** Maze3d is 3 dimensional array represent maze game.*/

public class Maze3d{

	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The z. */
	private int z;
	
	/** The maze. */
	protected int[][][] maze;
	
	/** The goal position. */
	Position startPosition, goalPosition;
	
	/**
	 * Instantiates a new maze3d.
	 * Byte array constructor
	 * @param array the array
	 */
	//constructor from byte array to default
	
	public Maze3d(byte[] array){
		//the first 3 places in the "byte array" stands for the start position (see toByteArray method)
		this.startPosition= new Position(ByteBuffer.wrap(Arrays.copyOfRange(array, 0, 4)).getInt(),ByteBuffer.wrap(Arrays.copyOfRange(array, 4, 8)).getInt(),ByteBuffer.wrap(Arrays.copyOfRange(array, 8, 12)).getInt());
		//the next 3 places in the "byte array" stands for the goal position (see toByteArray method)
		this.goalPosition= new Position(ByteBuffer.wrap(Arrays.copyOfRange(array, 12, 16)).getInt(),ByteBuffer.wrap(Arrays.copyOfRange(array, 16, 20)).getInt(),ByteBuffer.wrap(Arrays.copyOfRange(array, 20, 24)).getInt());
		//the first 3 places in the "byte array" stands for the maze dimensions size x,y,z (see toByteArray method)
		this.x=ByteBuffer.wrap(Arrays.copyOfRange(array, 24, 28)).getInt();
		this.y=ByteBuffer.wrap(Arrays.copyOfRange(array, 28, 32)).getInt();;
		this.z=ByteBuffer.wrap(Arrays.copyOfRange(array, 32, 36)).getInt();
		this.maze = new int[this.x][this.y][this.z];
		int m = 36;

	for(int i=0;i<this.getY();i++)
	{
			for(int j=0;j<this.getX();j++)
		{
				for(int g=0;g<this.getZ();g++)
				{
				maze[j][i][g]=array[m];
				m++;
				}
		}		
	}
}

	
	/**
	 * Instantiates a new maze3d.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	//constructor
	public Maze3d(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		maze = new int[x][y][z];
		this.startPosition = null;
		this.goalPosition = null;
	}

	/**
	 * Sets the value.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param value the value
	 */
	public void setValue(int x, int y, int z, int value){
		if (value!=0 && value!=1){
			System.out.println("Invalid value\n");
		}
		else{
			this.maze[x][y][z]=value;
		}
	}
	//getters and setters 
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	//constructors
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Gets the maze3d.
	 *
	 * @return the maze3d
	 */
	public int[][][] getMaze3d() {
		return maze;
	}

	/**
	 * Sets the maze3d.
	 *
	 * @param maze3d the new maze3d
	 */
	public void setMaze3d(int[][][] maze3d) {
		this.maze = maze3d;
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public int[][][] getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}

	/**
	 * Gets the start position.
	 *
	 * @return the start position
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/**
	 * Sets the start position.
	 *
	 * @param startPosition the new start position
	 */
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Gets the goal position.
	 *
	 * @return the goal position
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}

	
	/**
	 * Sets the goal position.
	 *
	 * @param goalPosition the new goal position
	 */
	public void setgoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}

	
	/**
	 *  all the possible moves from your current position in the maze.
	 *
	 * @param p the p
	 * @return the possible moves
	 */
	public String[] getPossibleMoves(Position p) {
	
		ArrayList<Position> possibleMoves = new ArrayList<Position>();
		//for each direction make sure you're not get out of the maze bounds
		if ((this.getX() > p.getX() + 1)
				&& (maze[p.getX() + 1][p.getY()][p.getZ()] == 0)) {
			possibleMoves.add(new Position(p.getX() + 1, p.getY(), p.getZ()));
		}

		if ((0 <= p.getX() - 1)
				&& (maze[p.getX() - 1][p.getY()][p.getZ()] == 0)) {
			possibleMoves.add(new Position(p.getX() - 1, p.getY(), p.getZ()));
		}

		if ((this.getY() > p.getY() + 1)
				&& (maze[p.getX()][p.getY() + 1][p.getZ()] == 0)) {
			possibleMoves.add(new Position(p.getX(), p.getY() + 1, p.getZ()));
		}

		if ((0 <= p.getY() - 1)
				&& (maze[p.getX()][p.getY() - 1][p.getZ()] == 0)) {
			possibleMoves.add(new Position(p.getX(), p.getY() - 1, p.getZ()));
		}

		if ((this.getZ() > p.getZ() + 1)
				&& (maze[p.getX()][p.getY()][p.getZ() + 1] == 0)) {
			possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ() + 1));
		}

		if ((0 <= p.getZ() - 1)
				&& (maze[p.getX()][p.getY()][p.getZ() - 1] == 0)) {
			possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ() - 1));
		}

		String moves[] = new String[possibleMoves.size()];
		int i=0;
		for (Position r : possibleMoves) {
			moves[i] = r.getXyz();
			i++;
			
		}
		return moves;
	}

	/**
	 * Gets the cross section by x.
	 *
	 * @param Line the line
	 * @return the cross section by x
	 */
/*	public int[][] getCrossSectionByX(int x) { // Cross section BY X
		if (x < 0 || x >= this.getX()) {
			IndexOutOfBoundsException e = new IndexOutOfBoundsException();
			throw e;
		} else {
			int[][] maze2dx = new int[maze[1].length][maze[0][1].length];
			for (int i = 0; i < maze2dx.length; i++) {
				for (int j = 0; j < maze2dx[0].length; j++) {
					maze2dx[i][j] = maze[x][i][j];
				}
			}
			return maze2dx;

		}
	}*/
	
	public int[][] getCrossSectionByX(int Line) {   //for the value of X
		
		//Will throw exception about-> negative floor or input is bigger then value. 
		if (Line < 0 || (Line >= this.getX()) ) {
			IndexOutOfBoundsException Exception = new IndexOutOfBoundsException();
			throw Exception;
		}
		int [][] Maze2dX = new int[this.getY()][this.getZ()]; //
		
			for(int i=0;i< this.getY() ;i++)
			{
				System.out.println();
				for(int j=0;j< this.getZ();j++)
				{
					Maze2dX[i][j] = maze[Line][i][j];
				}
			}  
			return Maze2dX;
		}

	/**
	 * Gets the cross section by y.
	 *
	 * @param floor the floor
	 * @return the cross section by y
	 */
/*	public int[][] getCrossSectionByY(int y) { // Cross section BY Y
		if (y < 0 || y >= this.getY()) {
			IndexOutOfBoundsException e = new IndexOutOfBoundsException();
			throw e;
		} else {
			int[][] maze2dy = new int[maze.length][maze[0][1].length];
			for (int i = 0; i < maze2dy.length; i++) {
				for (int j = 0; j < maze2dy[0].length; j++) {
					maze2dy[i][j] = maze[i][y][j];
				}
			}
			return maze2dy;
		}
	}*/
	
	public int[][] getCrossSectionByY(int floor) { //for the value of Y
		
		//Will throw exception about-> negative floor or input is bigger then value. 
		if (floor < 0 || (floor >= this.getY()) ) {
			IndexOutOfBoundsException Exception = new IndexOutOfBoundsException();
			throw Exception;
		}
		int [][] Maze2dY = new int[this.getX()][this.getZ()]; 
		
			for(int i=0;i< this.getX() ;i++)
			{
				System.out.println();
				for(int j=0;j< this.getZ();j++)
				{
					Maze2dY[i][j] = maze[i][floor][j]; 
				}
			}  
			return Maze2dY;
		}

	/**
	 * Gets the cross section by z.
	 *
	 * @param Column the column
	 * @return the cross section by z
	 */
/*	public int[][] getCrossSectionByZ(int z) { // Cross section BY Z
		if (z < 0 || z >= this.getZ()) {
			IndexOutOfBoundsException e = new IndexOutOfBoundsException();
			throw e;
		} else {
			int[][] maze2dz = new int[maze.length][maze[1].length];
			for (int i = 0; i < maze2dz.length; i++) {
				for (int j = 0; j < maze2dz[0].length; j++) {
					maze2dz[i][j] = maze[i][j][z];
				}
			}
			return maze2dz;
		}
	}*/

	public int[][] getCrossSectionByZ(int Column) { //for the value of Z
		
		//Will throw exception about-> negative floor or input is bigger then value. 
		if (Column < 0 || (Column >= this.getY()) ) {
			IndexOutOfBoundsException Exception = new IndexOutOfBoundsException();
			throw Exception;
		}
		int [][] Maze2dZ = new int[this.getX()][this.getY()]; //
		
			for(int i=0;i< this.getX() ;i++)
			{
				System.out.println();
				for(int j=0;j< this.getY();j++)
				{
					Maze2dZ[i][j] = maze[i][j][Column];
				}
			}  
			return Maze2dZ;
		}


	/**
	 * Prints the maze.
	 */
//	public void print() {
//	for (int i = 0; i < x; i++) {
//			System.out.println();
//			for (int j = 0; j < y; j++) {
//				System.out.println();
//				for (int k = 0; k < z; k++) {
//					System.out.print(maze[j][i][k]);
//				}
//
//			}
//		}
//	}

	
	public void print() {
		for(int i=0;i<this.getY();i++)
		{
			if(i!=0)
				System.out.println();
			for(int j=0;j<this.getX();j++)
			{
				System.out.println();
				for(int g=0;g<this.getZ();g++)
				{
					System.out.print(maze[j][i][g]);
				}
			}
		}
	}
	
	/**
	 * To byte array.
	 *
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] toByteArray() throws IOException{
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    int startPosArray[] = getStartPosition().split();
		    int goalPosArray[]=getGoalPosition().split();
		    for(int i=0;i<startPosArray.length;i++)
		    {
		    	dos.write(ByteBuffer.allocate(4).putInt(startPosArray[i]).array()); 
		    	dos.flush();
		    }
		    for(int i=0;i<goalPosArray.length;i++)
		    {
		    	dos.write(ByteBuffer.allocate(4).putInt(goalPosArray[i]).array()); 
		    	dos.flush();
		    }
		    dos.write(ByteBuffer.allocate(4).putInt(x).array());
		    dos.flush();
		    dos.write(ByteBuffer.allocate(4).putInt(y).array());
		    dos.flush();
		    dos.write(ByteBuffer.allocate(4).putInt(z).array());
		    dos.flush();
			for(int i=0;i<this.getY();i++)
			{
				for(int j=0;j<this.getX();j++)
				{
					for(int g=0;g<this.getZ();g++)
					{
						dos.write(this.maze[j][i][g]);
						dos.flush();
					}
				}
			}
		    return bos.toByteArray();
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Maze3d other = (Maze3d) obj;
		if (goalPosition == null) {
			if (other.goalPosition != null)
				return false;
		} else if (!goalPosition.equals(other.goalPosition))
			return false;
		if (!Arrays.deepEquals(maze, other.maze))
			return false;
		if (startPosition == null) {
			if (other.startPosition != null)
				return false;
		} else if (!startPosition.equals(other.startPosition))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	/**
	 * Generate walls.
	 */


	//public void walls(int x, int y, int z ,int gx, int gy, int gz  ){
	public void walls(Position start, Position goal){
		int i=0;
		int j=0;
		int z = 0;
		
	
		
		for(z=0;z<this.getY();z++){
			
		for(i=0;i< this.getZ() ;i++){
			for(j=0;j<this.getX();j+=this.getX()-1){
				maze[j][z][i] =1;}
		}
		
		for(i=0;i< this.getZ() ;i+=this.getZ()-1){
			for(j=0;j<this.getX();j++){
				maze[j][z][i] =1;}
		}
	
		}
		maze[goal.getX()+2][goal.getY()][goal.getZ()+1] =0;
		//Maze[start.getLine()-1][start.getFloor()][start.getColumn()] =0;
		
	}

//************************************ update: new methods for gui *********************//
	
	
	/**
	 * Gets the cell value.
	 * 
	 * @param z
	 *            -floor
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 * @return the cell value
	 */
	public int getCellValue(int x, int y, int z) {
		if(outOfRange(x, y, z)==true){
		return this.maze[x][y][z];
		}
		else 
			return 1;
	}

	
	
	
	
	/**
	 * Sets the cell value.
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 * @param num
	 */
	public void setCellValue(int x, int y, int z, int num) {
		if(outOfRange(x, y, z))
		this.maze[x][y][z] = num;
	}

	/**
	 * Position to int.
	 *
	 * @param Position
	 * @return value
	 */
	
	
	/**
	 * Out of range validation
	 *
	 * @param z
	 *            -height
	 * @param x
	 *            - length
	 * @param y
	 *            - width
	 * @return the boolean
	 */
/*	public Boolean outOfRange(int x, int y, int z) {

		if (x >= 0 && y >= 0 && z >= 0 && x < this.maze.length
				&& z < this.maze[0].length && y < this.maze[0][0].length) {
			return true;
		} else
			return false;
	}
*/
	public Boolean outOfRange(int x, int y, int z) {

		if (x >= 0 && y >= 0 && z >= 0 && x < this.getX()
				&& z < this.getZ() && y < this.getY()) {
			return true;
		} else
			return false;
	}

}


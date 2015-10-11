package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


/**
 * The Class DFS.
 * Represent DFS Algo': In this class we generate maze using the DFS algorithm. 
 * Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures.
 *  One starts at the root (selecting some arbitrary node as the root in the case of a graph)
 *  and explores as far as possible along each branch before backtracking.
 *  this class extends abstractMaze3dGenerator */

public class DFS extends abstractMaze3dGenerator{
	/** The maze. */
	private Maze3d mg;

	/** The temporary maze. */
	private int[][][] tempMaze;
	
	/** The temp maze2. */
	private int[][][] tempMaze2;

	/** The moves. */
	private ArrayList<Position> moves;
	
	/** The visited cells. */
	private boolean[][][] visitedCells;

	/**
	 * generates the maze using DFS (depth first search) algorithm .
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the maze3d
	 * @see algorithms.mazeGenerators.AbstractMaze3dGenerator#generate(int, int, int)
	 */
	@Override
	public Maze3d generate(int x, int y, int z) {
		mg = new Maze3d(x, y, z);
		tempMaze2 = new int[x][y][z];
				
		tempMaze = new int[x-2][y][z-2];
		visitedCells = new boolean[x-2][y][z-2];
		int row,column,height=0; // row = x,height = y,column = z
		Random rand = new Random();
		//Random randGoal = new Random();
		boolean goal = false;

		//Initializing the maze with 1's (all cells are walls at the start)
		while(height<y){ 
			for(row=0;row<x-2;row++)
			{
				for(column=0;column<z-2;column++)
				{
					tempMaze[row][height][column] = 1;
					visitedCells[row][height][column] = false;
				}
			}
			height++;
		}
		

		//Creating the maze from a random start point
		row = 0; 
		height = rand.nextInt(y);
		column = 0;

		//Initializing start position from the random raffle of numbers
		mg.setStartPosition(new Position(row, height, column) ); //set start at the middle of the wall with random floor.

		tempMaze[row][height][column] = 0; //set start to 0
		visitedCells[row][height][column] = true;


		//Generating the maze using DFS algorithm - using stack,boolean[][][] for visited cells and array list to save the moves
		Stack<Position> stack = new Stack<Position>();
		moves = new ArrayList<Position>();
		Position currentPosition = mg.getStartPosition();//new Position(row, height, column); //at the beginning the currentPosition is the startPosition
		stack.push(currentPosition);

		while(!stack.empty())
		{
			moves = getPossibleMoves(currentPosition);
			if(moves != null){
				int size = moves.size();
				int randPlace = rand.nextInt(size); //choose a random neighbor
				Position nextPosition = moves.get(randPlace);
				tempMaze[nextPosition.getX()][nextPosition.getY()][nextPosition.getZ()] = 0;
				visitedCells[nextPosition.getX()][nextPosition.getY()][nextPosition.getZ()] = true;

				if((nextPosition.getX()==mg.getX()-3/* || nextPosition.getColumn()==(mg.getColumn()-3)*/) /*&& nextPosition.getColumn()!=0 &&nextPosition.getLine()!=0*/ && !goal){
					mg.setgoalPosition(nextPosition);
					goal = true;

				}

				currentPosition = nextPosition;
				stack.push(currentPosition);
			}
			else{
				currentPosition = stack.pop();
			}

		}
				
		int he =0;
		int lineNew =1;
		int columnNew=1;
 		
 		while(he<y){ 
			for(lineNew=1;lineNew<x-1;lineNew++)
			{
				for(columnNew=1;columnNew<z-1;columnNew++)
				{
					tempMaze2[lineNew][he][columnNew] = tempMaze[lineNew-1][he][columnNew-1];
					
				}
			}
			he++;
		}

		
		Position reGoal =new Position(mg.getGoalPosition().getX()+1,mg.getGoalPosition().getY(), mg.getGoalPosition().getZ()+1);
		//tempMaze2[mg.getGoal().getLine()+2][mg.getGoal().getFloor()][mg.getGoal().getColumn()] =0;
		 mg.setMaze(tempMaze2);
		 
		 mg.walls(mg.getStartPosition(),mg.getGoalPosition());
		 mg.setStartPosition(new Position(1, height, 1));
		 mg.setgoalPosition(reGoal);
		 return mg;
	}

	//get possible moves for the best first search algorithm, checks if its possible to moves all 6 ways
	/**
	 * Gets the possible and valid moves.
	 *
	 * @param currentState the current state
	 * @return the possible moves
	 */
	//it isn't possible to move if we already found 2 possible neighbors or the neighbor was already spotted and changed to 0
	private ArrayList<Position> getPossibleMoves(Position currentState){


		int a = currentState.getX();
		int b = currentState.getY();
		int c = currentState.getZ();

		ArrayList<Position> AllNeighboursList = new ArrayList<Position>();
		ArrayList<Position> NeighboursList = null;

		int xn = a-1;
		int xp = a+1;
		int yn = b-1;
		int yp = b+1;
		int zn = c-1;
		int zp = c+1;

		//for each neighbors check if he's in limit on the maze and if he has more than 1 neighbors that has been visited
		//Check LEFT
		if (xn>=0){

			if(!visitedCells[xn][b][c]&&(yn<0 || !visitedCells[xn][yn][c])&&(yp==mg.getY() || !visitedCells[xn][yp][c])&&(zn<0 || !visitedCells[xn][b][zn])&&(zp==mg.getZ()-2 || !visitedCells[xn][b][zp]))
				AllNeighboursList.add(new Position(xn, b, c));

		}
		//Check RIGHT
		if (xp<mg.getX()-2){

			if(!visitedCells[xp][b][c]&&(yn<0 || !visitedCells[xp][yn][c])&&(yp==mg.getY() || !visitedCells[xp][yp][c])&&(zn<0 || !visitedCells[xp][b][zn])&&(zp==mg.getZ()-2 || !visitedCells[xp][b][zp]))
				AllNeighboursList.add(new Position(xp, b, c));

		}
		//Check DOWN
		if (yn>=0){

			if(!visitedCells[a][yn][c]&&(xn<0 || !visitedCells[xn][yn][c])&&(xp==mg.getX()-2 || !visitedCells[xp][yn][c])&&(zn<0 || !visitedCells[a][yn][zn])&&(zp==mg.getZ()-2 || !visitedCells[a][yn][zp]))
				AllNeighboursList.add(new Position(a, yn, c));

		}
		//Check UP
		if (yp<mg.getY()){

			if(!visitedCells[a][yp][c]&&(xn<0 || !visitedCells[xn][yp][c])&&(xp==mg.getX()-2 || !visitedCells[xp][yp][c])&&(zn<0 || !visitedCells[a][yp][zn])&&(zp==mg.getZ()-2 || !visitedCells[a][yp][zp]))
				AllNeighboursList.add(new Position(a, yp, c));

		}
		//Check BACKWARD
		if (zn>=0){

			if(!visitedCells[a][b][zn]&&(yn<0 || !visitedCells[a][yn][zn])&&(yp==mg.getY() || !visitedCells[a][yp][zn])&&(xn<0 || !visitedCells[xn][b][zn])&&(xp==mg.getX()-2 || !visitedCells[xp][b][zn]))
				AllNeighboursList.add(new Position(a, b, zn));

		}
		//Check FORWARD
		if (zp<mg.getZ()-2){
			if(!visitedCells[a][b][zp]&&(yn<0 || !visitedCells[a][yn][zp])&&(yp==mg.getY() || !visitedCells[a][yp][zp])&&(xn<0 || !visitedCells[xn][b][zp])&&(xp==mg.getX()-2 || !visitedCells[xp][b][zp]))
				AllNeighboursList.add(new Position(a, b, zp));

		}

		if(AllNeighboursList.size()>0)
			NeighboursList = AllNeighboursList;


		return NeighboursList;

	}

}
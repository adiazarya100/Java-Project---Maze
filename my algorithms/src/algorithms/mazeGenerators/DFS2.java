package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DFS2 extends abstractMaze3dGenerator {

	/** The maze. */
	private Maze3d mg;
	
	/** The temporary maze. */
	private int[][][] tempMaze;
	
	/** The moves. */
	private ArrayList<Position> moves;
	
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
		//mg.walls();
		tempMaze = new int[x][y][z];
	    visitedCells = new boolean[x][y][z];
		int row,column,height=0; // row = x,height = y,column = z
		Random rand = new Random();
		boolean goal = false;
		
		//Initializing the maze with 1's (all cells are walls at the start)
		while(height<y){ 
			for(row=0;row<x;row++)
			{
				for(column=0;column<z;column++)
				{
					tempMaze[row][height][column] = 1;
					visitedCells[row][height][column] = false;
				}
			}
			height++;
		}
		
	/*	int a=0;
		int b=0;
		int c = 0;



		for(c=0;c<mg.getY();c++){
			for(a=0;a< mg.getZ() ;a++){
				for(b=0;b<mg.getX();b+=mg.getX()-1){
					visitedCells[b][c][a] =true;}
			}
			for(a=0;a< mg.getZ() ;a+=mg.getZ()-1){
				for(b=0;b<mg.getX();b++){
					
					visitedCells[b][c][a] =true;}}}*/
		
		//Creating the maze from a random start point
		row = 0; 
		height = rand.nextInt(y);
		column = 0;
		//column = rand.nextInt(z);
		//if the Z is one of the farthest maze borders we will randomize X to be any of the possible lines, else we will just randomize Z and X will stay static row 0
//		if(column == 0 || column == z-1)
//		{
//			row = rand.nextInt(x);
//		}
		
		//Initializing start position from the random raffle of numbers
		mg.setStartPosition(new Position(row, height, column));
		tempMaze[row][height][column] = 0;	
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
				
				if(nextPosition.getX()==mg.getX()-1 || nextPosition.getZ()==mg.getZ()-1 && !goal){
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
		mg.setMaze(tempMaze);
		//mg.walls();
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

				if(!visitedCells[xn][b][c]&&(yn<0 || !visitedCells[xn][yn][c])&&(yp==mg.getY() || !visitedCells[xn][yp][c])&&(zn<0 || !visitedCells[xn][b][zn])&&(zp==mg.getZ() || !visitedCells[xn][b][zp]))
					AllNeighboursList.add(new Position(xn, b, c));
			
			}
			//Check RIGHT
			if (xp<mg.getX()){

				if(!visitedCells[xp][b][c]&&(yn<0 || !visitedCells[xp][yn][c])&&(yp==mg.getY() || !visitedCells[xp][yp][c])&&(zn<0 || !visitedCells[xp][b][zn])&&(zp==mg.getZ() || !visitedCells[xp][b][zp]))
					AllNeighboursList.add(new Position(xp, b, c));
			
			}
			//Check DOWN
			if (yn>=0){

				if(!visitedCells[a][yn][c]&&(xn<0 || !visitedCells[xn][yn][c])&&(xp==mg.getX() || !visitedCells[xp][yn][c])&&(zn<0 || !visitedCells[a][yn][zn])&&(zp==mg.getZ() || !visitedCells[a][yn][zp]))
					AllNeighboursList.add(new Position(a, yn, c));
				
			}
			//Check UP
			if (yp<mg.getY()){
		
				if(!visitedCells[a][yp][c]&&(xn<0 || !visitedCells[xn][yp][c])&&(xp==mg.getX() || !visitedCells[xp][yp][c])&&(zn<0 || !visitedCells[a][yp][zn])&&(zp==mg.getZ() || !visitedCells[a][yp][zp]))
					AllNeighboursList.add(new Position(a, yp, c));
				
			}
			//Check BACKWARD
			if (zn>=0){
				
				if(!visitedCells[a][b][zn]&&(yn<0 || !visitedCells[a][yn][zn])&&(yp==mg.getY() || !visitedCells[a][yp][zn])&&(xn<0 || !visitedCells[xn][b][zn])&&(xp==mg.getX() || !visitedCells[xp][b][zn]))
					AllNeighboursList.add(new Position(a, b, zn));
				
			}
			//Check FORWARD
			if (zp<mg.getZ()){
				if(!visitedCells[a][b][zp]&&(yn<0 || !visitedCells[a][yn][zp])&&(yp==mg.getY() || !visitedCells[a][yp][zp])&&(xn<0 || !visitedCells[xn][b][zp])&&(xp==mg.getX() || !visitedCells[xp][b][zp]))
					AllNeighboursList.add(new Position(a, b, zp));
				
			}
			
			if(AllNeighboursList.size()>0)
				NeighboursList = AllNeighboursList;
			
			return NeighboursList;
			
		}
}
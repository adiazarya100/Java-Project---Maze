package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/** Represent DFS Algo': In this class we generate maze using the DFS algorithm. 
 * Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures.
 *  One starts at the root (selecting some arbitrary node as the root in the case of a graph)
 *  and explores as far as possible along each branch before backtracking.
 *  this class extends abstractMaze3dGenerator */



public class MyMaze3dGenerator extends abstractMaze3dGenerator {
		
	/** The maze. */
	private Maze3d maze;
	
	/** The neighbors. */
	boolean [][][]neighbors;
	
	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.abstractMaze3dGenerator#generate(int, int, int)
	 */
	@Override
	public Maze3d generate(int x, int y, int z) {
	
		maze=new Maze3d(x,y,z);
		int row=0, column=0,floor=0;
		Random rand = new Random();
		neighbors = new boolean[x][y][z];
		int height=0;
		//set all the maze to 1`
		//set all the neighbors to false
		//set all the maze to 1
		//set all the neighbors to false		
		while(height<y)
		{
			for (int j = 0; j < x; j++) 
			{
				for (int k = 0; k < z; k++) {
					maze.maze[j][height][k]=1;
					neighbors[j][height][k]=false; //not visited yet
				}
			}
			height++;
		}
		
		
		column = rand.nextInt(z);
		//System.out.println(column);
		floor = rand.nextInt(y);
		//System.out.println(floor);
        maze.startPosition= new Position(row, floor, column); //define start point
        //System.out.println("("+row+","+floor+","+column+")");
        maze.setValue(row, floor, column, 0);
		Position position = new Position(row, floor, column);
		Stack<Position> s= new Stack <Position>();
		ArrayList<Position>possible=new ArrayList<Position>();
		s.add(maze.startPosition);
		while(s.isEmpty()!= true)
		{
			position=s.pop();
	//        System.out.println("("+position.getX()+","+position.getY()+","+position.getZ()+")");

			if(neighbors[position.getX()][position.getY()][position.getZ()]!=true)
			{
				neighbors[position.getX()][position.getY()][position.getZ()]=true;
				possible = getPossibleMoves(position);
				if(possible.isEmpty() != true)//change to discovered
				{
					maze.maze[position.getX()][position.getY()][position.getZ()]=0;    //set wall from 1 to 0
						for(int i=0; i<possible.size();i++)
						{
							s.add(possible.get(i));
						}
				}
			}
		}
				
		//maze.goalPosition=new Position(position.getX(), position.getY(), position.getZ());
		int xr= rand.nextInt(x);
		int yr= rand.nextInt(y);
		int zr =rand.nextInt(z);
		maze.goalPosition=new Position(xr,yr,zr);
		maze.setValue(xr, yr, zr, 0);
		return maze;
	}
	
	
	

	/**
	 * Gets the possible moves.
	 *
	 * @param p the p
	 * @return the possible moves
	 */
	public ArrayList<Position> getPossibleMoves(Position p) {
		ArrayList<Position> possibleMoves = new ArrayList<Position>();
		int counter=0;
		if((maze.getX()>p.getX()+1)&&(maze.maze[p.getX()+1][p.getY()][p.getZ()]==1) && (neighbors[p.getX()+1][p.getY()][p.getZ()]==false))
		{
			possibleMoves.add(new Position(p.getX()+1, p.getY(), p.getZ()));
		}
		else if((maze.getX()>p.getX()+1)&& (neighbors[p.getX()+1][p.getY()][p.getZ()]==true))
		{
			counter++;
		}
			
		if((0<=p.getX()-1)&&(maze.maze[p.getX()-1][p.getY()][p.getZ()]==1) && (neighbors[p.getX()-1][p.getY()][p.getZ()]==false))
		{
			possibleMoves.add(new Position(p.getX()-1, p.getY(), p.getZ()));
		}
		else if((0<=p.getX()-1)&& (neighbors[p.getX()-1][p.getY()][p.getZ()]==true))
		{
			counter++;
		}
		
		if((maze.getY()>p.getY()+1)&&(maze.maze[p.getX()][p.getY()+1][p.getZ()]==1) && (neighbors[p.getX()][p.getY()+1][p.getZ()]==false))
		{
			possibleMoves.add(new Position(p.getX(), p.getY()+1, p.getZ()));
		}
		else if((maze.getY()>p.getY()+1)&& (neighbors[p.getX()][p.getY()+1][p.getZ()]==true))
		{
			counter++;
		}
		
		if((0<=p.getY()-1)&&(maze.maze[p.getX()][p.getY()-1][p.getZ()]==1) && (neighbors[p.getX()][p.getY()-1][p.getZ()]==false))
		{
			possibleMoves.add(new Position(p.getX(), p.getY()-1, p.getZ()));
		}
		else if((0<=p.getY()-1)&& (neighbors[p.getX()][p.getY()-1][p.getZ()]==true))
		{
			counter++;
		}
		
		if((maze.getZ()>p.getZ()+1)&&(maze.maze[p.getX()][p.getY()][p.getZ()+1]==1) && (neighbors[p.getX()][p.getY()][p.getZ()+1]==false))
		{
			possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ()+1));
		}
		else if((maze.getZ()>p.getZ()+1)&& (neighbors[p.getX()][p.getY()][p.getZ()+1]==true))
		{
			counter++;
		}
		
		if((0<=p.getZ()-1)&&(maze.maze[p.getX()][p.getY()][p.getZ()-1]==1) && (neighbors[p.getX()][p.getY()][p.getZ()-1]==false))
		{
			possibleMoves.add(new Position(p.getX(), p.getY(), p.getZ()-1));
		}
		else if((0<=p.getZ()-1)&& (neighbors[p.getX()][p.getY()][p.getZ()-1]==true))
		{
			counter++;
		}
		if(counter>=2)
		{
			possibleMoves.clear();
			return possibleMoves;
		}
		return possibleMoves;
	}
}

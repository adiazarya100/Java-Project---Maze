package algorithms.mazeGenerators;

import java.util.Random;

/** This class represent random way to generate the maze.
 * this class extends abstractMaze3dGenerator*/

public class SimpleMaze3dGenerator extends abstractMaze3dGenerator {

	
	/* (non-Javadoc)
	 * @see algorithms.mazeGenerators.abstractMaze3dGenerator#generate(int, int, int)
	 */
	@Override
	public Maze3d generate(int x, int y, int z) {

		Maze3d maze=new Maze3d(x,y,z);
        maze.startPosition= new Position(0, 0, 0); //define start point
        maze.goalPosition= new Position(0, y, 0);   //define endpoint no mattar which floor
		Random random = new Random();
		int height = 0;
		
		while(height<y){
			for (int j = 0; j < x; j++) {
				for (int k = 0; k < z; k++) {
					if(j==0 && k==0 ){
						maze.maze[j][height][k]=0;
					}
					else
					maze.maze[j][height][k]=random.nextInt(2);//generate matrix of 0 and 1 
				}
			}
			height++;
		}
		return maze;

	}

	

}
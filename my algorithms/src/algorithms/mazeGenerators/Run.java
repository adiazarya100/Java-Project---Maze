package algorithms.mazeGenerators;



public class Run {
	private static void testMazeGenerator(Maze3dGenerator mg) {
		// prints the time it takes the algorithm to run
		System.out.println(mg.measureAlgorithmTime(6, 4, 6));
		// generate another 3d maze
		Maze3d maze = mg.generate(6, 4, 6);
		// get the maze entrance
		maze.print(); // 
		Position p = maze.getStartPosition();
		System.out.println();
		// print the position
		System.out.println(p); // format "{x,y,z}"
		// get all the possible moves from a position
		String[] moves = maze.getPossibleMoves(p);
		// print the moves
		for (String move : moves)
			System.out.println(move);
		// prints the maze exit position
		System.out.println(maze.getGoalPosition());
		try {
			// get 2d cross sections of the 3d maze
			int[][] maze2dx = maze.getCrossSectionByX(2);
			// TODO add code to print the array
			printCrossSection(maze2dx);
			int[][] maze2dy = maze.getCrossSectionByY(3);
			// TODO add code to print the array
			printCrossSection(maze2dy);
			int[][] maze2dz = maze.getCrossSectionByZ(0);
			// TODO add code to print the array
			printCrossSection(maze2dz);
			// this should throw an exception!
			maze.getCrossSectionByX(-1);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("good!");
		}
	}

	public static void printCrossSection(int[][] CrossSection) {
		for (int i = 0; i < CrossSection.length; i++) {
			for (int j = 0; j < CrossSection[i].length; j++) {
				System.out.print(CrossSection[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		testMazeGenerator(new SimpleMaze3dGenerator());
		testMazeGenerator(new MyMaze3dGenerator());
//		Maze3d m= new Maze3d(6, 4, 5);
//		System.out.println(m.getX());
//		System.out.println(m.getY());
//		System.out.println(m.getZ());
//
//		m.print();
	}
}

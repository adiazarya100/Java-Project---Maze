package model;

public interface Model {

	//provide the requested maze for display
	void provideMaze(String string);
	
	//this command generates Maze3d, sending name and 3 dimensions 
	void generateMaze(String name, int x, int y, int z);

	//this command asks for printing maze3d cross section for the given axis, index and name
	void crossSection(String section, int index, String name);

	//compressed the maze and save it
	void saveCompressedMaze(String name, String fileName);

	//load compressed maze 
	void loadCompressedMaze(String fileName, String name);

	//get maze size in the memory
	void mazeSize(String name);

	//solve the maze using BFS or ASTAR algorithm
	void mazeSolveing(String name, String algorithm);

	//asks for the maze size in the file
	void mazeSizeFile(String name);

	//asks for the solution
	void displaySolution(String string);

	
}

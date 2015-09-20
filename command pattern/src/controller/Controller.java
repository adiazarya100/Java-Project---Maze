package controller;

import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Controller {

	//notify the user the maze is ready after generates new Maze3D
	public void mazeIsReady(String name);

	//this command prints the given maze 
	public void displayMaze(Maze3d current);

	//display cross section by {X, Y, Z} 
	public void displayCrossSection(int[][] crossSectionByX);

	//print maze size in memory
	public void printMazeSize(long size);

	//notify the the solution is ready after solving
	public void solutionIsReady(String name);

	//send solution to the controller let us print the solution
	public void sendSolutioin(Solution<Position> solution);

	//start controller
	public void start() throws IOException;

	//print maze size in file
	public void printFileSize(String size);
	
	
}

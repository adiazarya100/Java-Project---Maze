package controller;

import algorithms.mazeGenerators.Maze3d;

public interface Controller {

	//notify the user the maze is ready after generates new Maze3D
	public void mazeIsReady(String string);

	//this command prints the given maze 
	public void displayMaze(Maze3d current);

	//display cross section by {X, Y, Z} 
	public void displayCrossSection(int[][] crossSectionByX);

	//print maze size in memory
	public void printMazeSize(long size);
	

}

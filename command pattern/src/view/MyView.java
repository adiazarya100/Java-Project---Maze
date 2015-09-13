package view;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;

public class MyView implements View {

	//this command displays every files in the current path
	@Override
	public void dir(String string) {
		File folder = new File(string);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}
	
	@Override
	public void displayMaze(Maze3d current) {

		int[][][] tmp = current.getMaze3d();
		for (int i = 0; i < current.getY(); i++) {
			if (i != 0)
				System.out.println();
			for (int j = 0; j < current.getX(); j++) {
				System.out.println();
				for (int g = 0; g < current.getZ(); g++) {
					System.out.print(tmp[j][i][g]);
				}
			}
		}

	}

	//this command prints the given maze 
	@Override
	public void mazeIsReady(String string) {
		System.out.println("maze "+string +" is ready");
		
	}

	//print cross section 
	@Override
	public void printCrossSection(int[][] crossSection) {
		System.out.println("Displaying cross section: ");
		for (int i = 0; i < crossSection.length; i++) {
			for (int j = 0; j < crossSection[i].length; j++) {
				System.out.print(crossSection[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
	}

	//prints the size of the maze in memory
	@Override
	public void printMazeSize(long size) {
		System.out.println("The size of the maze in memory is: "+size);
		
	}


}

package model;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.Controller;
import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFSalgorithm;
import algorithms.search.Searcher;

public class MyModel implements Model {

	protected HashMap<String, Maze3d> HM = new HashMap<>(); // save every maze with his (name, maze3d)
															
	protected HashMap<String, String> compressedHM = new HashMap<>(); // save every compressed maze with his (name, fileName)

	Controller controller;

	// notify the user the maze is ready after generates new Maze3D
	@Override
	public void provideMaze(String string) {
		Maze3d current = HM.get(string);
		controller.displayMaze(current);

	}

	// this command generates Maze3d in new Thread, sending name and 3
	// dimensions
	@Override
	public void generateMaze(String name, int x, int y, int z) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Maze3d maze = new MyMaze3dGenerator().generate(x, y, z);
				HM.put(name, maze);
			}
		}).start();
		// notify the user the maze is ready
		controller.mazeIsReady(name); // notify the controller layer
	}

	// this command asks for printing maze3d cross section for the given axis,
	// index and name
	@Override
	public void crossSection(String section, int index, String name) {
		Maze3d current = HM.get(name);
		switch (section) {
		case ("X"):
			controller.displayCrossSection(current.getCrossSectionByX(index));
			break;
		case ("x"):
			controller.displayCrossSection(current.getCrossSectionByX(index));
			break;
		case ("Y"):
			controller.displayCrossSection(current.getCrossSectionByY(index));
			break;
		case ("y"):
			controller.displayCrossSection(current.getCrossSectionByY(index));
			break;
		case ("Z"):
			controller.displayCrossSection(current.getCrossSectionByZ(index));
			break;
		case ("z"):
			controller.displayCrossSection(current.getCrossSectionByZ(index));
			break;
		default:
			System.out
					.println("Wrong input. please enter valid section {X,Y,Z}");
		}

	}

	// compressed the maze and save it
	@Override
	public void saveCompressedMaze(String name, String fileName) {
		Maze3d current = HM.get(name);
		try {
			OutputStream out = new MyCompressorOutputStream(
					new FileOutputStream(fileName));
			try {
				out.write(current.toByteArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		compressedHM.put(name, fileName);
	}

	// load compressed maze
	@Override
	public void loadCompressedMaze(String fileName, String name) {

		InputStream in = null;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte array[] = new byte[compressedHM.get(fileName).length()];
		try {
			in.read(array);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Maze3d loaded = new Maze3d(array);
		HM.put(name, loaded);
	}

	//get maze size in the memory
	@Override
	public void mazeSize(String name) {
		try {
			long size = HM.get(name).toByteArray().length;
			controller.printMazeSize(size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void mazeSolveing(String name, String algorithm) {
		Maze3d current = HM.get(name);
		switch(algorithm){
		case "BFS":
			Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
			searcherBFS.search(new searchMaze3DAdapter(current));
			break;
		case "ASTAR":
			
			break;
		}
	}
}

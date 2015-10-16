package GUI;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import view.Adapter;


public abstract class MazeDisplayer extends Canvas {
	
	int[][] mazeData;

	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	public void setMazeData(Adapter<Maze3d> adapter){
		int startFloor=adapter.getData().getStartPosition().getY();
		this.mazeData= adapter.getData().getCrossSectionByY(startFloor);
	}
	
	
	public abstract  void setCharacterPosition(int row,int col);

	public abstract void moveUp();

	public abstract  void moveDown();

	public abstract  void moveLeft();

	public  abstract void moveRight();

}

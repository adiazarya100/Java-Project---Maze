package GUI;

import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;

import presenter.Maze3dAdapter;
import view.Adapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;


/**
 * The Class CommonBoard.
 */
public abstract class CommonBoard extends Composite implements Board {

	/** The board rows. */
	int boardRows; 

	/** The board cols. */
	int boardCols;

	/** The tmp. */
	Adapter<Maze3d> tmp;

	/** The my maze. */
	Maze3dAdapter myMaze;

	/** The maze data. */
	int[][] mazeData;
	
	/** The current floor y. */
	int currentFloorY;

	/** The character x. */
	public int characterX;
	
	/** The character z. */
	public int characterZ;
	
		/**
		 * Instantiates a new common board.
		 *
		 * @param parent the parent
		 * @param style the style
		 */
		public CommonBoard(Composite parent, int style) {
			super(parent, style);
				addPaintListener(new PaintListener() { 
				
				@Override
				public void paintControl(PaintEvent arg0) {
					drawBoard(arg0);
				}
			});
				this.addKeyListener(new KeyListener(){	
					@Override

					public void keyPressed(KeyEvent e) { 
						if (e.keyCode == 16777217 /*&& hasPathUp(currentX, currentFloorY, currentZ)*/){//UP
							moveUp();} 
						 else 
							 if (e.keyCode == 16777220 /*&& hasPathRight(currentX, currentFloorY, currentZ)*/){//right
								 moveRight();} 
							else 
								if (e.keyCode == 16777219 /*&& hasPathLeft(currentX, currentFloorY, currentZ)*/){//left
								moveLeft();} 
								else
									if (e.keyCode == 16777218 /*&& hasPathDown(currentX, currentFloorY, currentZ)*/){//down
								moveDown();} 
									else
										if (e.keyCode == 16777221 && hasPathFloorUp(characterZ, currentFloorY, characterX)==true){//PAGE_UP -> floor up
									moveFloorUp();} 
										else
											if (e.keyCode == 16777222 && hasPathFloorDown(characterZ, currentFloorY, characterX)==true){//PAGE_DOWN  -> floor down
												moveFloorDown();} }

			@Override
			public void keyReleased(KeyEvent arg0) {

			}
		});
		//allows us to set the size of window by pressing ctrl + scrolling
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if((arg0.stateMask& SWT.CTRL)!=0){ //if control is pressed
					if(arg0.count>0){ //and we scroll up
						//up zoom in										//Bonus!!
						setSize(getSize().x+30, getSize().y+30);
					}
					if(arg0.count<0){ //and we scroll down
						setSize(getSize().x-30, getSize().y-30);
						//down zoom out	
					}
				}	
			}
		});		
	}

/*	@Override
	public abstract void  applyInputDirection(Direction direction);


	@Override
	public abstract void displayProblem(Object o);

	@Override
	public abstract void destructBoard();*/
		
		
	/* (non-Javadoc)
 * @see GUI.Board#displaySolution(algorithms.search.Solution)
 */
@Override
	public abstract <T> void displaySolution(Solution<T> s);
	
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathUp(int, int, int)
	 */
	public abstract boolean hasPathUp(int characterRow, int characterFloor ,int characterCol);
	
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathRight(int, int, int)
	 */
	public abstract boolean hasPathRight(int characterRow, int characterFloor ,int characterCol);
	
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathDown(int, int, int)
	 */
	public abstract boolean hasPathDown(int characterRow, int characterFloor ,int characterCol);
	
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathLeft(int, int, int)
	 */
	public abstract boolean hasPathLeft(int characterRow, int characterFloor ,int characterCol);
	 
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathFloorUp(int, int, int)
	 */
	public abstract boolean hasPathFloorUp(int characterRow, int characterFloor ,int characterCol);
	 
	/* (non-Javadoc)
	 * @see GUI.Board#hasPathFloorDown(int, int, int)
	 */
	public abstract boolean hasPathFloorDown(int characterRow, int characterFloor ,int characterCol);

}
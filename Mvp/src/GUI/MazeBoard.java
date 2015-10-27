package GUI;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import presenter.Maze3dAdapter;
import view.Adapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

/**
 * The Class MazeBoard.
 * definition of maze data to draw it in the window.
 */
public class MazeBoard extends CommonBoard{



	
	/** The images. */
	ImageData[] images;

	/** The hints. */
	boolean[][][] hints;
	
	/** The solution. */
	int solution[] =null;
	
	/** The tmp data. */
	int[][] tmpData=null;
	
	/** The tmp data2. */
	int[][] tmpData2=null;
	
	/** The position. */
	Position position;
	
	/** The won. */
	boolean won=false;
	
	/** The flag. */
	boolean flag = true;
	
	/** The exit x. */
	public int exitX=0;
	
	/** The exit y. */
	public int exitY=2;
	
	
	/** The image. */
	Image image = new Image(getDisplay(), "./resources/images/Mario.png");
	
	/** The image2. */
	Image image2 = new Image(getDisplay(), "./resources/images/win.png");
	
	/** The image3. */
	Image image3 = new Image(getDisplay(), "./resources/images/mushroom.png");

	/**
	 * Instantiates a new maze board.
	 *
	 * @param parent the parent
	 * @param style the style
	 */
	public MazeBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);		
			
	}
	
	/**
	 * Gets the current floor.
	 *
	 * @return the current floor
	 */
	public int getCurrentFloor() {
		return currentFloorY;
	}

	
	/* (non-Javadoc)
	 * @see GUI.Board#drawBoard(org.eclipse.swt.events.PaintEvent)
	 */
	@Override
	public void drawBoard(PaintEvent e) { //this draw the maze!
		e.gc.setForeground(new Color(null,0,0,0));
		e.gc.setBackground(new Color(null,0,0,0));
		int width=getSize().x;
		int height=getSize().y;

		int mx=(width/2);

		double w=(double)width/mazeData[0].length;
		double h=(double)height/mazeData.length;

		        	  
		for(int i=0;i<mazeData.length;i++){			
			double w0=0.7*w +0.3*w*i/mazeData.length;
			double w1=0.7*w +0.3*w*(i+1)/mazeData.length;
			double start=mx-w0*mazeData[i].length/2;
			double start1=mx-w1*mazeData[i].length/2;
			
			for(int j=0;j<mazeData[i].length;j++){
				double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};

				double cheight=h/2;
				if(mazeData[i][j]==1)
					paintCube(dpoints, cheight,e);
				
				//Solution for the Maze
				if(won==true){
					SetMazeWIN(this.solution);
				if(mazeData[i][j]==2)
					PaintSolution(dpoints, cheight,e);
				}
				
				//draw the red ball!
				if(i==characterZ && j==characterX){
					//e.gc.setBackground(new Color(null,200,0,0));
					//e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
					//e.gc.setBackground(new Color(null,255,0,0));
					//e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
					e.gc.drawImage(image,  0, 0, image.getBounds().width,image.getBounds().height,(int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
					e.gc.setBackground(new Color(null,0,0,0));
				}
			}
		}
		if(characterZ==tmp.getData().getGoalPosition().getX()+1 && currentFloorY==tmp.getData().getGoalPosition().getY() && characterX==tmp.getData().getGoalPosition().getZ()){
			if(flag==true){
			e.gc.drawImage(resize(image2, getSize().x, getSize().y),0,0);
			}
			flag =false;
	   }
	}

	
	
	/**
	 * Paint cube.
	 *
	 * @param p the p
	 * @param h the h
	 * @param e the e
	 */
	private void paintCube(double[] p,double h,PaintEvent e){ //this draw the maze!
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        

        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}
	
	/**
	 * Paint solution.
	 *
	 * @param p the p
	 * @param h the h
	 * @param e the e
	 */
	private void PaintSolution(double[] p,double h,PaintEvent e){ //this draw the Solution!
		e.gc.drawImage(image3, 0, 0, image3.getBounds().width,image3.getBounds().height,(int)Math.round(p[0]), (int)Math.round(p[1]-h/2), (int)Math.round((50)/2), (int)Math.round(h));
	}
	
	/**
	 * Sets the board data.
	 *
	 * @param adapter the adapter
	 */
	//set data Maze3DAdapter maze
	void SetBoardData(Adapter<Maze3d> adapter) {
		
		this.tmp=adapter;
		int startFloor=adapter.getData().getStartPosition().getY(); //get the start Position floor
		setFloor(startFloor);
		this.mazeData= adapter.getData().getCrossSectionByY(startFloor); //set maze data by cross section with the start position floor value
		//this.mazeData= adapter.getData().getCrossSectionByY(0);
		//this.mazeData2= adapter.getData().getCrossSectionByY(startFloor+1);
		this.characterX=adapter.getData().getStartPosition().getX(); //set the red ball character start position with the maze start position value
		this.characterZ=adapter.getData().getStartPosition().getZ(); //set the red ball character start position with the maze start position value
		//mazeBoard.setLayoutData(new GridData (SWT.FILL, SWT.FILL,true,true,2,2));
		//this.currentX=adapter.getData().getStartPosition().getX();
		//this.currentZ=adapter.getData().getStartPosition().getZ();
		//this.hints = new boolean[tmp.getData().getX()][tmp.getData().getY()][tmp.getData().getZ()];
	}


	/**
	 * Sets the maze win.
	 *
	 * @param solution the solution
	 */
	void SetMazeWIN(int[] solution) {
		int[][][] tmpMaze = this.tmp.getData().getMaze();
		
		for(int j=0;j<solution.length;j+=3){
				tmpMaze[solution[j]][solution[j+1]][solution[j+2]]=2;
			}
		tmp.getData().setMaze(tmpMaze);
		this.mazeData= tmp.getData().getCrossSectionByY(getFloor());
		}
	
	/* (non-Javadoc)
	 * @see GUI.CommonBoard#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public <T> void displaySolution(Solution<T> s) {
		
		ArrayList<State<T>> myList = s.getSolution();

	
}
	
	/**
	 * Sets the solution array.
	 *
	 * @param array the new solution array
	 */
	void setSolutionArray(int[] array){
    	  this.solution = array; 
 }

	/**
	 * Gets the floor.
	 *
	 * @return the floor
	 */
	public int getFloor() {
		return currentFloorY;
	}

	/**
	 * Sets the floor.
	 *
	 * @param floor the new floor
	 */
	public void setFloor(int floor) {
		this.currentFloorY = floor;
		
	}

	
	/**
	 * Move character.
	 *
	 * @param x the x
	 * @param z the z
	 */
	private void moveCharacter(int x,int z){
		if(x>=0 && x<mazeData[0].length && z>=0 && z<mazeData.length && mazeData[z][x]!=1){
			characterX=x;
			characterZ=z;
			getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					redraw();
				}
			});
		}
	}

	
	/* (non-Javadoc)
	 * @see GUI.Board#moveUp()
	 */
	@Override
	public void moveUp() {
		int x=characterX;
		int z=characterZ;
		z=z-1;
		//this.currentX=x;
	//	this.currentZ=z;
		moveCharacter(x, z);
	}
	
	/* (non-Javadoc)
	 * @see GUI.Board#moveDown()
	 */
	
	@Override
	public void moveDown() {
		int x=characterX;
		int z=characterZ;
		z=z+1;
		//this.currentX=x;
	//	this.currentZ=z;
		moveCharacter(x, z);
	}
	
	
	/* (non-Javadoc)
	 * @@see GUI.Board#moveLeft()
	 */
	@Override
	public void moveLeft() {
		int x=characterX;
		int z=characterZ;
		x=x-1;
		//this.currentX=x;
		//this.currentZ=z;
		moveCharacter(x, z);
	}
	
	
	/* (non-Javadoc)
	 * @@see GUI.Board#moveRight()
	 */
	@Override
	public void moveRight() {
		int x=characterX;
		int z=characterZ;
		x=x+1;
		//this.currentX=x;
		//this.currentZ=z;
		moveCharacter(x, z);
	}
	
	
	
	/* (non-Javadoc)
	 * @see GUI.Board#moveFloorUp()
	 */
	@Override
	public void moveFloorUp() {
		if((currentFloorY+1>=0) && (currentFloorY+1 <= (tmp.getData().getY()-1))){
		tmpData2 =tmp.getData().getCrossSectionByY(currentFloorY+1);
		if(tmpData2[characterZ][characterX]!=1 ){//&& currentFloorY > 0 && currentFloorY <= tmp.getData().getFloor()){
		int y= currentFloorY+1;
		int x=characterX;
		int z=characterZ;
		setFloor(y);
		//mazeData=mazeData2;
		mazeData=tmp.getData().getCrossSectionByY(y);
		moveCharacter(x, z);}}
/*		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
				update();
			}
		});}}*/
	//	redraw();
	//	update();		}}
		//this.currentFloorY =y;
		//moveCharacter(x,y,z);}//}
	}

	/* (non-Javadoc)
	 * @see GUI.Board#moveFloorDown()
	 */
	@Override
	public void moveFloorDown() {
		if((currentFloorY-1>=0) && (currentFloorY-1 <= (tmp.getData().getY()-1))){
		tmpData2 =tmp.getData().getCrossSectionByY(currentFloorY-1);
		if(tmpData2[characterZ][characterX]!=1){//&& currentFloorY > 0 && currentFloorY <= tmp.getData().getFloor()){
		//if(tmp.getData().getCellValue(characterX, currentFloorY-1, characterZ)==0){
		int y= currentFloorY-1;
		int x=characterX; 
		int z=characterZ;
		setFloor(y);
		mazeData=tmp.getData().getCrossSectionByY(y);
		moveCharacter(x, z);}}
		//this.currentFloorY=y;
		//moveCharacter(x,y,z);//}
/*		getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				redraw();
				update();
			}
		});}}}*/
		//redraw();
		//update();
		}
	

	/* (non-Javadoc)
	 * @see GUI.Board#setCharacterPosition(int, int)
	 */
	@Override
	public void setCharacterPosition(int row, int col) {
		characterX=col;
		characterZ=row;
		moveCharacter(col,row);
	}

	
	
	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathUp(int, int, int)
	 */
	@Override
	public boolean hasPathUp(int characterRow, int characterFloor ,int characterCol) {
		//tmp = this.myMaze.getData();
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		
		return value == 0;
	}

	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathRight(int, int, int)
	 */
	@Override
	public boolean hasPathRight(int characterRow, int characterFloor ,int characterCol) {
		//Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathDown(int, int, int)
	 */
	@Override
	public boolean hasPathDown(int characterRow, int characterFloor ,int characterCol){
		//Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathLeft(int, int, int)
	 */
	@Override
	public boolean hasPathLeft(int characterRow, int characterFloor ,int characterCol) {
	//	Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathFloorUp(int, int, int)
	 */
	@Override
	public boolean hasPathFloorUp(int characterRow, int characterFloor ,int characterCol) {
		System.out.println("hasPathFloorUp");
		System.out.println("x: "+characterRow);
		System.out.println("y: "+characterFloor);
		System.out.println("z: "+characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor+1 , characterCol);
		System.out.println("value: "+value);
		if(value != 1){
			System.out.print("true");
			return true;}
		else {
			System.out.print("false");
			return false;}
	}

	/* (non-Javadoc)
	 * @see GUI.CommonBoard#hasPathFloorDown(int, int, int)
	 */
	@Override
	public boolean hasPathFloorDown(int characterRow, int characterFloor ,int characterCol) {
		//Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		System.out.println("hasPathFloorDown");
		System.out.println("x: "+characterRow);
		System.out.println("y: "+characterFloor);
		System.out.println("z: "+characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor-1 , characterCol);
		System.out.println("value: "+value);
		if(value != 1){
			System.out.print("true");
			return true;}
		else {
			System.out.print("false");
			return false;}
	}
	
	/**
	 * Resize.
	 *
	 * @param image the image
	 * @param width the width
	 * @param height the height
	 * @return the image
	 */
	private Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, 
		image.getBounds().width, image.getBounds().height, 
		0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}


}

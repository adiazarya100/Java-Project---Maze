package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;

import presenter.Maze3dAdapter;
import view.Adapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeBoard extends CommonBoard{

	//ImageLoader gifs=new ImageLoader();


	ImageData[] images;

	int frameIndex=0;

	int GoalX;
	
	int SourceX;

	int GoalZ;

	int SourceZ;

	int GoalY;
	
	//int[][] mazeData2;
	
	int[][] tmpData=null;
	int[][] tmpData2=null;
	
	Position position;
	

	public int exitX=0;
	public int exitY=2;

	public MazeBoard(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED);		
			
	}
	
	public int getCurrentFloor() {
		return currentFloorY;
	}

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
		          if(mazeData[i][j]!=0)
		        	  paintCube(dpoints, cheight,e);
		          //draw the red ball!
		          if(i==characterZ && j==characterX){
					   e.gc.setBackground(new Color(null,200,0,0));
					   e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
					   e.gc.setBackground(new Color(null,255,0,0));
					   e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
					   e.gc.setBackground(new Color(null,0,0,0));				        	  
		          }
		      }
		   }
	}
	
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
	}


	
	@Override
	public <T> void displaySolution(Solution<T> s) {
		String Solution = s.toString();
		Solution=Solution.replace("{", "");
		Solution=Solution.replace("}", "");
		String []path = Solution.split("<-");
		//Image img = new Image(getDisplay(),".\\resources\\images\\mario.jpg"); //hint image
		for(int i=0;i<path.length-1;i++){
		String []indexes = path[i].split(",");
			int xt=Integer.parseInt(indexes[1]);
			int yt=Integer.parseInt(indexes[2]);	
				//(board[xt][yt]).setBackgroundImage(img); //put hints all over the solutions path
			}
	
			drawBoard(null);
			forceFocus();
	
}

	public int getFloor() {
		return currentFloorY;
	}

	public void setFloor(int floor) {
		this.currentFloorY = floor;
		
	}

	
	private void moveCharacter(int x,int z){
		if(x>=0 && x<mazeData[0].length && z>=0 && z<mazeData.length && mazeData[z][x]==0){
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
	
	
	
	@Override
	public void moveFloorUp() {
		if((currentFloorY+1>=0) && (currentFloorY+1 <= (tmp.getData().getY()-1))){
		tmpData2 =tmp.getData().getCrossSectionByY(currentFloorY+1);
		if(tmpData2[characterZ][characterX]==0 ){//&& currentFloorY > 0 && currentFloorY <= tmp.getData().getFloor()){
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

	@Override
	public void moveFloorDown() {
		if((currentFloorY-1>=0) && (currentFloorY-1 <= (tmp.getData().getY()-1))){
		tmpData2 =tmp.getData().getCrossSectionByY(currentFloorY-1);
		if(tmpData2[characterZ][characterX]==0){//&& currentFloorY > 0 && currentFloorY <= tmp.getData().getFloor()){
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
	
/*	@Override
	public void moveFloorUp() {
		if((currentFloorY+1>=0) && (currentFloorY+1 <= (tmp.getData().getY()-1))){
		tmpData2=tmp.getData().getCrossSectionByY(currentFloorY+1);}
		if(tmpData2[characterX][characterZ]==0){
		int y= (currentFloorY+1);
		int x=characterX;
		int z=characterZ;
		setFloor(y);
		//mazeData=mazeData2;
		mazeData=tmp.getData().getCrossSectionByY(y);
		moveCharacter(x, z);}
	}

	@Override
	public void moveFloorDown() {
		if((currentFloorY-1>=0) && (currentFloorY <= (tmp.getData().getY()-1))){
		tmpData2=tmp.getData().getCrossSectionByY(currentFloorY-1);}
		if(tmpData2[characterX][characterZ]==0){
		int y= (currentFloorY-1);
		int x=characterX;
		int z=characterZ;
		setFloor(y);
		mazeData=tmp.getData().getCrossSectionByY(y);
		moveCharacter(x, z);}
		
	}*/

	@Override
	public void setCharacterPosition(int row, int col) {
		characterX=col;
		characterZ=row;
		moveCharacter(col,row);
	}

	
	
	@Override
	public boolean hasPathUp(int characterRow, int characterFloor ,int characterCol) {
		//tmp = this.myMaze.getData();
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		
		return value == 0;
	}

	@Override
	public boolean hasPathRight(int characterRow, int characterFloor ,int characterCol) {
		//Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	@Override
	public boolean hasPathDown(int characterRow, int characterFloor ,int characterCol){
		//Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	@Override
	public boolean hasPathLeft(int characterRow, int characterFloor ,int characterCol) {
	//	Maze3d maze = this.myMaze.getData();
		//int value = maze.getCellValue(characterRow,characterFloor , characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor , characterCol);
		return value == 0;
	}

	@Override
	public boolean hasPathFloorUp(int characterRow, int characterFloor ,int characterCol) {
		System.out.println("hasPathFloorUp");
		System.out.println("x: "+characterRow);
		System.out.println("y: "+characterFloor);
		System.out.println("z: "+characterCol);
		int value = tmp.getData().getCellValue(characterRow,characterFloor+1 , characterCol);
		System.out.println("value: "+value);
		if(value == 0){
			System.out.print("true");
			return true;}
		else {
			System.out.print("false");
			return false;}
	}

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
		if(value == 0){
			System.out.print("true");
			return true;}
		else {
			System.out.print("false");
			return false;}
	}




}

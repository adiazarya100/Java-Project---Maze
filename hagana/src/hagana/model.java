package hagana;

import java.util.ArrayList;

import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFSalgorithm;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.*;



public class model {
	//Maze3d maze = new Maze3d(31,1,31);
	
	//int[][] matrix = new int[30][30];
	
	public model() {
		super();
		//ToZero();
	}
/*
	public void ToZero(){
	for(int i=0;i<30;i++){
		for(int j=0;j<30;j++){
			this.matrix[i][j]=0;}}
	}
	*/
	public String[] withparams(ArrayList<String> params){
		Maze3d maze = new Maze3d(30,1,30);
		String apple = null;
		String head = null;
		head  = params.get(0);
		apple = params.get(1);
		
		System.out.println(head);
		System.out.println(apple);
		
		
		String []headpath = head.split(",");
		String []applepath = apple.split(",");
		
		int Xhead = Integer.parseInt(headpath[0]);
		int Yhead = Integer.parseInt(headpath[1]);
		
		int Xapple = Integer.parseInt(applepath[0]);
		int Yapple = Integer.parseInt(applepath[1]);
		
		Position startPosition = new Position(Xhead, 0, Yhead);
		Position goalPosition = new Position(Xapple, 0, Yapple);
		
		maze.setStartPosition(startPosition);
		maze.setgoalPosition(goalPosition);
		
		System.out.println("Algo");
		searchMaze3DAdapter bfsMaze = new searchMaze3DAdapter(maze);
		//Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
		Searcher<Position> searcherBFS= new ASTARalgorithm<Position>(new ManhattanDistance());
		Solution<Position> s = new Solution<Position>();
		s= searcherBFS.search(bfsMaze);
		
		ArrayList<State<Position>> arr = s.getSolution();
		String[]commands =new String[arr.size() - 1];
		
		for(int i=0;i<arr.size() - 1;i++){
			Position p1 = arr.get(i).getCurrentPosition();
			Position p2 = arr.get(i + 1).getCurrentPosition();
			
			if (p2.getZ() == p1.getZ() + 1)
				commands[i] = "up";
			else if (p2.getZ() == p1.getZ() - 1)
				commands[i] = "down";
			else if (p2.getX() == p1.getX() - 1)
				commands[i] =  "right";
			else
				commands[i] = "left";
			
		}
		//for(int i=0;i<commands.length;i++){
		//	System.out.println(commands[i]);
		//}
		return commands;
	}
	
	
	
	
	
	public String[] withparams2(ArrayList<String> params){
		Maze3d maze = new Maze3d(30,1,30);
		String apple = null;
		String head = null;
		head  = params.get(0);
		apple = params.get(1);
		
		System.out.println(head);
		System.out.println(apple);
		
		
		String []headpath = head.split(",");
		String []applepath = apple.split(",");
		
		int Xhead = Integer.parseInt(headpath[0]);
		int Yhead = Integer.parseInt(headpath[1]);
		
		int Xapple = Integer.parseInt(applepath[0]);
		int Yapple = Integer.parseInt(applepath[1]);
		
		Position startPosition = new Position(Xhead, 0, Yhead);
		Position goalPosition = new Position(Xapple, 0, Yapple);
		
		maze.setStartPosition(startPosition);
		maze.setgoalPosition(goalPosition);
		
		System.out.println("Algo");
		searchMaze3DAdapter bfsMaze = new searchMaze3DAdapter(maze);
		Searcher<Position> searcherBFS= new BFSalgorithm<Position>(); 
		//Searcher<Position> searcherBFS= new ASTARalgorithm<Position>(new ManhattanDistance());
		Solution<Position> s = new Solution<Position>();
		s= searcherBFS.search(bfsMaze);
		
		//System.out.println(s);
				
		int xz = 0;
		int xt = 0;
		int yt = 0;
		int index =0;
		//int indextmp =0;
		String Solution = s.toString();
		Solution=Solution.replace("(", "");
		Solution=Solution.replace(")", "");
		String []path = Solution.split(" ");
		//s.toString();
		int[]tmpArray =new int[(path.length)*2];
		String[] tmp = null;
		//System.out.println(path.length-1);
		
		for(int i=0;i<path.length;i++){
		  tmp = path[i].split(",");
		  xz = Integer.parseInt(tmp[0]);
		  xt = Integer.parseInt(tmp[1]);
		  yt = Integer.parseInt(tmp[2]);
/*		  System.out.println(xz);
		  System.out.println(xt);
		  System.out.println(yt);*/
		  tmpArray[index] = xz;
		  tmpArray[index+1] = yt;
		  index+=2;
			}

		System.out.println("****************");
		int []Xcor = new int[tmpArray.length/2];
		int []Ycor = new int[tmpArray.length/2];
		//System.out.println(tmpArray.length);
		
		for(int j=0, i=0;i<tmpArray.length;i+=2){
			Xcor[j]= tmpArray[i];
			j++;}
		for(int j=0, i=1;i<tmpArray.length;i+=2){
			Ycor[j]= tmpArray[i];
			j++;}
		
		//System.out.println(Xcor);
		//System.out.println("****************");
		//System.out.println(Ycor);
		//for(int i=0;i<Xcor.length;i++){
			//System.out.println("x:"+Xcor[i]);
			//System.out.println("y:"+Ycor[i]);
		//}
		String[]commands =new String[(tmpArray.length/2)-1];
		
		for(int i=0;i<(tmpArray.length/2)-1;i++){
			if(Xcor[i] < Xcor[i+1])
				commands[i] = "left";
			else if(Xcor[i] > Xcor[i+1])
				commands[i] = "right";
			else if(Ycor[i] > Ycor[i+1])
				commands[i] = "down";
			else if(Ycor[i] < Ycor[i+1])
				commands[i] = "up";		
		}
		//for(int i=0;i<commands.length;i++){
		//	System.out.println(commands[i]);
		//}
		return commands;
	}
	
}



/*		
//mazeBoard.displaySolution(s);
		int xz = 0;
		int xt = 0;
		int yt = 0;
		int index =0;
		String Solution = s.toString();
		Solution=Solution.replace("(", "");
		Solution=Solution.replace(")", "");
		String []path = Solution.split(" ");
		//s.toString();
		int[]tmpArray =new int[(path.length)*3];
		String[] tmp = null;
		//System.out.println(path.length-1);
		
		for(int i=0;i<path.length;i++){
		  tmp = path[i].split(",");
		  xz = Integer.parseInt(tmp[0]);
		  xt = Integer.parseInt(tmp[1]);
		  yt = Integer.parseInt(tmp[2]);
		  System.out.println(xz);
		  System.out.println(xt);
		  System.out.println(yt);
		  tmpArray[index] = xz;
		  tmpArray[index+1] = xt;
		  tmpArray[index+2] = yt;
		  index+=3;*/


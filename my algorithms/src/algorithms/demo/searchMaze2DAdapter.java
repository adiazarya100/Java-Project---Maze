package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze2d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

public class searchMaze2DAdapter implements Searchable <Position> {
	
	Maze2d myMaze;
	
	public searchMaze2DAdapter(Maze2d myMaze) {
		this.myMaze = myMaze;
	}
	
	public Maze2d getMyMaze() {
		return myMaze;
	}

	public void setMyMaze(Maze2d myMaze) {
		this.myMaze = myMaze;
	}


	@Override
	public State<Position> getStartState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State<Position> getGoalState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		// TODO Auto-generated method stub
		return null;
	}
}

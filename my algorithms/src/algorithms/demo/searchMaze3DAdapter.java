package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Searchable;
import algorithms.search.State;

/**This class represents Object adapter.
 * Doing Adaptation from Maze3d to search problem (searchable). */
public class searchMaze3DAdapter implements Searchable <Position>{
	
	/** The searchable maze. */
	Maze3d searchableMaze; //stands for object adapter

	/**
	 * Instantiates a new search maze3 d adapter.
	 *
	 * @param searchableMaze the searchable maze
	 */
	
	//C'tor
	public searchMaze3DAdapter(Maze3d searchableMaze) {
		super();
		this.searchableMaze = searchableMaze;
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Searchable#getStartState()
	 */
	@Override
	public State<Position> getStartState() {
	
		State<Position> startpos = new State<Position>(searchableMaze.getStartPosition());
		return startpos;
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Searchable#getGoalState()
	 */
	@Override
	public State<Position> getGoalState() {
		State<Position> goalpos = new State<Position>(searchableMaze.getGoalPosition());
		return goalpos;
	}

	/* (non-Javadoc)
	 * @see algorithms.search.Searchable#getAllPossibleStates(algorithms.search.State)
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		ArrayList<State<Position>> array=new ArrayList<State<Position>>();
		String[] state;
		state=searchableMaze.getPossibleMoves(s.getCurrentPosition());
		for(String i: state){
			Position p =new Position(i);
			State<Position> sp=new State<Position>(p);
			sp.setCost(1);
			array.add(sp);
			
		}
		return array;
	}
	
}

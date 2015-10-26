package algorithms.search;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import algorithms.demo.searchMaze3DAdapter;
import algorithms.mazeGenerators.DFS;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class ASTARunitTest {
		

		/**
		 * Test null heuristic.
		 */
		@Test
		public void testNullHeuristic() {
			Maze3d maze3D = new DFS().generate(3, 3, 3);
			searchMaze3DAdapter maze3d = new searchMaze3DAdapter(maze3D);
			Searcher<Position> astar= new ASTARalgorithm<Position>(null);
			maze3D.print();
			Solution<Position> astarSolution=astar.search(maze3d);
			System.out.println(" Astar sol "+astarSolution);
			assertNull(astarSolution);
			
		}

		/**
		 * Test empty maze argument.
		 */
		@Test
		public void testEmptyMazeArgument() {
			Maze3d maze3D = new DFS().generate(0,0,0);
			searchMaze3DAdapter maze3d = new searchMaze3DAdapter(maze3D);
			Searcher<Position> astar= new ASTARalgorithm<Position>(new ManhattanDistance());
			Solution<Position> astarSolution=astar.search(maze3d);
			System.out.println("emptymaze" + astarSolution);
			assertNull(astarSolution);
		}

		/**
		 * Test dumb searchable argument.
		 */
		@SuppressWarnings("unchecked")
		@Test
		public void testDumbSearchableArgument() {
			@SuppressWarnings("rawtypes")
			ASTARalgorithm astar=new ASTARalgorithm(new ManhattanDistance());
			Searcher<Position> astarSolution=(Searcher<Position>) astar.search(new Searchable<Position>() {


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
			});
			
			assertNull(astarSolution);
		
		}
		
		/**
		 * Test null search argument.
		 */
		@Test
		public void testNullSearchArgument() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			ASTARalgorithm astar=new ASTARalgorithm(new ManhattanDistance());
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Solution sol=astar.search(null);
			
			assertNull(sol);
		}
}

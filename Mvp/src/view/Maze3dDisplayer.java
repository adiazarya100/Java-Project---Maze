package view;

import java.io.PrintWriter;
import java.util.Arrays;

import algorithms.mazeGenerators.Maze3d;

/**
 * The Class Maze3dDisplayer.
 */
public class Maze3dDisplayer implements Display<Maze3d> {

	/** The draw. */
	Maze3d draw;
	
	
	/** The out. */
	PrintWriter out;

	/**
	 * Instantiates a new maze3d displayer adapter.
	 *
	 * @param out the out
	 */
	public Maze3dDisplayer(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.Displayer#getDisplayer(view.Drawable)
	 */
	@Override
	public void getDisplayer(Adapter<Maze3d> draw) {

		this.draw = draw.getData();
	}


	
	
	@Override
	public void display() {

		/*for (int i = 0; i < draw[1].length; i++) {
			for (int j= 0; j < draw.length; j++) {
				for (int w = 0; w < draw[0][1].length; w++) {

					out.print(draw[j][i][w]);
					out.flush();
				}
				out.print("\n");
				out.flush();
			}
			out.print("\n\n");
			out.flush();
		}
		
	}*/
		draw.print();
	}
}

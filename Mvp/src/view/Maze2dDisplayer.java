package view;

import java.io.PrintWriter;

/**
 * The Class Maze2dDisplayer.
 */
public class Maze2dDisplayer implements Display<int[][]> {

	/** The draw. */
	int[][] draw;
	
	/** The out. */
	PrintWriter out;

	/**
	 * Instantiates a new maze2d displayer adapter.
	 *
	 * @param out the out
	 */
	public Maze2dDisplayer(PrintWriter out) {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see view.Displayer#getDisplayer(view.Drawable)
	 */
	@Override
	public void getDisplayer(Adapter<int[][]> draw) {
		this.draw = draw.getData();
		
	}


	/* (non-Javadoc)
	 * @see view.Displayer#display()
	 */
	@Override
	public void display() {
		out.flush();
		for (int i = 0; i < draw.length; i++) {
			for (int j = 0; j < draw[i].length; j++) {
				out.flush();

				out.print(draw[i][j]);	
			}

			out.print("\n");
		}

		out.print("\n\n");
	}



}

package boot;

import model.MyModel;
import GUI.MazeWindow;
import presenter.MyPresenter;
import presenter.Properties;

/**
 * The Class RunGUI.
 */
public class RunGUI {
	
	/**
	 * Start.
	 *
	 * @param properties the properties
	 */
	public void start(Properties properties){
		MazeWindow view=new MazeWindow("Maze Generations", 600, 600);
		MyModel model;
		model=new MyModel(properties);
		MyPresenter p=new MyPresenter(view,model);
		view.addObserver(p);
		model.addObserver(p);
		view.start();
	}
}
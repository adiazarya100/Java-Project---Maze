package generic;


import generic.Enums;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import GUI.MazeWindow;
import model.Model;
import model.MyModel;
import presenter.MyPresenter;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class Run.
 */
public class Run {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		Properties myProp = readPreferences();
		//--------כל מה שבירוק זה שינויים בשביל ה GUI, מה שהיה בהערה היה קודם--------//
		//MyModel myModel = new MyModel(myProp); 
		//MyView myView = new MyView(out, in); 
		Model model = new MyModel(myProp);
		View view = new MazeWindow("test", 500, 500);
		
		//Presenter presenter = new MyPresenter(myView, myModel);
		Presenter presenter = new MyPresenter(view, model);
		//myView.addObserver(presenter);
		//myModel.addObserver(presenter);
		
		((Observable) view).addObserver(presenter);
		((Observable) model).addObserver(presenter);
		presenter.start();
		
		

	}
	
	/**
	 * Read preferences.
	 * read propetries from XML file.
	 * @return the properties
	 */
	public static Properties readPreferences()
	{
		XMLDecoder d;
		Properties p=null;
		try {
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(Enums.XML_FILE_PATH));
			d=new XMLDecoder(in);
			p=(Properties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new Properties();
		}
		return p;
	}
}
package generic;


import generic.Enums;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import presenter.Presenter;
import presenter.Properties;
import view.MyView;

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
		Properties myPref = readPreferences();
		
		MyModel myModel = new MyModel(myPref);
		MyView myView = new MyView(out, in);
		
		Presenter presenter = new MyPresenter(myView, myModel);
		myView.addObserver(presenter);
		myModel.addObserver(presenter);
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
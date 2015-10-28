package boot;

import generic.Enums;
import generic.WritePropertiesGUI;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import model.Model;
import model.MyModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import GUI.MazeWindow;
import presenter.MyPresenter;
import presenter.Properties;
import view.MyView;
import view.View;

/** * @authors  Zlil Korman 302751839 & adi azarya 200540789
* @version 1.0
* @since   2015-10-27*/ 

/**
 * The Class Run.
 * this class run GUI or CLI depends on the user decision without **/

public class Run2 {

	public static void main(String[] args) {
		
		
		
		Display display=new Display();
		Shell shell=new Shell(display);
		WritePropertiesGUI guiProp=new WritePropertiesGUI();
		guiProp.writeProperties(shell);
		Model model;
		View view;
		Properties properties=readProperties();
		if(properties!=null)
		{
			model=new MyModel(properties);
			
			switch(properties.getUi())
			{
				case CLI:
					view=new MyView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
					MyPresenter p=new MyPresenter(view,model);
					((Observable) view).addObserver(p);
					((Observable) model).addObserver(p);
					view.start();
					break;
				case GUI:
					
					BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
					PrintWriter out = new PrintWriter(System.out);					
					//Properties myProp = readProperties();
					model = new MyModel(properties);
					//view = new MazeWindow("test", 500, 500);
					view=new MazeWindow(display, shell, "bobo", 1300, 700);
					MyPresenter pMaze=new MyPresenter(view,model);
					((Observable) view).addObserver(pMaze);
					((Observable) model).addObserver(pMaze);
					pMaze.start();
					break;
				default:
					return;	
			}
		}
		else
			return;
	
	}

	public static Properties readProperties()
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
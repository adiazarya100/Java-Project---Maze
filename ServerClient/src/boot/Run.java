package boot;


import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Observable;

import Model.Enums;
import Model.Model;
import Presenter.Presenter;
import Server.MazeClientHandler;
import Server.MyModelServer;
import Server.ServerProperties;
import View.MyViewServer;
import View.View;


public class Run {
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		ServerProperties myProp = readProperties();
		MazeClientHandler MCH = new MazeClientHandler();
		Model model = new MyModelServer(MCH);
		View view = new MyViewServer(out, in);
		Presenter presenter = new Presenter(model, view);
		//myView.addObserver(presenter);
		//myModel.addObserver(presenter);
		
		((Observable) view).addObserver(presenter);
		((Observable) model).addObserver(presenter);
		view.start();
		
		

	}
	
	/**
	 * Read preferences.
	 * read propetries from XML file.
	 * @return the properties
	 */
	public static ServerProperties readProperties()
	{
		XMLDecoder d;
		ServerProperties p=null;
		try {
			BufferedInputStream in=new BufferedInputStream(new FileInputStream("./ServerProperties.zip"));
			d=new XMLDecoder(in);
			p=(ServerProperties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new ServerProperties();
		}
		return p;
}
}

package generic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.MyPresenter;
import presenter.Properties;
import view.MyView;

public class RunCLI {
	
	public void startProgram(Properties preferences ) {
		MyView view=new MyView(new PrintWriter(System.out),new BufferedReader(new InputStreamReader(System.in)));
		MyModel model;
		model=new MyModel(preferences );
		MyPresenter p=new MyPresenter(view,model);
		view.addObserver(p);
		model.addObserver(p);
		view.start();
	}

}

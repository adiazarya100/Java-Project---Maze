package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class MyPresnter implements Observer {

	View view;
	Model model;
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

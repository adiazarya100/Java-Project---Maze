package boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.Model;
import model.MyModel;
import view.CLI;
import view.MyView;
import view.View;
import controller.Controller;
import controller.MyController;
 

public class Run {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("made by zlil korman 302751839 & adi azarya 200540789");
		PrintWriter w=new PrintWriter(System.out);
		CLI cli = new CLI(w,new BufferedReader(new InputStreamReader(System.in)));/*, new MyController(w)*/
		View view = new MyView(cli);
		Model model = new MyModel();
		Controller controller = new MyController(model, view);
		//view.start();
		controller.start();
	}

}

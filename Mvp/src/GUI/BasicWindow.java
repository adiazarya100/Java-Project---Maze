package GUI;

import java.util.Observable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public abstract class BasicWindow extends Observable implements Runnable{

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	Display display; 
	Shell shell;
	
	public BasicWindow(Display display,Shell shell,String title, int width, int height) {
		this.display=display;
		this.shell=shell;
		shell.setText(title); //set window text 
		shell.setSize(width,height); //set window heights
	}
	
	public BasicWindow(String title, int width, int height) {
		display=new Display(); // creates display
		shell=new Shell(display); //creates a window inside the display
		shell.setText(title); //set window text 
		shell.setSize(width,height); //set window heights
	}
	
	abstract void initWidgets(); // a function that will be later implemented and will have all widgets inside the windows
	
	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
		// System.exit(0);
	}

	public Shell getShell() {
		return shell;
	}

}

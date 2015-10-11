package GUI;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import presenter.Command;
import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.Adapter;
import view.View;

public class MazeWindow extends BasicWindow implements View{

	Timer timer;
	TimerTask task;

	protected HashMap<String, Command>  commands;

	protected Command LastUserCommand =null;

	Properties prop;

	String mazeName=null;

	//Maze3d dataRecieved=null; 

	MazeProperties input;

	CommonBoard boardWidget;
	MazeBoard mazeBoard;


	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}



	@Override
	void initWidgets() {

		//sets the background image to white
		shell.setBackground(new Color(null,255,255,255));
		shell.setLayout(new GridLayout(2,false));
		shell.setText("Maze3D Game"); //sets the text of window
		
		//StartButton//
		Button start=new Button(shell, SWT.PUSH);
		start.setText("Start");
		start.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		//StopButton//
		Button stopButton=new Button(shell, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		stopButton.setEnabled(false);

		//GenerateButton//
		Button GenerateButton=new Button(shell, SWT.PUSH);
		GenerateButton.setText("Generate");
		GenerateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		//DisplayButton//
		Button DisplayButton=new Button(shell, SWT.PUSH);
		DisplayButton.setText("displayMaze");
		DisplayButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));


		//SolveButton//
		Button SolveButton=new Button(shell, SWT.PUSH);
		SolveButton.setText("solveMaze");
		SolveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));


		//Display Button Listener//	
		DisplayButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String [] args= {"display","adi"};
				Command command = commands.get("display");
				command.setArguments(args);
				setUserCommand(command);
							
			}
		});

		//Generate Button Listener/
		GenerateButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent e) {
				input = new MazeProperties("adi",20,2,20);

				String x = "" + input.getRows();
				String y = "" +input.getFloors(); 
				 //<-------------החרא הזה שווה 2
				String z = "" + input.getColumns(); 

				String [] args= {"generate", "maze", "3d",input.getMazeName(), x, y, z}; 
				Command command = commands.get("generate");
				command.setArguments(args);
				//System.out.println("3"); //this is the 3! that he prints
				setUserCommand(command);			
			}

		});

		//Stop Button Listener/
		stopButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				task.cancel();
				timer.cancel();
				start.setEnabled(true);
				stopButton.setEnabled(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

	
	
	//Solve Button Listener/
	SolveButton.addSelectionListener(new SelectionListener(){

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {}

		@Override
		public void widgetSelected(SelectionEvent e) {
			System.out.println(input.getMazeName());
			if(input.getMazeName()!=null ){//&& !boardWidget.won){
				
//				String[] constantArgs=new String[2];
//				constantArgs[0] = Constant.MODEL_SOLVED;
//				constantArgs[1] = input.getMazeName();
//				setChanged(); //solve the maze
//				notifyObservers(constantArgs);
				String [] args= {"solve",input.getMazeName(),"astar"};
				Command command= commands.get("solve");
				command.setArguments(args);
				setUserCommand(command);
				
			
			}
			else{ //if there is no maze to solve
				MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				messageBox.setText("Information");
				messageBox.setMessage("No maze to solve");
				messageBox.open();		
		}
		}

	});

	}
	
	
	//-------------------------view methods--------------------------------//


	@Override
	public void dirCommand(String fileName) {
		// not relevant for this view

	}

	@Override
	public <T> void displayModel(Adapter<T> adapter) {
		mazeBoard = new MazeBoard(shell, SWT.BORDER);
		mazeBoard.SetBoardData((Adapter<Maze3d>) adapter);
		mazeBoard.setLayoutData(new GridData (SWT.FILL, SWT.FILL,true,true,2,2));
		
		shell.layout();
		//mazeBoard.update();
		//mazeBoard.redraw();
		//boardWidget.displayProblem(adapter);
	}

	@Override
	public <T> void displayCrossSectionBy(Adapter<T> draw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void displaySolution(Solution<T> solution) {
		boardWidget.displaySolution((Solution<Position>)solution);

	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;}

	@Override
	public void displayString(String toPrint) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
		messageBox.setText("Information");
		messageBox.setMessage(toPrint);
		messageBox.open();

	}

	@Override
	public Command getUserCommand() {

		return LastUserCommand;
	}
	@Override
	public void setUserCommand(Command command) {
		LastUserCommand= command;
		setChanged();
		notifyObservers("New command"); 

	}

	@Override
	public void exit() {
		/*if(boardWidget!=null){

			boardWidget.destructBoard();
			boardWidget.dispose();
		}*/

	}

	public Properties getProperties() {
		return prop;
	}

	public void setProperties(Properties prop) {
		this.prop = prop;
	}
	@Override
	public void start() {
		this.run();

	}








}

//Menu menuBar = new Menu(shell, SWT.BAR);
/*		Button startButton=new Button(shell, SWT.PUSH);
	startButton.setText("Start");
	startButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));*/

//------------------PRINT TEST ---------------------------------//
/*		System.out.println("Displaying cross section: ");
		System.out.println();

		for (int i = 0; i < tempmaze.length; i++) {
			for (int j = 0; j < tempmaze[i].length; j++) {
				System.out.print(tempmaze[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();*/
//------------------PRINT TEST ---------------------------------//
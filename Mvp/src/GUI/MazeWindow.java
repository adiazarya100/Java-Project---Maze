package GUI;

import generic.Run;
import generic.RunCLI;
import generic.RunGUI;
import generic.WritePropertiesGUI;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

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

	MazeProperties input=null;

	CommonBoard boardWidget;
	MazeBoard mazeBoard;
	
	

	
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}



	@Override
	void initWidgets() {

		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				exit();
				//display.dispose();
				//setUserCommand(commands.get("exit"));
			}

		});
		
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

		Image img = new Image(getDisplay(),".\\resources\\images\\star.png");
		//SolveButton//
		Button SolveButton=new Button(shell, SWT.PUSH);
		SolveButton.setText("solveMaze");
		SolveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		
		//getShell().setBackgroundImage(new Image(getDisplay(),".\\resources\\images\\star.png"));
		
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
				input = new MazeProperties("adi",10,2,10);
				//input = new MazeProperties();
				String x = "" + input.getRows();
				String y = "" +input.getFloors(); 
				String z = "" + input.getColumns(); 
				System.out.println(x);
				System.out.println(y);
				System.out.println(z);
				String [] args= {"generate", "maze", "3d",input.getMazeName(), x, y, z}; 
				Command command = commands.get("generate");
				command.setArguments(args);
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
			
			if(input!=null){
				//String tmpName=input.getMazeName();
				//System.out.println(tmpName); 
				//System.out.println("1");
		
				
			//if(!tmpName.equals(null)){//&& !boardWidget.won){
				
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
			else{ 
				displayString("No maze to solve, try to generate it first.");
			   }
		}

	});
	

	
	//creates a tool bar
	Menu menuBar = new Menu(shell, SWT.BAR);
	//creates a file category in toolbar
	MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
	cascadeFileMenu.setText("&File");
	Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	cascadeFileMenu.setMenu(fileMenu);


	//creates a help category in toolbar
	MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
	cascadeHelpMenu.setText("&Help");
	Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
	cascadeHelpMenu.setMenu(HelpMenu);
	
	//add item to file menu open properties
			MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Open Properties");
			item.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {


				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
					fd.setText("open");
					fd.setFilterPath("C:\\");
					String[] filterExt = { "*.xml"};
					fd.setFilterExtensions(filterExt);
					String filename=fd.open(); //choose the file
					if(filename!=null){
						setProperties(filename);
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								switch(prop.getUi())
								{
								case CLI: //if the properties calls for CLI
									exit(); //dispose all data
									display.dispose(); //dispose display
									setUserCommand(commands.get("exit"));
									RunCLI demo=new RunCLI(); //call for a function that works with cli
									demo.startProgram(getProperties());
									break;
								case GUI:
									exit();// dispose all and close timer task
									display.dispose();
									setUserCommand(commands.get("exit"));
									RunGUI demoG = new RunGUI(); //calls for a function that recreates a gui window
									demoG.start(getProperties());
									break;
								default:
									return;	
								}
							}
						});
					}
				}

			});
	
			//new item to file menu
			item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Write Properties");
			item.addSelectionListener(new SelectionListener(){

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {

				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					display.asyncExec(new Runnable() {

						@Override
						public void run() {//this function works on the same basis as open Properties the only difference is the source of the Properties data here we recieve it directly from the user
							WritePropertiesGUI guiProp=new WritePropertiesGUI();
							if(guiProp.writeProperties(shell)!=-1)
							{
								prop=Run.readProperties();
								switch(prop.getUi())
								{
								case CLI:
									exit();
									display.dispose();
									LastUserCommand= commands.get("exit");
									setChanged();
									notifyObservers();
									RunCLI demo=new RunCLI();
									demo.startProgram(getProperties());
									break;
								case GUI:
									exit();
									display.dispose();
									LastUserCommand = commands.get("exit");
									setChanged();
									notifyObservers();
									RunGUI demoG = new RunGUI();
									demoG.start(getProperties());
									break;
								default:
									return;	
								}
							}
						}
					});


				}

			});
			//new item for file menu
			item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Exit");
			item.addSelectionListener(new SelectionListener(){

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {


				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					exit();
					display.dispose();
					setUserCommand(commands.get("exit"));	
				}

			});

			item = new MenuItem(HelpMenu, SWT.PUSH);
			item.setText("About");
			item.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
					messageBox.setText("Information");
					messageBox.setMessage("Game By Zlil Korman & Adi Azarya, Enjoy :)!");
					messageBox.open();

				}

			});
			shell.setMenuBar(menuBar);
			
			
			
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
		//boardWidget.displaySolution((Solution<Position>)solution);
		//String s = solution.toString();
		/*System.out.println("1111111");
		System.out.println(solution.toString());*/
		this.displayString(solution.toString());

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
		if(mazeBoard!=null){
			mazeBoard.dispose();
			this.shell.dispose();
		}
		else
			this.shell.dispose();
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




	protected void setProperties(String filename) {

		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			prop=(Properties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
			displayString("Error Loading Properties");
		}
	}



}


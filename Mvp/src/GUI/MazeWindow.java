package GUI;

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

import boot.Run;
import boot.RunCLI;
import boot.RunGUI;
import presenter.Command;
import presenter.Properties;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.Adapter;
import view.View;


/**
 * The Class MazeWindow.
 */
public class MazeWindow extends BasicWindow implements View{

	/** The timer. */
	Timer timer;
	
	/** The task. */
	TimerTask task;

	/** The commands. */
	protected HashMap<String, Command>  commands;

	/** The Last user command. */
	protected Command LastUserCommand =null;

	/** The prop. */
	Properties prop;

	/** The maze name. */
	String mazeName=null;

	/** The input. */
	MazeProperties input=null;
	
	/** The data recieved. */
	Maze3d dataRecieved=null; 

	/** The board widget. */
	CommonBoard boardWidget;
	
	/** The maze board. */
	MazeBoard mazeBoard;
	
	/** The bool display. */
	boolean boolDisplay=false;
	
	/** The bool generate. */
	boolean boolGenerate=false;

	
	/**
	 * Instantiates a new maze window.
	 *
	 * @param display the display
	 * @param shell the shell
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}

	/**
	 * Instantiates a new maze window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}


	/* (non-Javadoc)
	 * @see GUI.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {

		//sets the background image to white
		shell.setBackground(new Color(null,255,255,255));
		shell.setLayout(new GridLayout(2,true));
		shell.setText("Maze3D Game"); //sets the text of window

		//GenerateButton//
		Button GenerateButton=new Button(shell, SWT.PUSH);
		GenerateButton.setText("Generate");
		GenerateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false, 1, 1));

		//DisplayButton//
		Button DisplayButton=new Button(shell, SWT.PUSH);
		DisplayButton.setText("Display The Maze");
		DisplayButton.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false, 1, 1));

		//newProperties Button//
		Button newProperties=new Button(shell, SWT.PUSH);
		newProperties.setText("New Properties");
		newProperties.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false, 2, 1));

		//HelpMeButton//
		Button HelpMe=new Button(shell, SWT.PUSH);
		HelpMe.setText("Help Me!");
		HelpMe.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 2, 1));
		
		//EXIT Button//
		Button exit=new Button(shell, SWT.PUSH);
		exit.setText("Exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false, 2, 1));

		//EXIT Button Listener//	
		exit.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				exit();

			}
		});

		//Display Button Listener//	
		DisplayButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String [] args= {"display",input.getMazeName()};
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
				
				ClassInputDialog dlg = new ClassInputDialog(shell,MazeProperties.class);
				MazeProperties tempInput=(MazeProperties)dlg.open();
				if(boolGenerate==false && tempInput!=null){
					//input = new MazeProperties("adi",20,2,20);
					input=tempInput;
					boolDisplay = true;
					boolGenerate= true;

					String x = "" + input.getRows();
					String y = "" + input.getFloors(); 
					String z = "" + input.getColumns();
					
					String [] args= {"generate", "maze", "3d",input.getMazeName(), x, y, z}; 
					Command command = commands.get("generate");
					command.setArguments(args);
					setUserCommand(command);
					} 
				else{
					input = new MazeProperties();
					String x = "" + input.getRows();
					String y = "" + input.getFloors(); 
					String z = "" + input.getColumns();
					String [] args= {"generate", "maze", "3d",input.getMazeName(), x, y, z}; 
					Command command = commands.get("generate");
					command.setArguments(args);
					setUserCommand(command);
				}				
			}

		});

		//Write New Properties Button Listener/
		newProperties.addSelectionListener(new SelectionListener() {

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

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});


		//HelpMe Button Listener//	
		HelpMe.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(input!=null ){
			
					String [] args= {"solve",input.getMazeName(),"null"};

					Command command= commands.get("solve");
					command.setArguments(args);
					setUserCommand(command);
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
				//display.dispose();
				//setUserCommand(commands.get("exit"));	
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
				messageBox.setMessage("Maze3D Create By Adi Azarya & Zlil Korman ");
				messageBox.open();

			}

		});
		shell.setMenuBar(menuBar);
	}


	
	
	//-------------------------view methods--------------------------------//


	/* (non-Javadoc)
 * @see view.View#dirCommand(java.lang.String)
 */
@Override
	public void dirCommand(String fileName) {
		// not relevant for this view

	}

	/* (non-Javadoc)
	 * @see view.View#displayModel(view.Adapter)
	 */
	@Override
	public <T> void displayModel(Adapter<T> adapter) {
		if(boolDisplay==true){
		mazeBoard = new MazeBoard(shell, SWT.BORDER);
		mazeBoard.SetBoardData((Adapter<Maze3d>) adapter);
		mazeBoard.setLayoutData(new GridData (SWT.FILL, SWT.FILL,true,true,2,2));
		shell.layout();}
		boolDisplay=false;
		//mazeBoard.update();
		//mazeBoard.redraw();
		//boardWidget.displayProblem(adapter);
	}

	/* (non-Javadoc)
	 * @see view.View#displayCrossSectionBy(view.Adapter)
	 */
	@Override
	public <T> void displayCrossSectionBy(Adapter<T> draw) {}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(algorithms.search.Solution)
	 */
	@Override
	public <T> void displaySolution(Solution<T> s) {
		//mazeBoard.displaySolution(s);
		int xz = 0;
		int xt = 0;
		int yt = 0;
		int index =0;
		String Solution = s.toString();
		Solution=Solution.replace("(", "");
		Solution=Solution.replace(")", "");
		String []path = Solution.split(" ");
		//s.toString();
		int[]tmpArray =new int[(path.length)*3];
		String[] tmp = null;
		//System.out.println(path.length-1);
		
		for(int i=0;i<path.length;i++){
		  tmp = path[i].split(",");
		  xz = Integer.parseInt(tmp[0]);
		  xt = Integer.parseInt(tmp[1]);
		  yt = Integer.parseInt(tmp[2]);
	/*	  System.out.println(xz);
		  System.out.println(xt);
		  System.out.println(yt);*/
		  tmpArray[index] = xz;
		  tmpArray[index+1] = xt;
		  tmpArray[index+2] = yt;
		  index+=3;
		  
		}
		mazeBoard.setSolutionArray(tmpArray);
		mazeBoard.won =true;
	}

	/* (non-Javadoc)
	 * @see view.View#setCommands(java.util.HashMap)
	 */
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;}

	/* (non-Javadoc)
	 * @see view.View#displayString(java.lang.String)
	 */
	@Override
	public void displayString(String toPrint) {
		if(toPrint!=null){
			MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			messageBox.setText("Information");
			messageBox.setMessage(toPrint);
			messageBox.open();}
		else
			System.out.println("");
		
	}

	/* (non-Javadoc)
	 * @see view.View#getUserCommand()
	 */
	@Override
	public Command getUserCommand() {

		return LastUserCommand;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setUserCommand(presenter.Command)
	 */
	@Override
	public void setUserCommand(Command command) {
		LastUserCommand= command;
		setChanged();
		notifyObservers("New command"); 

	}

	/* (non-Javadoc)
	 * @see view.View#exit()
	 */
	@Override
	public void exit() {
		if(mazeBoard!=null){
			mazeBoard.dispose();
			this.shell.dispose();
		}
		else
			this.shell.dispose();
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties() {
		return prop;
	}

	/**
	 * Sets the properties.
	 *
	 * @param prop the new properties
	 */
	public void setProperties(Properties prop) {
		this.prop = prop;
	}
	
	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() {
		this.run();

	}




	/**
	 * Sets the properties.
	 *
	 * @param filename the new properties
	 */
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


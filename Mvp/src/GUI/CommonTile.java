package GUI;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonTile extends Canvas implements Tile{
	
	Image cellImage; 
	String imageName;
	
	CommonCharacter ch = null;  
	
	Image goal =null; 
	public CommonTile(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {   
				drawTile(e);
		}
	});
	}
	
	@Override
	public abstract void drawTile(PaintEvent e);
	
	
	 
	public  void setGoal(Image img){
		this.goal=img;
	}
	
	public  Image getGoal(){
		return this.goal;
	}
	
	public void setCellImage(Image img){
		this.cellImage=img;
	}
	
	public  void setCharacter(CommonCharacter character){
		this.ch=character;
	}
	
	public  String getImageName()
	{
		return this.imageName;
	}
	
	public void setImageName(String name)
	{
		this.imageName=name;
	}
	
	public  Image getCellImage(){
		return this.cellImage;
	}
	
	public  CommonCharacter getCharacter(){
		return this.ch;
	}
	

}


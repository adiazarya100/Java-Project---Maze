package GUI;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonCharacter extends Canvas implements Character {

	int cellX = 0;
	int cellY = 0;
	
	
	CommonCharacter(Composite parent, int style) {
		super(parent, style);
		
	}
	
	@Override
	public void drawCharacter() {
		this.redraw();
		
	}
	

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}

	@Override
	public abstract ImageData[] getCharacterImagesArray();

	@Override
	public abstract void setCharacterImageArray(ImageData[] image);

	@Override
	public abstract int getCharacterImageIndex();

	@Override
	public abstract void setCharacterImageIndex(int index);



	

}
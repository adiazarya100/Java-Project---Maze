package GUI;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;

public class MazeCharacter extends CommonCharacter {
	
	ImageLoader gifs = new ImageLoader();

	ImageData [] images;
	int frameIndex = 0;
	
	MazeCharacter(Composite parent, int style) {
		super(parent, style);
		this.images = gifs.load(".\\resources\\images\\UpAndDown.gif");//TODO need to paste the path here
	}

	@Override
	public ImageData[] getCharacterImagesArray() {
		
		return images;
	}

	@Override
	public void setCharacterImageArray(ImageData[] image) {
		this.images = image;

	}

	@Override
	public int getCharacterImageIndex() {
		
		return frameIndex;
	}

	@Override
	public void setCharacterImageIndex(int index) {
		this.frameIndex = index;
	}



}
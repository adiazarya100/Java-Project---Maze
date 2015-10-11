package GUI;

import org.eclipse.swt.graphics.ImageData;

// TODO: Auto-generated Javadoc
/**
 * The Interface Character.
 */
public interface Character {
	
	/**
	 * Draw character.
	 */
	public void drawCharacter();
	
	/**
	 * Gets the character images array.
	 *
	 * @return the character images array
	 */
	public ImageData[] getCharacterImagesArray();
	
	/**
	 * Sets the character image array.
	 *
	 * @param image the new character image array
	 */
	public void setCharacterImageArray(ImageData[] image);
	
	/**
	 * Gets the character image index.
	 *
	 * @return the character image index
	 */
	public int getCharacterImageIndex();
	
	/**
	 * Sets the character image index.
	 *
	 * @param index the new character image index
	 */
	public void setCharacterImageIndex(int index);

}

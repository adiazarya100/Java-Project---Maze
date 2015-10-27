package generic;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Shell;

import GUI.ClassInputDialog;
import presenter.Properties;

/**
 * The Class WritePropertiesGUI.
 */
public class WritePropertiesGUI {

	/**
	 * Write properties.
	 *
	 * @param shell the shell
	 * @return the int
	 */
	public int writeProperties(Shell shell)
	{
		XMLEncoder e;
		

	    ClassInputDialog dlg = new ClassInputDialog(shell,Properties.class);
	    Properties input = (Properties) dlg.open();
	    if (input != null) {
	     
	    	try {
				e = new XMLEncoder(new FileOutputStream(Enums.XML_FILE_PATH));
				e.writeObject(input);
				e.flush();
				e.close();
				return 0;
	    	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	    }
	  
	    return -1;
	}
}
package GUI;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.swt.widgets.Dialog;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class ClassInputDialog extends Dialog{

	Shell shell;
	/**	the generic class
	 * 
	 */
	private Class<?> template;
	/**	Property Descriptors give me the ability to see what properties the class contains - and has generic functionalities for setters and getters for fields!
	 * 
	 */
	PropertyDescriptor[] descs;
	/**	I wanna have a robust connection between a property to a text box - that way upon pressing OK I could know what class property was written in it.
	 * 
	 */
	HashMap<PropertyDescriptor,Text> txtMap=new HashMap<PropertyDescriptor,Text>();
	/**	The bonus demanded that this dialog will support all given classes
	 * 	but what happens when a class has an enum? a whole new story with combo-boxes and once again I wanna have a connection between the class field enum to the String that was selected in the form.
	 * 
	 */
	HashMap<PropertyDescriptor,String> enumMap=new HashMap<PropertyDescriptor,String>();

	  /**	This is the reference of the instance I will return.
	 * 
	 */
	private Object input;

	  /**	Ct'r for people that don't know a thing about SWT.
	 * @param parent - Shell
	 * @param template - The Class to create form to
	 */
	public ClassInputDialog(Shell parent,Class<?> template) {
		    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,template);
		  }

	  /** Ct'r with SWT style
	 * @param parent - Shell
	 * @param style - SWT style
	 * @param template - The Class to create form to
	 */
	public ClassInputDialog(Shell parent, int style,Class<?> template) {
	    super(parent, style);
	    this.template=template;
		descs=PropertyUtils.getPropertyDescriptors(template);
	    setText("Set Properties");
	  }


	  public Object getInput() {
	    return input;
	  }

	  public void setInput(Object input) {
	    this.input = input;
	  }

	  /**	Here the window layout is set and the main loop event happens. When window closes User's input is returned.
	 * @return The user's input
	 */
	public Object open() {
	    this.shell = new Shell(getParent(), getStyle());
	    shell.setText(getText());
	    createContents(shell);
	    shell.pack();
	    shell.open();
	    Display display = getParent().getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    //display.dispose();
	    return input;
	  }

	  /**	Creates Window content and layout - sets Labels, Text boxes and combo boxes nicely.
	 * @param shell - window's parent
	 */
	private void createContents(final Shell shell) {
	    shell.setLayout(new GridLayout(2, true));
	    for(PropertyDescriptor propDesc: descs)
	    	if(!propDesc.getName().equals("class"))
	    	{
	    		if(!propDesc.getPropertyType().isEnum())
	    		{
	    			Label label = new Label(shell, SWT.NONE);
		    	    label.setText(propDesc.getName());
		    	    GridData data = new GridData();
		    	    data.horizontalSpan = 2;
		    	    label.setLayoutData(data);

		    	    final Text text = new Text(shell, SWT.BORDER);
		    	    data = new GridData(GridData.FILL_HORIZONTAL);
		    	    data.horizontalSpan = 2;
		    	    text.setLayoutData(data);
		    	    txtMap.put(propDesc, text);
	    		}
	    		else
	    		{
	    			Label label = new Label(shell, SWT.NONE);
		    	    label.setText(propDesc.getName());
		    	    GridData data = new GridData();
		    	    data.horizontalSpan = 2;
		    	    label.setLayoutData(data);
	    			
		    	    final Combo combo = new Combo(shell, SWT.DROP_DOWN);
		    
		    	    String[] toCombo=new String[propDesc.getPropertyType().getEnumConstants().length];
		    	    for(int i=0;i<propDesc.getPropertyType().getEnumConstants().length;i++)
		    	    	toCombo[i]=propDesc.getPropertyType().getEnumConstants()[i].toString();
		    	    combo.setItems(toCombo);
		    	    data = new GridData(GridData.FILL_HORIZONTAL);
		    	    data.horizontalSpan = 2;
		    	    combo.setLayoutData(data);
		    	    combo.addSelectionListener(new SelectionListener() {
						
						@Override
						public void widgetSelected(SelectionEvent arg0) {
			    	        enumMap.put(propDesc, combo.getText());
						}
						
						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
							
							
						}
					});
		    	   
	    		}
	    		
	    	}
	
	    Button ok = new Button(shell, SWT.PUSH);
	    ok.setText("OK");
	    GridData dataOK = new GridData(GridData.FILL_HORIZONTAL);
	    ok.setLayoutData(dataOK);
	    ok.addSelectionListener(new SelectionAdapter() {
		      @SuppressWarnings({ "unchecked", "rawtypes" })
		public void widgetSelected(SelectionEvent event) {
	        //input = text.getText();
	    	Object t;
			try {
				t = template.newInstance();
				for(PropertyDescriptor propDesc: txtMap.keySet())
		  		{
		  			if(isNumeric(txtMap.get(propDesc).getText()))
		  				PropertyUtils.setNestedProperty(t, propDesc.getName(), Integer.parseInt(txtMap.get(propDesc).getText()));
		  			else
		  				PropertyUtils.setNestedProperty(t, propDesc.getName(), txtMap.get(propDesc).getText());
		  		}
				for(PropertyDescriptor propDesc : enumMap.keySet())
				{
		    	    
		    	    PropertyUtils.setNestedProperty(t, propDesc.getName(), Enum.valueOf((Class<Enum>)propDesc.getPropertyType(),enumMap.get(propDesc)));
				}
		  		input=t;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.toString();
			} catch (InvocationTargetException e) {			
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
	  		
	        shell.close();
	      
		      }});

	    Button cancel = new Button(shell, SWT.PUSH);
	    cancel.setText("Cancel");
	    GridData dataCancel = new GridData(GridData.FILL_HORIZONTAL);
	    cancel.setLayoutData(dataCancel);
	    cancel.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        input = null;
	        shell.close();
	      }
	   });

	    shell.setDefaultButton(ok);
	    }
	  /**Helper to know if the text box has a number 
	 * @param str - The string in the text box.
	 * @return
	 */
	private static boolean isNumeric(String str)
	  {
	    NumberFormat formatter = NumberFormat.getInstance();
	    ParsePosition pos = new ParsePosition(0);
	    formatter.parse(str, pos);
	    return str.length() == pos.getIndex();
	  }
}
	
	    

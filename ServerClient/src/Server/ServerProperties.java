/*
 * 
 */
package Server;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The server property.
 */
public class ServerProperties{
	
	
	/** The Constant instance.
	 * pure singleton - Ensure that only one instance of a class is created*/
	private final static ServerProperties instance = new ServerProperties();
	
	/** The properties. */
	private Properties properties;
	
	/**
	 * Gets the single instance of ServerProperties.
	 *
	 * @return single instance of ServerProperties
	 */
	public static ServerProperties getInstance() {
		return instance;
	}
	
	/**
	 * Instantiates a new server properties.
	 */
	public ServerProperties() {
		this.properties=readProperties();
		properties.setPort(7070);//Port=6767;
		properties.setNumOfClients(10);//numOfClients=10;
		}
	
	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Properties getProperties(){
		return properties;
	}
	
	
	/**
	 * Read properties.
	 *
	 * @return the properties
	 */
	private Properties readProperties()
	{
		XMLDecoder d;
		Properties p=null;
		try {
			BufferedInputStream in=new BufferedInputStream(new FileInputStream("./ServerProperties.zip"));
			d=new XMLDecoder(in);
			p=(Properties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e) {
			return new Properties();
		}
		return p;
	}
}
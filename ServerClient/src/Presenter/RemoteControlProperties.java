package Presenter;

import java.io.Serializable;

public class RemoteControlProperties implements Serializable{
/*	
	*//** The Constant serialVersionUID. *//*
	private static final long serialVersionUID = 1L;

	*//** The Port for server clients. *//*
	private int PortForServerClients;
	
	*//** The port on which server listens. *//*
	private int portOnWhichServerListens;

	*//** The num of clients. *//*
	private int numOfClients;
	
	*//** The Remote control port listener. *//*
	private int RemoteControlPortListener;

	*//** The ip. *//*
	private String IP;

	*//**
	 * Gets the port on which server listens.
	 *
	 * @return the port on which server listens
	 *//*
	public int getPortOnWhichServerListens() {
		return portOnWhichServerListens;
	}

	*//**
	 * Sets the port on which server listens.
	 *
	 * @param portOnWhichServerListens the new port on which server listens
	 *//*
	public void setPortOnWhichServerListens(int portOnWhichServerListens) {
		this.portOnWhichServerListens = portOnWhichServerListens;
	}


	*//**
	 * Instantiates a new remote control properties.
	 *//*
	public RemoteControlProperties() {
		PortForServerClients=5400;
		numOfClients=32;
		RemoteControlPortListener = 1234;
	}
	
	*//**
	 * Instantiates a new remote control properties.
	 *
	 * @param port the port
	 * @param numOfClients the num of clients
	 * @param RemoteControlPortListener the remote control port listener
	 *//*
	public RemoteControlProperties(int port,int numOfClients,int RemoteControlPortListener) {
		this.PortForServerClients=port;
		this.numOfClients=numOfClients;
		this.RemoteControlPortListener=RemoteControlPortListener;
	}

	*//**
	 * Gets the remote control port listener.
	 *
	 * @return the remote control port listener
	 *//*
	public int getRemoteControlPortListener() {
		return RemoteControlPortListener;
	}

	*//**
	 * Sets the remote control port listener.
	 *
	 * @param RemoteControlPortListener the new remote control port listener
	 *//*
	public void setRemoteControlPortListener(int RemoteControlPortListener) {
		this.RemoteControlPortListener = RemoteControlPortListener;
	}

	*//**
	 * Gets the port server clients.
	 *
	 * @return the port server clients
	 *//*
	public int getPortServerClients() {
		return PortForServerClients;
	}

	*//**
	 * Sets the port server clients.
	 *
	 * @param port the new port server clients
	 *//*
	public void setPortServerClients(int port) {
		this.PortForServerClients = port;
	}

	*//**
	 * Gets the num of clients.
	 *
	 * @return the num of clients
	 *//*
	public int getNumOfClients() {
		return numOfClients;
	}

	*//**
	 * Sets the num of clients.
	 *
	 * @param numOfClients the new num of clients
	 *//*
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	*//**
	 * Gets the ip.
	 *
	 * @return the ip
	 *//*
	public String getIP() {
		return IP;
	}

	*//**
	 * Sets the ip.
	 *
	 * @param iP the new ip
	 *//*
	public void setIP(String iP) {
		IP = iP;
	}*/
}

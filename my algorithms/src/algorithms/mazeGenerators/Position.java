package algorithms.mazeGenerators;

import java.io.Serializable;

/** This class represent positions in the maze.  */

public class Position implements Serializable{
	
	/**
	 * Instantiates a new position.
	 */
	public Position() {
		super();
	}
	
	/** The xyz. */
	private String xyz;
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The z. */
	private int z;
	
	/**
	 * Instantiates a new position.
	 *
	 * @param xyz the xyz
	 */
	public Position(String xyz) //Ctor-Transition from string to - int
	{
		
		String[] arrayxyz = xyz.split(",");
		this.x =Integer.parseInt(arrayxyz[0]) ;
		this.y =Integer.parseInt(arrayxyz[1]) ;
		this.z =Integer.parseInt(arrayxyz[2]) ;
		this.xyz=xyz;
		//System.out.println(this.x);//Print for testing
		//System.out.println(this.y);//Print for testing
		//System.out.println(this.z);//Print for testing
	}
	
	/**
	 * Instantiates a new position.
	 *
	 * @param p the p
	 */
	public Position(Position p)
	{
		this.x=p.getX();
		this.y=p.getY();
		this.z=p.getZ();
		this.xyz = p.getXyz();
	}
	
	/**
	 * Instantiates a new position.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Position(int x, int y, int z)//Ctor-Transition from int to - string
	{
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.xyz = x+","+y+","+z;
		//System.out.println(xyz);//Print for testing
		//System.out.println();
	}
	
	/**
	 * Gets the xyz.
	 *
	 * @return the xyz
	 */
	//Getters && Setters
	public String getXyz() {
		return xyz;
	}
	
	/**
	 * Sets the xyz.
	 *
	 * @param xyz the new xyz
	 */
	public void setXyz(String xyz) {
		this.xyz = xyz;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Prints the position.
	 */
	public void PrintPosition()// print position (x,y,z)
	{
		System.out.println("(" + x +"," + y + "," + z + ")");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return ("(" + x +"," + y + "," + z + ")");
	}
	

	
	
	public int[] split()
	{
		String []tmp = this.toString().replace("(", "").replace(")","").split(","); //split the string from toString method
		int tmp2[] = new int[tmp.length]; //new integer array
		for(int i=0; i<tmp2.length; i++)
		{
			tmp2[i]=Integer.parseInt(tmp[i]);  //cast from string array to integer array
		}
		return tmp2;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (xyz == null) {
			if (other.xyz != null)
				return false;
		} else if (!xyz.equals(other.xyz))
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
}

/*
	private String xyz;
	private int x, y, z; //stand for current position
	

	public Position(String xyz)
	{
		
	}
	public Position(int x, int y, int z) {
		super("{" +x +"," +y +","+z+"}"); 
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}

	/*	
	public String getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}
	public String getLastPosition() {
		return lastPosition;
	}
	public void setLastPosition(String lastPosition) {
		this.lastPosition = lastPosition;
	}
	public long getPositionCost() {
		return positionCost;
	}
	public void setPositionCost(long positionCost) {
		this.positionCost = positionCost;
	} */
	


	


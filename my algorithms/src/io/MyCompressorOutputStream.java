package io;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;

public class MyCompressorOutputStream extends OutputStream{
	
	OutputStream out;

	//C'tor
	public MyCompressorOutputStream (OutputStream out){
		this.out=out;
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte array[]) throws IOException {
		int counter=0;
		int i=0;
		int lastInt;
		
		while(i<array.length){
			lastInt=array[i];
			while((i!=array.length) && lastInt==array[i])
			{
				counter++;
				i++;
			}
			this.write(lastInt);		
			this.write(counter);
			counter=0;
			
		/*for(int i=0; i<9 ;i++){ //stand for: 3 cells [0][1][2] are start position, 3 cells are goal position [3][4][5]
			                    //and 3 cells are maze dimensions x,y,z [6][7][8] (see toByteArray method @ Maze3d)
			out.write(array[i]);
		}*/
		
/*		for(int i=0; i<array.length;) //cell number 9 till the end is the maze content
		{
			
			temp = array[i]; //Specifier array content, is it 1 or 0 ? 
			out.write(temp); //write the current content 
			counter=0;       //initialize counter
			while(i!=array.length && array[i]==temp) //while the content doesn't change
			{
				i++;              //continue 
				counter++;        //count how many times this content appears in a row
			}
			out.write(counter);   //write the number of times in a row of the specific content 
			
		}*/
		

		}
	}
}



	



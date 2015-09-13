package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyDecompressorInputStream extends InputStream {

	InputStream in;

	// C'tor
	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte array[]) throws IOException {

		int i = 0;

		int content=0;
		int counter=0;
		while ((i<array.length-1) && ((content = in.read()) != -1) && ((counter = in.read()) != -1)) {
			
			for (int k=0; k < counter; k++, i++) {
				if(i<array.length)
				array[i]=(byte)content;
			}
			
		}
		return 0;
	}

}
package hagana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.SliderUI;

public class Snake {
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		boolean flag =true;
		//ArrayList<String> result = new ArrayList<String>();
		
		String[] commands = null;
		
		
		PrintWriter serverOutput;
		BufferedReader serverInput;
		//ArrayList<String> serverList = new ArrayList<String>();

		Socket server;
			System.out.println("Trying to connect server, IP: " + "127.0.0.1"
					+ " " + "5400");
			server = new Socket("127.0.0.1", 5400);
			serverOutput = new PrintWriter(server.getOutputStream());
			serverInput = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
			for(int j=0;j<1024;j++){
			model m = new model();
			ArrayList<String> serverList = new ArrayList<String>();
			//serverList.removeAll(serverList);
			commands = null;
			serverOutput.println("get head");
			serverOutput.flush();
			serverList.add(serverInput.readLine());

			serverOutput.println("get apple");
			serverOutput.flush();
			serverList.add(serverInput.readLine());
			
			//System.out.println(serverList.get(1));
			
			
			if(flag){
				flag = false;
				System.out.println("please press enter:");
				Scanner input = new Scanner(System.in);
				String test = input.nextLine();
				Thread.sleep(2000);
			}
			long first = System.currentTimeMillis();
			commands = m.withparams(serverList);
			System.out.println("commands num: " + commands.length);
			long sec = System.currentTimeMillis();
			System.out.println(sec-first);
	
					System.out.println("start");
					for(int i=0;i<commands.length;i++){
						serverOutput.println(commands[i]);
						serverOutput.flush();
						String str = serverInput.readLine();
						System.out.println(str);
						System.out.println(commands[i]);
						if(i==0)
						Thread.sleep(140-(sec-first));
						else
							Thread.sleep(140);
						}
					Thread.sleep(140);

						
			}
			
			
			
	}

}

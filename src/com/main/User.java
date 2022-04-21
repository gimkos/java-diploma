package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class User {
	File file = new File("user.txt");
	
	public boolean isUserFileExist() {
		
		return file.exists();
		
	}
	
	public void createUser() {
		try {
			if(!isUserFileExist()) file.createNewFile();
			FileWriter fw = new FileWriter(file);
			
			String userID = createUserID(16);
			
			fw.write(userID);
			
			fw.close();
		}
		catch(IOException e) {
			
		}
	}
	
	public String getUser() {
//		if(!isUserFileExist()) return "";
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			
		}
		return scan.nextLine();
		
	}
	
	public String createUserID(int userIdLength) {
		char[] symbols = {'A', 'B', 'C', 'E', 'F', 'G',  'H',  'J',  'K',  'L', 'M',  'N',  'P',  'Q', 
				'R', 'U', 'V', 'W', 'X', 'Y', 'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'p', 
				'r', 's', 't', 'u', 'v', 'w', 'x', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		
		char[] buf = new char[userIdLength];
		
		
		Random random = new Random();
		for(int i = 0; i< userIdLength; i++) {
			buf[i] = symbols[random.nextInt(symbols.length)];
		}
		
		return new String(buf);
	}
}
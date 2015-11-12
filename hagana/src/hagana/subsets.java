package hagana;

import java.util.Arrays; 
import java.util.Scanner; 

public class subsets {

public static void main(String[] args){ 

final Scanner in = new Scanner(System.in); 
while (true){ 
System.out.println("Enter string : ");
final String masterWord = in.nextLine(); 
char[] word = masterWord.toCharArray(); 
Arrays.sort(word); 
generateUniqueSubsets(word, 0, "");
 } 
 } 

 private static void generateUniqueSubsets(char[] word, int start, String prefix) { 

 //System.out.println("start " + start + " prefix " + prefix); 
 if (start >= word.length) { 
 	System.out.println(prefix);
 	return; } 
 
 char ch = word[start]; 
 int count = 1; 
 for (int i=start+1; i<word.length; i++) { 
 	if (word[i] == ch) { 
 		count++; } 
 	else { 
 		break; } 
 } 
 String next = ""; 
 for (int i=0; i<=count; i++) {
  generateUniqueSubsets(word, start+count, prefix+next); 
  next = next + ch;
   } 
 }
 
}



package examen1.pratique02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public class Pratique02 {
	public static void main(String[] args) throws Exception {
		String chaineDepart="";
		Stack<String> stack01 = new Stack();
		List<String> liste01 = new ArrayList<>();
		Vector<Character> vecteur01 = new Vector();
		Queue<Character> queue01 = new LinkedList<>();
		String resultat01 = "";
		String resultat02 = "";
		
		String resultatFinal = "";
		BufferedReader fdin = null;
		try {
			fdin = new BufferedReader(new FileReader("./dataExamen01.txt"));
			chaineDepart = fdin.readLine();
			for(int i=0 ; i<chaineDepart.length() ; i++) {
				if(i < 4)
					stack01.push(chaineDepart.substring(i, i+1));
				else if(i < 8)
					liste01.add(chaineDepart.substring(i, i+1));
				else if(i < 12)
					vecteur01.add(chaineDepart.charAt(i));
				else
					queue01.add(chaineDepart.charAt(i));
			}
			resultat01+= stack01.pop();
			resultat01+= liste01.get(1);
			for(char str : vecteur01) {
				if(Character.isDigit(str))
					resultat01+=str;
				else
					resultat02+=str;
			}
			for(char str : queue01) {
				if(Character.isAlphabetic(str))
					resultat02+=str;
			}
			resultat01 = new StringBuffer(resultat01).reverse().toString();
			resultat02 = new StringBuffer(resultat02).reverse().toString();
			resultatFinal+= resultat02.substring(0, 8) + " "+resultat01.substring(0,2)+ " "+resultat02.substring(8)+" "+resultat01.substring(2);
			System.out.println(resultatFinal);
			fdin.close();
		}catch(Exception e) {e.printStackTrace();}
		
	}
}

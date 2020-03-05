package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LabyrinthClass {

	int[][] lab = 
			{ { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 0, 0, 2, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 }, // 1
			{ 1, 1, 1, 2, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 }, // 2
			{ 1, 1, 1, 0, 1, 0, 2, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1 }, // 3
			{ 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 }, // 4
			{ 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 }, // 5
			{ 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1 }, // 6
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 }, // 7
			{ 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1 }, // 8
			{ 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1 }, // 9
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1 }, // 10
			{ 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1 }, // 11
			{ 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1 }, // 12
			{ 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1 }, // 13
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, // 14
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1 }, // 15
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1 }, // 16
			{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, // 17
			{ 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1 }, // 18
			{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1 }, // 19
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } // 20
	};
	
	
	public LabyrinthClass() {
		
	}
	
	public int[][] getLab() {
		return lab;
	}
	
	public int getValue(int[][] lab, int x, int y) {
		return lab[y][x];
	}	
	
	public void printLabyrinth(int[][] lab) {
//		for(int[] row : lab)
//	    	System.out.println(Arrays.toString(row));
		
		printLabyrinth(lab, true);
	}
	
	public void printLabyrinth(int[][] lab, boolean correct) {
		if(correct) {
			for (int y = 0; y < lab.length; y++) {
				for (int x = 0; x < lab[y].length; x++) {
					System.out.format("%d, ", lab[y][x]);
				}
				System.out.println();
			}
		} else {
			for (int y = 0; y < lab.length; y++) {
				for (int x = 0; x < lab[y].length; x++) {
					System.out.format("%d, ", lab[x][y]);
				}
				System.out.println();
			}
		}
		
		System.out.println("lab.length = " + lab.length);
		System.out.println("lab[0].length = " + lab[0].length + "\n");
	}
	
	public int[][] loadLabyrinth() {
		int[][] arr = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader("labyrinth.txt"));
			String zeile = null;
			int a = 0;
			
			while((zeile = in.readLine()) != null) {
				a++;
			}
			in.close();

			in = new BufferedReader(new FileReader("labyrinth.txt"));
			
			String[] z = new String[a];
			
			for(int i = 0; i < a; i++) {
				z[i] = in.readLine();
			}
			
			arr = arrToLab(z);
		} catch (IOException e) {
			System.out.println("Es ist ein Lesefehler aufgetreten");
		}
		return arr;
	}

	public void safeLabyrinth() {
		String[] arr = labToArray();
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("labyrinth1.txt")));
			for (int i = 0; i < arr.length; i++) {
				out.println(arr[i]);
			}
		} catch (IOException e) {
			System.out.println("Es ist ein Schreibfehler aufgetreten.");
		}
	}

	public String[] labToArray() {
		String lString = "";
		String[] arrLab = new String[lab.length];
		
		for (int y = 0; y < lab.length; y++) {
			lString = "";
			
			for (int x = 0; x < lab[y].length; x++) {
				lString += lab[y][x];
				
				if (x != lab.length - 1) {
					lString += ",";
				}
			}
			
			if (y != lab.length - 1) {
				lString += "/";
			}
			arrLab[y] = lString;
		}
		return arrLab;
	}
	
	public String toString() {
		return toString(lab);
	}
	
	public String toString(int[][] lab) {
		String lString = "";
		
		for (int y = 0; y < lab.length; y++) {
			for (int x = 0; x < lab[y].length; x++) {
				lString += lab[y][x];
				
				if (x != lab.length - 1) {
					lString += ",";
				}
			}
			
			if (y != lab.length - 1) {
				lString += "/";
			}
		}
		return "L:" + lString;
	}

	public int[][] arrToLab(String[] arrString) {
		int[][] nLab = null;
		String lString = "";
		
		for (int x = 0; x < arrString.length; x++) {
			lString += arrString[x];
		}
		
		nLab = stringToLab(lString);
		
		return nLab;
	}
	
	public int[][] stringToLab(String pString) {
		String[] zeilen = pString.split("/");
		
		int[][] nLab = new int[zeilen.length][];
		
		for (int y = 0; y < zeilen.length; y++) {
			String[] spalten = zeilen[y].split(",");		
			
			nLab[y] = new int[spalten.length];
			
			for (int x = 0; x < spalten.length; x++) {
				nLab[y][x] = Integer.parseInt(spalten[x]);
			}
		}
		return nLab;
	}
}

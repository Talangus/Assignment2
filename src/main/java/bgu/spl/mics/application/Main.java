package bgu.spl.mics.application;


import bgu.spl.mics.application.misc.Initializer;
import java.io.*;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Initializer.runProgram(args[0]);
		Initializer.createOutput(args[1]);
	}
}













































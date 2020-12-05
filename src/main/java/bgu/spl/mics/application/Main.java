package bgu.spl.mics.application;

import bgu.spl.mics.application.misc.CircularIterator;
import bgu.spl.mics.application.misc.Parser;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Gson gson= new Gson();
		Object obj= new JsonReader(new FileReader("/home/spl211/IdeaProjects/Assignment2/input.json"));
		JsonObject jo = (JsonObject) obj;
		System.out.println(jo);
		Parser parser = new Parser();
//		parser.setNumOfEwoks((int) jo.get("Ewoks"));
		Ewoks ewoks = Ewoks.getInstance();
		System.out.println(parser.getNumOfEwoks());
		System.out.println(parser.getLandoDuration());
		System.out.println(parser.getR2D2Duration());

	}
}

package bgu.spl.mics.application;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.misc.CircularIterator;
import bgu.spl.mics.application.misc.Input;
import bgu.spl.mics.application.misc.Parser;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("/home/spl211/IdeaProjects/Assignment2/input.json"));
		Input input = gson.fromJson(reader,Input.class);
		System.out.println(input.getEwoks());


		Future<Boolean> f1 = new Future<>();
		System.out.println(f1.get());

	}
}

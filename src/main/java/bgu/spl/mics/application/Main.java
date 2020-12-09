package bgu.spl.mics.application;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.misc.CircularIterator;
import bgu.spl.mics.application.misc.Input;
import bgu.spl.mics.application.misc.Parser;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.io.*;
import java.util.LinkedList;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.Map;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		Input input=Parser.getInputFromJson(args[0]);
		MessageBus bus=MessageBusImpl.getInstance();
		Ewoks ewoks = Ewoks.createInstance(input.getEwoks());
		Diary diary= Diary.getInstance();
		MicroService leah = new LeiaMicroservice(input.getAttacks());
	   	MicroService han = new HanSoloMicroservice();
		MicroService c3p0 = new C3POMicroservice();
		MicroService r2D2 = new R2D2Microservice(input.getR2D2());
		MicroService lando = new LandoMicroservice(input.getLando());
		Thread threadLeah = new Thread(leah);
		Thread threadHan= new Thread(han);
		Thread threadC3P0 = new Thread(c3p0);
		Thread threadR2D2= new Thread(r2D2);
		Thread threadLando = new Thread(lando);
		threadHan.start();
		threadC3P0.start();
		threadLeah.start();
		threadR2D2.start();
		threadLando.start();
		try{
			threadHan.join();
			threadC3P0.join();
			threadLando.join();
			threadLeah.join();
			threadR2D2.join();
		}catch (InterruptedException e){}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter("/home/spl211/output.json");
		gson.toJson(diary,writer);
		writer.flush();
		writer.close();
	}
}













































package bgu.spl.mics.application;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.misc.Input;
import bgu.spl.mics.application.misc.Parser;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		Input input=Parser.getInputFromJson("/home/spl211/Desktop/ass2Test/input4.json");

		for(int i=0;i<=5000;i++) {
			System.out.println("iteration: "+(i+1));
			MessageBusImpl bus=MessageBusImpl.getInstance();
			bus.setUninitializedThreads(new AtomicInteger(5));
			Ewoks ewoks = Ewoks.createInstance(input.getEwoks());
			Diary diary= Diary.getInstance();
			MicroService leah = new LeiaMicroservice(input.getAttacks());
			MicroService han = new HanSoloMicroservice();
			MicroService c3p0 = new C3POMicroservice();
			MicroService r2D2 = new R2D2Microservice(input.getR2D2());
			MicroService lando = new LandoMicroservice(input.getLando());
			Thread threadLeah = new Thread(leah);
			Thread threadHan = new Thread(han);
			Thread threadC3P0 = new Thread(c3p0);
			Thread threadR2D2 = new Thread(r2D2);
			Thread threadLando = new Thread(lando);
			threadLando.start();
			threadHan.start();
			threadC3P0.start();
			threadLeah.start();
			threadR2D2.start();
			try {
				threadHan.join();
				threadC3P0.join();
				threadLando.join();
				threadLeah.join();
				threadR2D2.join();
			} catch (InterruptedException e) {
			}
			if(diary.getHanSoloFinish()==0)
				System.out.println("HANSOLO DID NOT RUN!!!!!!!!!!!!!!!");
			if(diary.getC3POFinish()==0)
				System.out.println("C3P0 DID NOT RUN!!!!!!!!!!!!!!!!!!");
			System.out.println("total attacks is correct: "+ diary.getTotalAttacks().compareAndSet(1,0));
			bus.setUninitializedThreads(new AtomicInteger(5));

		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter("/home/spl211/output.json");
		//gson.toJson(diary,writer);
		writer.flush();
		writer.close();


		}

	}














































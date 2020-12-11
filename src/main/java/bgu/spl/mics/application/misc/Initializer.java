package bgu.spl.mics.application.misc;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class Initializer {

    public static void runProgram(String filepath) throws IOException {
        Input input=Parser.getInputFromJson(filepath);
        MessageBus bus= MessageBusImpl.getInstance();
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
    }

    public static void createOutput(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filepath);
        gson.toJson(Diary.getInstance(),writer);
        writer.flush();
        writer.close();
    }

}

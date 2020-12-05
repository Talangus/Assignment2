package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MessageBus;
import bgu.spl.mics.application.misc.Parser;

import java.util.LinkedList;
import java.util.List;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static Ewoks instance;
    private List<Ewok> ewoks;

    public static Ewoks getInstance() {
        if(instance==null)
            instance=new Ewoks(Parser.getNumOfEwoks());
        return instance;
    }

    public List<Ewok> getEwoks(){
        return ewoks;
    }

    private Ewoks(int numOfEwoks){
        ewoks = new LinkedList<>();
        for(int i=0; i<numOfEwoks;i++){
            ewoks.add(new Ewok(i));
        }
    }


}
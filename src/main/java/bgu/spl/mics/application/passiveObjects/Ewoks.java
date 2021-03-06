package bgu.spl.mics.application.passiveObjects;



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
    private static Ewoks instance=null;
    private List<Ewok> ewoks;
    private int numOfEwoks;

    public static Ewoks getInstance(){
        return createInstance(0);
    }

    public static Ewoks createInstance(int numOfEwoks) {
        if(instance==null)
            instance=new Ewoks(numOfEwoks);
        return instance;
    }

    public List<Ewok> getEwoks(){
        return ewoks;
    }

    private Ewoks(int numOfEwoks){
        this.numOfEwoks=numOfEwoks;
        ewoks = new LinkedList<>();
        for(int i=0; i<numOfEwoks;i++){
            ewoks.add(new Ewok(i));
        }
    }

}
package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.MicroService;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
//    static Diary instance=null;
    AtomicInteger totalAttacks = new AtomicInteger(0);
    HashMap<MicroService,Long> attackersFinishTime;
    HashMap<MicroService,Long> terminationTimes;
    long R2D2Deactivate;

    private static class SingletonHolder{
        private static Diary instance=new Diary();
    }

    public static Diary getInstance(){
        return SingletonHolder.instance;
    }

    private Diary(){
        attackersFinishTime=new HashMap<>();
        terminationTimes=new HashMap<>();
    }

    public void addTerminationTime(MicroService m, long time){terminationTimes.put(m,time);}
    public void addAttackerFinishTime(MicroService m, long time){attackersFinishTime.put(m,time);}
    public void setR2D2Deactivate(long time){R2D2Deactivate=time;}

    public void incrementTotalAttacks(){
        int val;
        do{
            val=totalAttacks.get();
        } while(!totalAttacks.compareAndSet(val,val+1));
    }

    public AtomicInteger getTotalAttacks(){return totalAttacks;}
}

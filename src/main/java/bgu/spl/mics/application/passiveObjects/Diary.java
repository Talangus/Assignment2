package bgu.spl.mics.application.passiveObjects;



import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {

    AtomicInteger totalAttacks = new AtomicInteger(0);
    long HanSoloFinish;
    long C3POFinish;
    long R2D2Deactivate;
    long LeiaTerminate;
    long HanSoloTerminate;
    long C3POTerminate;
    long R2D2Terminate;
    long LandoTerminate;

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }

    public void resetNumberAttacks() {
        totalAttacks.compareAndSet(totalAttacks.get(),0);
    }


    private static class SingletonHolder{
        private static Diary instance=new Diary();
    }

    public static Diary getInstance(){
        return SingletonHolder.instance;
    }

    private Diary(){
    }

    public void setHanSoloFinish(long duration){HanSoloFinish=duration;}
    public void setC3P0Finish(long duration){C3POFinish=duration;}
    public void setLeiaTerminate(long duration){LeiaTerminate=duration;}
    public void setHanSoloTerminate(long duration){HanSoloTerminate=duration;}
    public void setC3P0Terminate(long duration){C3POTerminate=duration;}
    public void setR2D2Terminate(long duration){R2D2Terminate=duration;}
    public void setLandoTerminate(long duration){LandoTerminate=duration;}
    public void setR2D2Deactivate(long time){R2D2Deactivate=time;}

    public long getHanSoloFinish(){return HanSoloFinish;}
    public long getC3POFinish(){return C3POFinish;}

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public void incrementTotalAttacks(){
        int val;
        do{
            val=totalAttacks.get();
        } while(!totalAttacks.compareAndSet(val,val+1));
    }

    public AtomicInteger getTotalAttacks(){return totalAttacks;}
}

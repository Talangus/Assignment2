package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminationBrodcast;



/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    private long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;

    }

    @Override
    protected void initialize() {

        subscribeBroadcast(TerminationBrodcast.class,c ->{ terminate(); diary.setLandoTerminate(System.currentTimeMillis());});
        subscribeEvent(BombDestroyerEvent.class,(c ->
        {
            try { Thread.sleep(duration);} catch (InterruptedException e){}
            sendBroadcast(new TerminationBrodcast());
        }));
    }
}

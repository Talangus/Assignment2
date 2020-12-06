package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBrodcast;
import bgu.spl.mics.application.misc.Parser;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {

    public LandoMicroservice(long duration) {
        super("Lando");
    }

    @Override
    protected void initialize() {
        bus.register(this);
        subscribeBroadcast(TerminationBrodcast.class,(c -> terminate()));
        subscribeEvent(DeactivationEvent.class,(c ->
        {try{ Thread.sleep(Parser.getLandoDuration());} catch (InterruptedException e){}}));
    }
}

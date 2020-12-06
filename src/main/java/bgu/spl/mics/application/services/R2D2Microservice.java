package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBrodcast;
import bgu.spl.mics.application.misc.Parser;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    public R2D2Microservice(long duration) {
        super("R2D2");
    }

    @Override
    protected void initialize() {
        bus.register(this);
        subscribeBroadcast(TerminationBrodcast.class,(c -> terminate()));
        subscribeEvent(DeactivationEvent.class,(c ->
        {try{ Thread.sleep(Parser.getR2D2Duration());} catch (InterruptedException e){}}));

    }
}

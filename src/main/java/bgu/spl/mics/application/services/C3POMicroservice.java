package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.NoMoreAttackBroadcast;
import bgu.spl.mics.application.messages.TerminationBrodcast;
import bgu.spl.mics.application.misc.AttackEventCallback;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {

    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        bus.register(this);
        subscribeBroadcast(TerminationBrodcast.class,c ->{ terminate(); diary.setC3P0Terminate(System.currentTimeMillis());});
        subscribeBroadcast(NoMoreAttackBroadcast.class, c->{ diary.setC3P0Finish(System.currentTimeMillis());});
        subscribeEvent(AttackEvent.class,new AttackEventCallback());
    }
}

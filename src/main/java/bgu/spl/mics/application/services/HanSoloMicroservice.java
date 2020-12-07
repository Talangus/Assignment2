package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishedAttackingBrodcast;
import bgu.spl.mics.application.messages.NoMoreAttackBroadcast;
import bgu.spl.mics.application.messages.TerminationBrodcast;
import bgu.spl.mics.application.misc.AttackEventCallback;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        bus.register(this);
        subscribeBroadcast(TerminationBrodcast.class,(c -> terminate()));
        subscribeEvent(AttackEvent.class,new AttackEventCallback());
        subscribeBroadcast(NoMoreAttackBroadcast.class, c->{ diary.addAttackerFinishTime(this,System.currentTimeMillis()); });
    }
}

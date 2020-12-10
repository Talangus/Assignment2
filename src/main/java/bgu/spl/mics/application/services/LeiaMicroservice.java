package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
    private boolean hanFinsihed;
    private boolean c3p0Finsihed;


    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		hanFinsihed=false;
		c3p0Finsihed=false;
    }

    @Override
    protected void initialize() {
    	bus.register(this);
        System.out.println("lead started");
    	subscribeBroadcast(TerminationBrodcast.class,c ->{ terminate(); diary.setLeiaTerminate(System.currentTimeMillis());});
//        subscribeBroadcast(FinishedAttackingBrodcast.class, (c -> { System.out.println("got finished attacking event"); CheckMyEvents(); }));
        SendAttacks();
        sendBroadcast(new NoMoreAttackBroadcast());
        CheckMyEvents();
    }

    private void SendAttacks(){
        System.out.println("starting send attack");
        for (Attack obj: attacks) {
            AttackEvent curr = new AttackEvent(obj);
            myEvents.put(curr,sendEvent(curr));
            System.out.println("attack sent");
        }
    }
    private void CheckMyEvents(){
//        System.out.println("checking events");
        for(Event key : myEvents.keySet()){
//            System.out.println(myEvents.get(key).isDone());
            myEvents.get(key).get();
        }
//        System.out.println("sending deactivation event");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        sendEvent(new DeactivationEvent());
    }

}

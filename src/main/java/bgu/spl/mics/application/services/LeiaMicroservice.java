package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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


    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;

    }

    @Override
    protected void initialize() {
    	subscribeBroadcast(TerminationBrodcast.class,c ->{ terminate(); diary.setLeiaTerminate(System.currentTimeMillis());});
    	try{Thread.sleep(200);}catch (InterruptedException e){};    //leia sleeps so that all other threads could initialize before program begins
    	while (!(bus.getUninitializedThreads().get()== 1)){}                 // final check (busy wait, no Sync)
        SendAttacks();
        sendBroadcast(new NoMoreAttackBroadcast());
        System.out.println("lea sent NMA brodcast");
        CheckMyEvents();
    }

    private void SendAttacks(){
        for (Attack obj: attacks) {
            AttackEvent curr = new AttackEvent(obj);
            myEvents.put(curr,sendEvent(curr));
        }
    }
    private void CheckMyEvents(){
        for(Event key : myEvents.keySet()){
            myEvents.get(key).get();
            System.out.println("event resolved");
        }
        sendEvent(new DeactivationEvent());
        System.out.println("sent deactivation brodcast");
    }
}

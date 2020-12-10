package bgu.spl.mics.application.misc;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.Comparator;
import java.util.List;

public class AttackEventCallback implements Callback<AttackEvent> {
    public void call(AttackEvent c){
        List<Integer> resources = c.getAttackinfo().getSerials();
        int duration = c.getAttackinfo().getDuration();
        Ewoks EwoksInstance = Ewoks.getInstance();
        List<Ewok> ewoks = EwoksInstance.getEwoks();
        ewoks.sort(Comparator.comparingInt(Ewok::getSerialNumber));
        for(int i = 0; i<resources.size(); i++){                        //ewoks is ordered, thus resources acquiring is ordered to prevent deadlocks.
            ewoks.get(i).acquire();
        }
        try{ Thread.sleep(duration);} catch (InterruptedException e){}
        for(int i = 0; i<resources.size(); i++){
            ewoks.get(i).release();
        }
        MessageBusImpl.getInstance().complete(c,true);
        Diary diary=Diary.getInstance();
        diary.incrementTotalAttacks();

    }
}

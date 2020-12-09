package bgu.spl.mics.application.misc;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.Comparator;
import java.util.List;

public class AttackEventCallback implements Callback<AttackEvent> {
    public void call(AttackEvent c){
        System.out.println("attack");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        List<Integer> resources = c.getAttackinfo().getSerials();
        int duration = c.getAttackinfo().getDuration();
        Ewoks EwoksInstance = Ewoks.getInstance();
        List<Ewok> ewoks = EwoksInstance.getEwoks();
        ewoks.sort(new Comparator<Ewok>() {
            @Override
            public int compare(Ewok o1, Ewok o2) {
                return o1.getSerialNumber()-o2.getSerialNumber();
            }
        });
        for(int i = 0; i<resources.size(); i++){                        //ewoks is ordered, thus resources acquiring is ordered to prevent deadlocks.
            ewoks.get(i).acquire();
        }
        try{ Thread.sleep(duration);} catch (InterruptedException e){}
        for(int i = 0; i<resources.size(); i++){
            ewoks.get(i).release();
        }
        Diary diary=Diary.getInstance();
        diary.incrementTotalAttacks();
        System.out.println("Total attacks now: "+diary.getTotalAttacks());
    }
}

package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {
    Attack Attackinfo;

    public AttackEvent(Attack AttackObj){
        Attackinfo = AttackObj;
    }

    public Attack getAttackinfo(){return Attackinfo;};
	
}

package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.Message;

public class NewSubBroadcast implements Broadcast {

     private Class myclass;

    public NewSubBroadcast(Class<? extends Message> eventClass){
        myclass = eventClass;
    }
    public Class<? extends Event> getmyclass(){return myclass;}

}

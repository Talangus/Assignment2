package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;

public class UnassignedEventHandler extends MicroService {

    private HashMap<Class<Event<T>>,BlockingDeque<Event>> unassignedEventsQ;

    public UnassignedEventHandler(String _name) {
        super(_name);
        unassignedEventsQ=new HashMap<>();
    }

    @Override
    protected void initialize() {
        bus.register(this);
        subscribeBroadcast(NewRegisterBroadcast.class,c->{
        });
    }
}

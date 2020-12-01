package bgu.spl.mics.application.misc;

import bgu.spl.mics.Future;
import bgu.spl.mics.Message;

public class Packet<T> implements Message{
    Message mess;
    Future<T> future;

    Packet(Message _mess, Future _future){
        mess=_mess;
        future=_future;
    }
}

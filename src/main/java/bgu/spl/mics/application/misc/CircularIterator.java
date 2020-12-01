package bgu.spl.mics.application.misc;

import java.util.Iterator;
import java.util.LinkedList;

public class CircularIterator<E> implements Iterator {
    Iterator<E> realiter;
    LinkedList<E> list;

    public CircularIterator(LinkedList<E> list){
        realiter = list.iterator();
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    public E next(){
        if (!realiter.hasNext())
            realiter = list.iterator();
        return realiter.next();
    }
}

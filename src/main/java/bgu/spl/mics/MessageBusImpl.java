package bgu.spl.mics;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl instance=null;
	private  HashMap<Class<? extends Message>, Queue<MicroService>> SubscribersMap;
	private  HashMap<MicroService, LinkedBlockingQueue<Message>> MessageQueueMap;
	private  ConcurrentHashMap<Event, Future> EventFutureMap;
	private AtomicInteger UninitializedThreads;

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		subscribeMessage(type, m);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		subscribeMessage(type, m);
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		Future<T> temp = EventFutureMap.get(e);
		if (temp != null)
			temp.resolve(result);
	}

	//send broadcast and send event are synchronized because if there is a contact switch
	//between getting the message queue and

	@Override
	public void sendBroadcast(Broadcast b) {
		Queue<MicroService> q = SubscribersMap.get(b.getClass());
		if (q != null) {
			for (MicroService m : q) {
				MessageQueueMap.get(m).add(b);
			}
		}
	}

	
	@Override
	public  <T> Future<T> sendEvent(Event<T> e) {
		Queue<MicroService> q = checkSubscribers(e.getClass());
		MicroService m = q.peek();
		roundRobin(q);				//Microservice is pulled and pushed back into the Queue for round robin
		MessageQueueMap.get(m).add(e);
		Future<T> f = new Future<>();
		EventFutureMap.put(e,f);
        return f;
	}

	public synchronized void roundRobin(Queue<MicroService> q){
		if(q.size()>1)
		{
			MicroService m=q.poll();
			q.add(m);
		}
	}

	@Override
	public void register(MicroService m) {
		synchronized (MessageQueueMap) {// synchronized so that there will not be a contact switch between the time that we have a key in the map and creating our message queue
			MessageQueueMap.put(m, new LinkedBlockingQueue<Message>());
		}
	}

	@Override
	public void unregister(MicroService m) {
		for(Class<?extends Message> c: m.mySubscriptions()){
			SubscribersMap.get(c).remove(m);
		}
		MessageQueueMap.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		if (MessageQueueMap.get(m)==null)
			throw new IllegalStateException();
		Message message = MessageQueueMap.get(m).take();
		return message;
	}

	public static MessageBusImpl getInstance() {
		if (instance == null)
			instance = new MessageBusImpl();
		return instance;
	}

	private MessageBusImpl(){
		SubscribersMap=new HashMap<Class<? extends Message>, Queue<MicroService>>();
		MessageQueueMap=new HashMap<MicroService,LinkedBlockingQueue<Message>>();
		EventFutureMap=new ConcurrentHashMap<Event, Future>();
	}

	//checks if Message of type "type" has a queue in Sub map, creates one if it doesn't
	private  <T> Queue<MicroService> getQueuefromSubMap(Class<? extends Message> type){
		synchronized (SubscribersMap) {
			Queue<MicroService> q = SubscribersMap.get(type);
			if (q == null) {
				q = new LinkedBlockingQueue<MicroService>();
				SubscribersMap.put(type, q);
			}
			return q;
		}
	}
	private synchronized void subscribeMessage(Class<? extends Message> type, MicroService m){
		Queue<MicroService> subscribersQueue = getQueuefromSubMap(type);						//gets the message's microservice queue
		if (!subscribersQueue.contains(m))
			subscribersQueue.add(m);
		notifyAll();
	}

	//makes sure that events of type "type" has subscribers before it is sent
	private <T> Queue<MicroService> checkSubscribers(Class<? extends Event> type) {
		synchronized (SubscribersMap) {
			while (SubscribersMap.get(type) == null || SubscribersMap.get(type).isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {}
				;
			}
			return SubscribersMap.get(type);
		}
	}

	public void setUninitializedThreads(AtomicInteger uninitializedThreads) {
		UninitializedThreads = uninitializedThreads;
	}

	public AtomicInteger getUninitializedThreads() {
		return UninitializedThreads;
	}

	public void decreaseUninitializedThreads(){
		int val;
		do{
			val = UninitializedThreads.get();
		} while (!UninitializedThreads.compareAndSet(val,val -1));
	}
}

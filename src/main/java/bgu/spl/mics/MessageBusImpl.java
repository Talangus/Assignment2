package bgu.spl.mics;

import bgu.spl.mics.application.messages.NewSubBroadcast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private static MessageBusImpl instance=null;
	private  HashMap<Class<? extends Message>, Queue<MicroService>> SubscribersMap;
	private  HashMap<MicroService, LinkedBlockingQueue<Message>> MessageQueueMap;
	private  HashMap<Event, Future> EventFutureMap;

	
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
	public <T> Future<T> sendEvent(Event<T> e) {
		Queue<MicroService> q = checkSubscribers(e.getClass());
		MicroService m = q.poll();							//Microservice is pulled and pushed back into the Queue for round robin
		MessageQueueMap.get(m).add(e);
		q.add(m);
		Future<T> f = new Future<>();
		EventFutureMap.put(e,f);
        return f;
	}

	@Override
	public void register(MicroService m) {
		MessageQueueMap.put(m, new LinkedBlockingQueue<Message>());
	}

	@Override
	public void unregister(MicroService m) {
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
		EventFutureMap=new HashMap<Event, Future>();
	}

	//checks if Message of type "type" has a queue in Sub map, creates one if it doesn't
	private synchronized  <T> Queue<MicroService> getQueuefromSubMap(Class<? extends Message> type){
		Queue<MicroService> q =SubscribersMap.get(type);
		if(q==null) {
			q = new LinkedBlockingQueue<MicroService>();
			SubscribersMap.put(type, q);
			sendBroadcast(new NewSubBroadcast(type));
		}
		return q;
	}
	private synchronized void subscribeMessage(Class<? extends Message> type, MicroService m){
		Queue<MicroService> subscribersQueue = getQueuefromSubMap(type);						//gets the message's microservice queue
		if (!subscribersQueue.contains(m))
			subscribersQueue.add(m);
		notifyAll();
	}

	//makes sure that events of type "type" has subscribers before it is sent
	private synchronized <T> Queue<MicroService> checkSubscribers(Class<? extends Event> type){
		while (SubscribersMap.get(type)==null || SubscribersMap.get(type).isEmpty()){
			try{wait();}catch (InterruptedException e){};
		}
		return SubscribersMap.get(type);
	}


}

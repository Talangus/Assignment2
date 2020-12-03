package bgu.spl.mics;

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

	private static MessageBus instance;
	private  HashMap<Class<? extends Message>, Queue<MicroService>> SubscribersMap;
	private  HashMap<MicroService, Queue<Message>> MessageQueueMap;
	private  HashMap<Event, Future> EventFutrueMap;

	
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
		Future<T> temp = EventFutrueMap.get(e);
		if (temp != null)
			temp.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		
        return null;
	}

	@Override
	public void register(MicroService m) {
		
	}

	@Override
	public void unregister(MicroService m) {
		
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}

	public static MessageBus getInstance() {
		if (instance == null)
			instance = new MessageBusImpl();
		return instance;
	}

	private MessageBusImpl(){
		SubscribersMap=new HashMap<>();
		MessageQueueMap=new HashMap<>();
		EventFutrueMap=new HashMap<>();
	}

	//checks if Message of type "type" has a queue in Sub map, creats one if it doesn't
	private <T> Queue<String> getQueuefromSubMap(Class<? extends Message> type){
		Queue<String> q =SubscribersMap.get(type);
		if(q==null) {
			q = new LinkedBlockingQueue<String>();
			SubscribersMap.put(type, q);
		}
		return q;
	}
	private void subscribeMessage(Class<? extends Message> type, MicroService m){
		Queue<String> q = getQueuefromSubMap(type);
		String name = m.getName();
		if (!q.contains(name))
			q.add(name);
	}

}

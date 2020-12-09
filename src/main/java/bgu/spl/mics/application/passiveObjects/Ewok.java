package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	
    public Ewok(int _serialNumber){
        serialNumber=_serialNumber;
        available=true;
    }
    /**
     * Acquires an Ewok
     */
    public synchronized void acquire() { // needs to be synchronized because an ewok cannot be apart of 2 attacks simultaneously
        while (!available) {               //if not synchronized there can be instances that 2 threads will acquire the ewok
            try { wait(); }
            catch (InterruptedException e) {}
        }
        available = false;
    }

    public int getSerialNumber(){return serialNumber;}
    /**
     * release an Ewok
     */
    public synchronized void release() {
    	if(!available){
    	    available=true;
            notifyAll();
    	}
    }
}

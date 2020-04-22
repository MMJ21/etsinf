// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone

	private int nInstructors;
	private int nKids;

    public void init(int ki, int cap) {
    	nInstructors = 0; nKids = 0;
    }

    public synchronized void kidSwims() throws InterruptedException {
    	while (nInstructors < 1) {
    		log.waitingToSwim();
    		wait();
    	}
    	nKids++;
    	notifyAll();
    	log.swimming();
    }
    public synchronized void kidRests() throws InterruptedException {
    	nKids--;
    	notifyAll();
    	log.resting();
    }
    public synchronized void instructorSwims() throws InterruptedException {
    	nInstructors++;
    	log.swimming();
    }
    public synchronized void instructorRests() throws InterruptedException {
    	while (nInstructors == 1 && nKids > 0) {
    		log.waitingToRest();
    		wait();
    	}
    	nInstructors--;
    	notifyAll();
    	log.resting();
    }
}

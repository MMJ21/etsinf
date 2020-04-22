// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    private int nInstructors;
	private int nKids;
	private int kidInsRatio;
    private int capacity;

    public void init(int ki, int cap) {
    	nInstructors = 0; nKids = 0; kidInsRatio = ki; capacity = cap;
    }

    public synchronized void kidSwims() throws InterruptedException {
    	while (nInstructors < 1 || kidInsRatio * nInstructors <= nKids || nKids + nInstructors == capacity) {
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
        while (nKids + nInstructors == capacity) {
            log.waitingToSwim();
            wait();
        }
    	nInstructors++;
        notifyAll();
    	log.swimming();
    }
    public synchronized void instructorRests() throws InterruptedException {
    	while ((nInstructors == 1 && nKids > 0) || (nKids + kidInsRatio - 1 >= kidInsRatio * nInstructors))  {
    		log.waitingToRest();
    		wait();
    	}
    	nInstructors--;
    	notifyAll();
    	log.resting();
    }
}

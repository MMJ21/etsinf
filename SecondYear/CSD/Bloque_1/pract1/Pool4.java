// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool { //kids cannot enter if there are instructors waiting to exit
    private int nInstructors;
	private int nKids;
	private int kidInsRatio;
    private int capacity;
    private int insWait;

    public void init(int ki, int cap) {
    	nInstructors = 0; nKids = 0; kidInsRatio = ki; capacity = cap; insWait = 0;
    }

    public synchronized void kidSwims() throws InterruptedException {
    	while (nInstructors < 1 || kidInsRatio * nInstructors <= nKids || nKids + nInstructors == capacity || insWait > 0) {
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
    	insWait++;
    	while ((nInstructors == 1 && nKids > 0) || (nKids + kidInsRatio - 1 >= kidInsRatio * nInstructors))  {
    		log.waitingToRest();
    		wait();
    	}
    	insWait--;
    	nInstructors--;
    	notifyAll();
    	log.resting();
    }
}

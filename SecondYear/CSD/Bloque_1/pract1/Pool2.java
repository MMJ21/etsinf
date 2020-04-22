// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    private int nInstructors;
	private int nKids;
	private int kidInsRatio;

    public void init(int ki, int cap) {
    	nInstructors = 0; nKids = 0; kidInsRatio = ki;    }

    public synchronized void kidSwims() throws InterruptedException {
    	while (nInstructors < 1 || kidInsRatio * nInstructors <= nKids) {
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
    	while ((nInstructors == 1 && nKids > 0) || (nKids + kidInsRatio - 1 >= kidInsRatio * nInstructors))  {
    		log.waitingToRest();
    		wait();
    	}
    	nInstructors--;
    	notifyAll();
    	log.resting();
    }
}

// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
	private int capacity;
    public LimitedTable(StateManager state) {
    	super(state);
    	capacity = 0;
    }
    public synchronized void enter(int id) throws InterruptedException {
    	while (capacity == 4) {
    		state.wenter(id);
    		wait();
    	}
    	capacity++;
    	notifyAll();

    	state.enter(id);
    }
    public synchronized void exit(int id) {
    	capacity--;
    	notifyAll();
    	state.exit(id);
    }
    
}

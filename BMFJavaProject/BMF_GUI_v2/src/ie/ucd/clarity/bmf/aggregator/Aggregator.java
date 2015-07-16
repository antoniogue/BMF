package ie.ucd.clarity.bmf.aggregator;

import ie.ucd.clarity.bmf.common.AggregationItem;

import java.util.ArrayList;

/**
 * @author  Matthew
 */
public class Aggregator implements IAggregator {

    /**
	 * @uml.property  name="operation"
	 */
    private int operation;
	private int idRequest;
	private int totalNodes;
	private int counter = 0;
    private ArrayList<AggregationItem> array;
    private boolean first = true;
	private boolean finish = false;
    /**
	 * @uml.property  name="timer"
	 * @uml.associationEnd  
	 */
    Timer timer;
    long result = -1;

    public Aggregator(int id, int operation, int totalNodes) {
        this.operation = operation;
        this.idRequest = id;
        this.totalNodes = totalNodes;
        timer = new Timer();
        array = new ArrayList<AggregationItem>();
    }

    @Override
    public synchronized long addAggregateValue(AggregationItem item) {
        if (first) {
            first = false;
            //timer.start();
        }
        result = -1;
        // aggiungo
        if (array.contains(item)) {
            finish = true;
            result = calculateValueResult();
            array = new ArrayList<AggregationItem>();
            array.add(item);
        } else {
            array.add(item);
        }
        if (array.size() == totalNodes) {
            finish = true;
            System.out.println("finish");
        }
        if (finish) {
            if (result == -1) {
                result = calculateValueResult();
            }
            System.out.println("RISULTATO FINALE " + counter++ + " value: " + result);
            array = new ArrayList<AggregationItem>();
            first = true;
            finish = false;
            //timer.
            //timer = new Timer();
        }
        System.out.println(item);
        return result;
    }

    private long calculateValueResult() {
        switch (operation) {
            case 0:
                return min();
            case 1:
                return max();
            case 2:
                return average();
            case 3:
                return variance();
            default:
                return 0L;
        }
    }

    public int getID() {
        return idRequest;
    }

    /**
	 * @return
	 * @uml.property  name="operation"
	 */
    public int getOperation() {
        return operation;
    }

    private long min() {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getValue() < min) {
                min = array.get(i).getValue();
            }
        }
        return min;
    }

    private long max() {
        long max = Long.MIN_VALUE;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getValue() > max) {
                max = array.get(i).getValue();
            }
        }
        return max;
    }

    private long average() {
        long sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i).getValue();
        }
        return (sum / array.size());
    }

    
    private long variance() {
    	long avg = average();
    	long var=0;
        for (int i = 0; i < array.size(); i++) {
        	var+=((array.get(i).getValue()-avg)*(array.get(i).getValue()-avg));
        }
        return (var/array.size());
    }

    class Timer extends Thread {

        @Override
        public void run() {
            int i = 0;
            while (i < 5 && !finish) { // il thread muore quando ho finito oppure sono passati 30 secondi
                try {
                    this.sleep((1000 * ++i));
                    System.out.println(i);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (i == 5) { // è scattato il timeout
                // devo mandare il valore
                result = calculateValueResult();
                System.out.println("RISULTATO FINALE dal timer " + counter++ + " value: " + result);
                array = new ArrayList<AggregationItem>();
                first = true;
                finish = false;
            }else{

            }

        }
    }
}

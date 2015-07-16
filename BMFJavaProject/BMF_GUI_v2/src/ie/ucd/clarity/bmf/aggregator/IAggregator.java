package ie.ucd.clarity.bmf.aggregator;

import ie.ucd.clarity.bmf.common.AggregationItem;

public interface IAggregator {   

    public long addAggregateValue(AggregationItem item);
    
    public int getID();

}

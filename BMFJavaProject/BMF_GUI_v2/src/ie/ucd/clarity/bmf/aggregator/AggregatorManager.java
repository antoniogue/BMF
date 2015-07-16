package ie.ucd.clarity.bmf.aggregator;

import java.util.ArrayList;

public class AggregatorManager implements IAggregatorManager {

    private ArrayList<IAggregator> iAggregators=new ArrayList<IAggregator>();

    @Override
    public void createIAggregator(int id, int operation, int nNodes) {
        iAggregators.add(new Aggregator(id,operation, nNodes));
    }

    @Override
    public IAggregator getIAggregator(int id){
        for(int i=0; i<iAggregators.size();i++){
            if(iAggregators.get(i).getID()==id)
                return iAggregators.get(i);
        }
        return null;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.aggregator;

/**
 *
 * @author Andrea
 */
public interface IAggregatorManager {

    public void createIAggregator(int id, int operation, int nNodes);

    public IAggregator getIAggregator(int id);

}

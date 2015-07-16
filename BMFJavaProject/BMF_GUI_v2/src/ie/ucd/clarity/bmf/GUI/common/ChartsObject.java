/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common;

import ie.ucd.clarity.bmf.GUI.ChartInternalFrame;
import javax.swing.JComponent;

/**
 * @author  Andrea
 */
public class ChartsObject {
    /**
	 * @uml.property  name="requestChart"
	 * @uml.associationEnd  
	 */
    ChartInternalFrame requestChart;
    /**
	 * @uml.property  name="aggregationChart"
	 * @uml.associationEnd  
	 */
    ChartInternalFrame aggregationChart;
    /**
	 * @uml.property  name="component"
	 */
    JComponent component;

    public ChartsObject(ChartInternalFrame requestChart, ChartInternalFrame aggregationChart, JComponent comp) {
        this.requestChart = requestChart;
        this.aggregationChart = aggregationChart;
        this.component=comp;
    }

    /**
	 * @return
	 * @uml.property  name="aggregationChart"
	 */
    public ChartInternalFrame getAggregationChart() {
        return aggregationChart;
    }

    /**
	 * @return
	 * @uml.property  name="requestChart"
	 */
    public ChartInternalFrame getRequestChart() {
        return requestChart;
    }

    /**
	 * @return
	 * @uml.property  name="component"
	 */
    public JComponent getComponent() {
        return component;
    }

    /**
	 * @param component
	 * @uml.property  name="component"
	 */
    public void setComponent(JComponent component) {
        this.component = component;
    }
    

}

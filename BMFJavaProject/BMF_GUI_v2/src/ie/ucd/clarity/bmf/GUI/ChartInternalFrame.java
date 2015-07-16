package ie.ucd.clarity.bmf.GUI;

import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFRequestObjectGUI;
import ie.ucd.clarity.bmf.common.CodeConversion;
import ie.ucd.clarity.bmf.common.Constants;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A simple demonstration application showing how to create a line chart using data from an {@link XYDataset} .
 */
public class ChartInternalFrame extends JInternalFrame {

    private ArrayList<Series> series;
    private XYDataset dataset;
    /**
	 * @uml.property  name="request"
	 * @uml.associationEnd  
	 */
    private BMFRequestObjectGUI request;
    private String title;
    private String yLabel;
	private String xLabel;
    private long startTime;
	private long lastTime;
    private long timescaledivider;
    private int type; // 0 request 1 aggregation
    private int first=0;

    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public ChartInternalFrame(final String title, BMFRequestObjectGUI request, int type) {
        this.title = title + " ID" + request.getRequest().getRequestID();
        this.request = request;
        if (request.isAggregation()) {
            this.setTitle(title);
        } else {
            this.setTitle(this.title);
        }
        this.type=type;
        yLabel = getYLabel();
        xLabel = getXLabel();
        dataset = new XYSeriesCollection();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setIconifiable(true);
        this.setResizable(true);
        this.setMaximizable(true);
        startTime=System.currentTimeMillis();
        lastTime=startTime;
        initializeSeries(request.getResult());
    }

    private String getXLabel() {
        String s = "Time (";
        int timescale = request.getRequest().getPeriodTimescale();
        switch (timescale) {
            case Constants.TIMESCALE_SEC:
                s += "SEC)";
                timescaledivider=1000;
                break;
            case Constants.TIMESCALE_HOUR:
                s += "HOUR)";
                timescaledivider=1000*60*60;
                break;
            case Constants.TIMESCALE_MIN:
                s += "MIN)";
                timescaledivider=1000*60;
                break;
            case Constants.TIMESCALE_DAY:
                s += "DAY)";
                timescaledivider=1000*60*60*24;
                break;
        }
        return s;
    }

    private String getYLabel() {
    	String s=CodeConversion.getSensorName(request.getRequest().getSensor_actuatorType())+" ("+CodeConversion.getSensorScale(request.getRequest().getSensor_actuatorType())+")";
        return s;
    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    public void createDataset(String Name, double[] array) {
        XYSeries serie = new XYSeries(Name);
        for (int i = 0; i < array.length; i++) {
            serie.add(i, array[i]);
        }
        ((XYSeriesCollection) dataset).addSeries(serie);
    }

    public void AddDataToDataset(int id, double y, long x) {   
    	Series selected = getSerieByID(id);    	
    	if(first<2 && request.getRequest().getSensor_actuatorType()==Constants.SENSOR_TYPE_ELECTRICITY){
    		first++;
    	}else{
    		selected.serie.add(selected.incrX(Math.round(((double)x-(double)lastTime)/(double)timescaledivider)), y);
    	}
        ((XYSeriesCollection) dataset).addSeries(selected.serie);
    	lastTime=x;
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the data for the chart.
     *
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                title, // chart title
                xLabel, // x axis label
                yLabel, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
                );
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

        //        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        plot.setRenderer(renderer);


        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }

    public void displayGUI() {
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setAutoscrolls(false);
        final JScrollPane scroll = new JScrollPane();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        scroll.add(chartPanel);
        scroll.setPreferredSize(chartPanel.getPreferredSize());
        scroll.setViewportView(chartPanel);
        setContentPane(scroll);
        this.pack();
        this.setVisible(true);
    }

    public void displayGUI(JComponent j) {
        final JFreeChart chart = createChart(dataset);
        chart.setTitle((String) null);
        final XYPlot plot = chart.getXYPlot();
        final NumberAxis rangeAxis = (NumberAxis) plot.getDomainAxis();
        rangeAxis.setFixedAutoRange(80);
        final ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setPreferredSize(new java.awt.Dimension(980, 309));
        j.add(chartPanel);
        j.setVisible(true);
    }

    /**
	 * @return
	 * @uml.property  name="request"
	 */
    public BMFRequestObjectGUI getRequest() {
        return request;
    }

    private void initializeSeries(ArrayList<BMFNodeGUI> nodo) {
        series = new ArrayList<Series>();
        if (type==0) {
            for (int i = 0; i < nodo.size(); i++) {
                XYSeries tmp = new XYSeries(nodo.get(i).getName());
                tmp.setDescription(nodo.get(i).getName());
                series.add(new Series(nodo.get(i).getID(), 0, tmp));
                AddDataToDataset(nodo.get(i).getID(), 0.0, startTime);
            }
        } else {
            XYSeries tmp = new XYSeries(request.getName());
            tmp.setDescription(request.getName());
            series.add(new Series(request.getRequest().getRequestID(), 0, tmp));
            AddDataToDataset(request.getRequest().getRequestID(), 0.0, startTime);
        }
        
    }

    private Series getSerieByID(int id) {
        for (int i = 0; i < series.size(); i++) {
            if (series.get(i).id == id) {
                return series.get(i);
            }
        }
        return null;
    }

    /**
	 * @author  Matthew
	 */
    class Series {

        /**
		 * @uml.property  name="id"
		 */
        int id;
		/**
		 * @uml.property  name="x"
		 */
		int x;
        /**
		 * @uml.property  name="serie"
		 */
        XYSeries serie;

        public Series(int id, int x, XYSeries serie) {
            this.id = id;
            this.x = x;
            this.serie = serie;
        }

        /**
		 * @return
		 * @uml.property  name="id"
		 */
        public int getId() {
            return id;
        }

        /**
		 * @return
		 * @uml.property  name="serie"
		 */
        public XYSeries getSerie() {
            return serie;
        }

        /**
		 * @return
		 * @uml.property  name="x"
		 */
        public int getX() {
            return x;
        }

        public int incrX(long incr) {
            return this.x+=incr;
        }
    }
}




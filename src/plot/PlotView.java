package plot;

import Interfaces.Observer;

import javax.swing.*;
import java.awt.*;

public class PlotView extends JPanel implements Observer, Runnable {
    private static final int CANDLE_STICK_WIDTH = 50;
    private PlotModel plotModel;
    private JPanel timeLinePanel = new JPanel();
    private PlotPanel plotPanel = new PlotPanel();

    private int PLOT_VIEW_HEIGHT;
    private int PLOT_VIEW_WIDTH;

    private int PLOT_PANEL_HEIGHT;
    private int PLOT_PANEL_WIDTH;

    private int TIME_LINE_PANEL_HEIGHT;
    private int TIME_LINE_PANEL_WIDTH;

    public PlotView(PlotModel plotModel) {
        this.plotModel = plotModel;
        run();
    }

    //todo dodaj strategie wyswietlania wykresu
    @Override
    public void run() {
        initPlotView();
    }

    private void paintCandleStick(int leftX, CandleStick candleStick, Graphics2D g2D) {
        int y = candleStick.getOpenPriceHeight();
        int width = CANDLE_STICK_WIDTH;
        int height = candleStick.getCurrentPriceHeigth() - candleStick.getOpenPriceHeight();
        int middleOfCandle = leftX + CANDLE_STICK_WIDTH / 2;
        //drawing the knot
        g2D.drawLine(middleOfCandle, candleStick.getMinPriceHeight(), middleOfCandle, candleStick.getMaxPriceHeight());
        //drawing body of candle
        g2D.drawRect(leftX, y, width, height);
    }

    public void initPlotView() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(PLOT_VIEW_WIDTH, PLOT_VIEW_HEIGHT));
        this.add(timeLinePanel, BorderLayout.SOUTH);
        this.add(plotPanel, BorderLayout.CENTER);
        initTimeLinePanel();
        initPlotPanel();
    }

    private void initPlotPanel() {
        plotPanel.setLayout(null);
        plotPanel.setBackground(Color.BLACK);
        plotPanel.setSize(new Dimension(PLOT_VIEW_WIDTH, PLOT_VIEW_HEIGHT - TIME_LINE_PANEL_HEIGHT));
    }

    private void initTimeLinePanel() {
        plotPanel.setBackground(Color.green);
        //plotPanel.setSize(newd)
    }

    @Override
    public void update() {
        plotPanel.repaint();
    }

    private class PlotPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.drawRect(50, 50, 100, 100);
        }

    }
}

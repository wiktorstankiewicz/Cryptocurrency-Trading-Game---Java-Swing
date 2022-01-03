package mainFrame.gameView;

import Interfaces.Observer;
import Utilities.CandleStick;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class PlotView extends JPanel implements Observer {
    private static final int CANDLE_STICK_WIDTH = 50;
    private GameModel gameModel;

    private JPanel timeLinePanel = new JPanel();
    private PlotPanel plotPanel = new PlotPanel();
    private AxisPanel priceAxisPanel;

    private int numberOfCandleSticksToPaint = 20;
    private int PLOT_VIEW_HEIGHT = 100;
    private int PLOT_VIEW_WIDTH = 500;

    private JTextArea currentPrice = new JTextArea();
    private JTextArea openPrice = new JTextArea();
    private JTextArea minPrice = new JTextArea();
    private JTextArea maxPrice = new JTextArea();

    public PlotView(GameModel gameModel) {
        this.gameModel = gameModel;
        initPlotView();

    }

    //todo dodaj strategie wyswietlania wykresu

    private void paintCandleStick(int leftX, CandleStick candleStick, Graphics2D g2D, int width) {
        //todo
        // double scale = 0.4;
        int y = (int) (candleStick.getOpenPricePercentHeight() * (double) plotPanel.getHeight());
        int height = (int) (((candleStick.getClosePricePercentHeight() - candleStick.getOpenPricePercentHeight()) * plotPanel.getHeight()));
        int middleOfCandle = leftX + width / 2;
        //drawing body of candle
        g2D.setColor(candleStick.getColor());
        if (height > 0) {
            g2D.fill(new Rectangle(leftX, y, width, abs(height)));
        } else {
            g2D.fill(new Rectangle(leftX, y - abs(height), width, abs(height)));
        }

        //drawing the candlewick
        g2D.drawLine(middleOfCandle, (int) (candleStick.getMinPricePercentHeight() * plotPanel.getHeight()), middleOfCandle,
                (int) (candleStick.getMaxPricePercentHeight() * plotPanel.getHeight()));
    }

    private void paintAllCandleSticks(Graphics2D g2D) {
        CurrencyModel.PacketToDraw packetToDraw = gameModel.getChoosenCurrencyModel().getPacketToDraw(numberOfCandleSticksToPaint);
        ArrayList<CandleStick> arrayListToDraw = packetToDraw.getCandleSticks();
        CandleStick lastCandleStick = arrayListToDraw.get(arrayListToDraw.size()-1);

        int candleStickWidth = plotPanel.getWidth()/arrayListToDraw.size();
        int middleOfLastCandleStick = arrayListToDraw.size()*candleStickWidth - candleStickWidth/2;

        //painting bodys of candlesticks
        for(int i = 0; i<arrayListToDraw.size(); i++){
            paintCandleStick(i*candleStickWidth,arrayListToDraw.get(i),g2D,candleStickWidth);
        }
        paintCurrentPriceLine(g2D, lastCandleStick, middleOfLastCandleStick);
    }

    private void paintCurrentPriceLine(Graphics2D g2D, CandleStick lastCandleStick, int middleOfLastCandleStick) {
        g2D.setColor(lastCandleStick.getColor());
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(0, (int) (lastCandleStick.getClosePricePercentHeight() * (double) plotPanel.getHeight()),
                middleOfLastCandleStick,
                (int) (lastCandleStick.getClosePricePercentHeight() * (double) plotPanel.getHeight()));
        plotPanel.add(currentPrice);
        currentPrice.setBounds(0, plotPanel.getHeight() - (int) (lastCandleStick.getClosePricePercentHeight() * (double) plotPanel.getHeight()),
                300,
                50);
        DecimalFormat dec = new DecimalFormat("#0.00");
        currentPrice.setText("$ " + dec.format(lastCandleStick.getClosePrice()));
        currentPrice.setForeground(Color.WHITE);
        currentPrice.setOpaque(false);
    }

    public void initPlotView() {
        initPriceAxisPanel();
        initTimeLinePanel();
        initPlotPanel();
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(PLOT_VIEW_WIDTH, PLOT_VIEW_HEIGHT));
        this.add(timeLinePanel, BorderLayout.SOUTH);
        this.add(plotPanel, BorderLayout.CENTER);
        this.add(priceAxisPanel, BorderLayout.WEST);
    }

    private void initPriceAxisPanel() {
        priceAxisPanel = new AxisPanel();


    }

    private void initPlotPanel() {
        plotPanel.setLayout(null);
        plotPanel.setBackground(Color.BLACK);
    }

    private void initTimeLinePanel() {
        timeLinePanel.setBackground(Color.MAGENTA);
    }

    @Override
    public void update() {
        plotPanel.repaint();
        priceAxisPanel.repaint();
    }

    private class PlotPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.scale(1, -1);
            g2D.translate(0, -getHeight());

            //todo
            paintAllCandleSticks(g2D);


            g2D.scale(1, -1);
            g2D.translate(0, getHeight());
        }
    }

    private class AxisPanel extends JPanel {
        private int heigthOfLabel = 50;
        private int widthOfLabel = 50;
        private int amountOfLabels = 10;
        private JTextArea[] labelArrayList = new JTextArea[amountOfLabels];
        private JPanel[] grid = new JPanel[amountOfLabels];

        public AxisPanel() {
            this.setLayout(new GridLayout(amountOfLabels,1,0,0));
            initLabelArrayList();
            for (int i = grid.length - 1; i > 0; i--) {
                grid[i] = new JPanel();
                grid[i].add(labelArrayList[i]);
                this.add(grid[i]);
            }
        }

        private void initLabelArrayList() {
            for (int i = labelArrayList.length - 1; i > 0; i--) {
                labelArrayList[i] = new JTextArea();
            }
            updateLabels();
        }

        private void updateLabels(){
            CurrencyModel.PacketToDraw packetToDraw = gameModel.getChoosenCurrencyModel().getPacketToDraw(numberOfCandleSticksToPaint);
            String text;
            for (int i = labelArrayList.length - 1; i > 0; i--) {
                text = String.valueOf((((double)i / (double)labelArrayList.length) *
                        (packetToDraw.getMaxPriceInArrayList() - packetToDraw.getMinPriceInArrayList())));
                labelArrayList[i].setText(text);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            updateLabels();
        }


    }
}

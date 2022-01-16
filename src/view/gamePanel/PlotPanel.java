package view.gamePanel;

import interfaces.Observer;
import model.CurrencyModel;
import model.GameModel;
import utilities.CandleStick;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class PlotPanel extends JPanel implements Observer {
    @Serial
    private static final long serialVersionUID = -6105297773901533785L;
    private int numberOfCandleSticksToPaint;
    private CurrencyModel.PacketToDraw packetToDraw;

    //====================================================Public Methods==============================================//

    public PlotPanel() {
        initPlotPanel();
        update();
    }

    @Override
    public void update() {
        this.repaint();
    }

    public int getNumberOfCandleSticksToPaint() {
        return numberOfCandleSticksToPaint;
    }

    public void setNumberOfCandleSticksToPaint(int numberOfCandleSticksToPaint) {
        this.numberOfCandleSticksToPaint = numberOfCandleSticksToPaint;
    }

    //====================================================Private Methods=============================================//

    private void initPlotPanel() {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
    }

    private void paintCandleStick(int leftX, CandleStick candleStick, Graphics2D g2D, int width) {
        int y = (int) (candleStick.getOpenPricePercentHeight() * (double) this.getHeight());
        int height = (int) (((candleStick.getClosePricePercentHeight() - candleStick.getOpenPricePercentHeight()) * this.getHeight()));
        int middleOfCandle = leftX + width / 2;
        Color color = switch (candleStick.getColor()) {
            case UP -> Color.green;
            case DOWN -> Color.RED;
        };
        //drawing body of candle
        g2D.setColor(color);
        if (height > 0) {
            g2D.fill(new Rectangle(leftX, y, width, abs(height)));
        } else {
            g2D.fill(new Rectangle(leftX, y - abs(height), width, abs(height)));
        }

        //drawing the candlewick
        g2D.drawLine(middleOfCandle, (int) (candleStick.getMinPricePercentHeight() * this.getHeight()), middleOfCandle,
                (int) (candleStick.getMaxPricePercentHeight() * this.getHeight()));
    }

    private void paintAllCandleSticks(Graphics2D g2D, CurrencyModel.PacketToDraw packetToDraw, int numberOfCandleSticksToPaint) {
        ArrayList<CandleStick> arrayListToDraw = packetToDraw.getCandleSticks();
        CandleStick lastCandleStick = arrayListToDraw.get(arrayListToDraw.size() - 1);
        int candleStickWidth = this.getWidth() / numberOfCandleSticksToPaint;
        int middleOfLastCandleStick = arrayListToDraw.size() * candleStickWidth - candleStickWidth / 2;

        for (int i = 0; i < arrayListToDraw.size(); i++) {
            paintCandleStick(i * candleStickWidth, arrayListToDraw.get(i), g2D, candleStickWidth);
        }
        paintCurrentPriceLine(g2D, lastCandleStick, middleOfLastCandleStick);
    }

    private void paintCurrentPriceLine(Graphics2D g2D, CandleStick lastCandleStick, int middleOfLastCandleStick) {
        Color color = switch (lastCandleStick.getColor()) {
            case UP -> Color.GREEN;
            case DOWN -> Color.RED;
        };
        g2D.setColor(color);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(0, (int) (lastCandleStick.getClosePricePercentHeight() * (double) this.getHeight()),
                middleOfLastCandleStick,
                (int) (lastCandleStick.getClosePricePercentHeight() * (double) this.getHeight()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.scale(1, -1);
        g2D.translate(0, -getHeight());

        paintAllCandleSticks(g2D, packetToDraw, numberOfCandleSticksToPaint);

        g2D.scale(1, -1);
        g2D.translate(0, getHeight());
    }
}


package mainFrame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JPanel centerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    public GamePanel(){
        this.setLayout(new BorderLayout());
        initBorderLayoutPanels();

    }

    private void initBorderLayoutPanels(){
        initCenterPanel();
        initTopPanel();
        initBottomPanel();
        initRightPanel();
        initLeftPanel();

    }

    private void initLeftPanel() {
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.setBackground(Color.blue);
        leftPanel.setBounds(0,0,200,0);
        this.add(leftPanel,BorderLayout.WEST);
    }

    private void initRightPanel() {
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.blue);
        this.add(rightPanel,BorderLayout.EAST);
    }

    private void initBottomPanel() {
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottomPanel.setBackground(Color.red);
        this.add(bottomPanel,BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.setBackground(Color.red);
        this.add(topPanel,BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setBackground(Color.GREEN);
        this.add(centerPanel,BorderLayout.CENTER);
    }

}

package mainFrame;

import Interfaces.Observer;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements Observer, Runnable {
    private final int HEIGHT = 600;
    private final int WIDTH = 1000;
    private final String TITLE = "Giełda - Wiktor Stankiewicz";
    private final JPanel containerPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savesPanel = new JPanel();
    private final JPanel createGamePanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private GameView gameView;
    private GameModel gameModel;

    public MainFrame(GameModel model) {
        this.gameModel = model;
    }

    @Override
    public void run() {
        initMainFrame();
        gameModel = new GameModel();
        gameView = new GameView(gameModel);
        this.add(gameView);
        this.pack();
        this.setVisible(true);
    }

    private void initMainFrame(){
        this.setTitle(TITLE);
        //this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initCreateGamePanel() {
        createGamePanel.setBackground(Color.blue);
        createGamePanel.add(new JTextArea("Create Game Panel"));
    }

    private void initGamePanel() {
        gameView.setBackground(Color.red);
        gameView.add(new JTextArea("Game Panel"));
    }

    private void initSavesPanel() {
        savesPanel.setBackground(Color.green);
        savesPanel.add(new JTextArea("Saves Panel"));
    }

    @Override
    public void update() {

    }

    private void initContainerPanel() {
        containerPanel.setLayout(cardLayout);
        containerPanel.add(mainPanel, "mainPanel");
        containerPanel.add(savesPanel, "savesPanel");
        containerPanel.add(gameView, "gamePanel");
        containerPanel.add(createGamePanel, "createGamePanel");
        containerPanel.setSize(WIDTH, HEIGHT);
    }

    private void initMainPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        JTextField gameTitle = new JTextField("Symulator giełdy kryptowalut", 1);
        gameTitle.setEditable(false);
        gameTitle.setFocusable(false);
        gameTitle.setOpaque(false);
        gameTitle.setFont(new Font("Comic sans", Font.PLAIN, 55));
        gameTitle.setHorizontalAlignment(JTextField.CENTER);

        JButton savesButton = initSavesButton();
        JButton createGameButton = initCreateGameButton();
        JButton exitGameButton = initExitGameButton();
        JButton dummyGameButton = initDummyGameButton();

        buttonsPanel.add(savesButton);
        buttonsPanel.add(createGameButton);
        buttonsPanel.add(exitGameButton);
        buttonsPanel.add(dummyGameButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(gameTitle, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.setSize(WIDTH, HEIGHT);
    }

    private JButton initDummyGameButton() {
        JButton dummyGameButton = new JButton("Graj - todo");
        dummyGameButton.setFont(new Font("Comic sans", Font.BOLD, 30));
        dummyGameButton.addActionListener(new dummyGameButtonClicked());
        return dummyGameButton;
    }

    private JButton initExitGameButton() {
        JButton exitGameButton = new JButton("Wyjdź do Windows");
        exitGameButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        exitGameButton.addActionListener(new ExitButtonClicked());
        return exitGameButton;
    }

    private JButton initCreateGameButton() {
        JButton createGameButton = new JButton("Stwórz nową grę");
        createGameButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        createGameButton.addActionListener(new CreateGameButtonClicked());
        return createGameButton;
    }

    private JButton initSavesButton() {
        JButton savesButton = new JButton("Wczytaj zapis");
        savesButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        savesButton.addActionListener(new SavesButtonClicked());
        return savesButton;
    }

    private class ExitButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }

    private class CreateGameButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Create Game button clicked!");
            cardLayout.show(containerPanel, "createGamePanel");
        }
    }

    private class SavesButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Saves button clicked!");
            cardLayout.show(containerPanel, "savesPanel");
        }
    }

    private class dummyGameButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("dummyGameButton clicked!");
            cardLayout.show(containerPanel, "gamePanel");
        }
    }
}

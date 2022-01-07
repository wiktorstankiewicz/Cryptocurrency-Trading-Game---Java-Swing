package mainFrame;

import Interfaces.Observer;
import jdk.jshell.execution.Util;
import model.GameModel;
import model.Launcher;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.io.Serializable;

public class MainFrame extends JFrame implements Observer, Runnable, Serializable {
    @Serial
    private static final long serialVersionUID = -5685591881236472424L;
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
    private Launcher launcher;

    public MainFrame(Launcher launcher, GameModel gameModel) {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.launcher = launcher;
        this.gameModel = gameModel;
        this.gameView = new GameView(gameModel);
        this.addWindowListener(new MainFrameListener());
        gameModel.addObserver(gameView);
        gameModel.addObserver(gameView.getPlotView());
        initMainFrame();
        initMainPanel();
        initSavesPanel();
        initContainerPanel();
        this.add(containerPanel);
    }

    private class MainFrameListener extends WindowAdapter  {
            @Override
            public void windowClosing(WindowEvent e) {
                /*int option = JOptionPane.showOptionDialog(
                        MainFrame.this,
                        "Czy na pewno chcesz zakończyć grę?",
                        "Czy zakończyć?", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null,
                        new Object[]{"Zapisz i zakończ","Zakończ bez zapisywania","Anuluj"},
                        null );
                switch (option) {
                    case (0) -> {
                        System.out.println("Window Closed");
                        launcher.serialize(gameModel);
                        System.exit(42);
                    }
                    case (1) -> {
                        System.exit(41);
                    }
                }*/
                System.out.println("Window Closed");
                launcher.serialize(gameModel);
                System.exit(42);
            }

            @Override
            public void windowClosed(WindowEvent e) {


            }
        }


    @Override
    public void run() {
        cardLayout.show(containerPanel,"savesPanel");
        System.out.println("Done");
        this.pack();
        this.setVisible(true);
    }

    private void initMainFrame() {
        this.setTitle(TITLE);
        //this.setSize(WIDTH, HEIGHT);
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
        savesPanel.setLayout(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> jList = new JList<>(listModel);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addElement("1");
        listModel.addElement("2");
        listModel.addElement("3");
        listModel.addElement("4");
        savesPanel.add(jList);
        //savesPanel.add(new JTextArea("Saves Panel"));
    }

    @Override
    public void update() {

    }

    private void initContainerPanel() {
        containerPanel.setLayout(cardLayout);
        containerPanel.add(gameView, "gameView");
        containerPanel.add(mainPanel, "mainPanel");
        containerPanel.add(savesPanel, "savesPanel");

        //containerPanel.setSize(WIDTH, HEIGHT);
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

        buttonsPanel.add(savesButton);
        buttonsPanel.add(createGameButton);
        buttonsPanel.add(exitGameButton);

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

package view;

import view.gamePanel.GamePanel;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

import static utilities.Utilities.addGridOfJPanels;

@SuppressWarnings("ALL")
public class MainFrame extends JFrame implements Runnable {
    private static final Dimension SMALL_DIMENSION = new Dimension(300, 300);
    private static final Dimension BIG_DIMENSION = new Dimension(1200, 600);
    @SuppressWarnings("FieldCanBeLocal")
    private final String TITLE = "Giełda - Wiktor Stankiewicz";
    private final String SAVE_FILE_NAME = "save.ser";

    private final JPanel containerPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savesPanel = new JPanel();
    private final JPanel createGamePanel = new JPanel();

    private final CardLayout cardLayout = new CardLayout();

    private JSpinner createGameStartingFunds = new JSpinner();

    private ArrayList<GameModel> gameModelArrayList = new ArrayList<>();
    private JList<GameModel> gameModelSelectionJList = new JList<>();

    private GamePanel gamePanel;

    //====================================================Public Methods==============================================//

    public MainFrame() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MainFrameListener());
        initMainFrame();
        initMainPanel();
        initSavesPanel();
        initCreateGamePanel();
        initContainerPanel();
        this.add(containerPanel);
    }

    @Override
    public void run() {
        deserialize();
        this.setSize(BIG_DIMENSION);
        cardLayout.show(containerPanel, "mainPanel");
        this.setVisible(true);
    }

    //====================================================Private Methods=============================================//

    private void initMainFrame() {
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
    }

    private void initCreateGamePanel() {
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JButton acceptButton = new JButton("Akceptuj");
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        JPanel[][] grid = addGridOfJPanels(5, 2, centerPanel, 1, 1);
        JLabel title = new JLabel("Tworzenie nowej gry");
        JLabel name = new JLabel("Nazwa: ");
        JTextField gameNameInputJTextField = new JTextField();
        JLabel startingFunds = new JLabel("Srodki początkowe: ");

        createGameStartingFunds = new JSpinner(spinnerNumberModel);
        createGamePanel.setLayout(new BorderLayout());
        createGamePanel.add(southPanel, BorderLayout.SOUTH);
        createGamePanel.add(centerPanel, BorderLayout.CENTER);
        createGamePanel.add(northPanel, BorderLayout.NORTH);

        title.setFocusable(false);
        title.setOpaque(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        name.setFocusable(false);
        name.setHorizontalAlignment(SwingConstants.CENTER);

        startingFunds.setFocusable(false);
        startingFunds.setHorizontalAlignment(SwingConstants.CENTER);

        spinnerNumberModel.setMinimum(5000);
        spinnerNumberModel.setMaximum(1000000);
        spinnerNumberModel.setValue(125000);
        spinnerNumberModel.setStepSize(5000);

        acceptButton.addActionListener(e -> {
            addGameModel(new GameModel(gameNameInputJTextField.getText(),
                    ((Integer) createGameStartingFunds.getValue()).doubleValue()));
            refreshSavesPanel();
            this.setSize(BIG_DIMENSION);
            cardLayout.show(containerPanel, "savesPanel");
        });

        northPanel.add(title);
        southPanel.add(acceptButton);

        grid[0][0].add(name);
        grid[1][0].add(startingFunds);
        grid[1][1].add(createGameStartingFunds);
        grid[0][1].add(gameNameInputJTextField);
    }

    private void addGameModel(GameModel gameModel) {
        this.gameModelArrayList.add(gameModel);
    }

    private void initSavesPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 1, 1));

        initGameModelSelectionJList();
        JButton confirmSelectionOfSaveButton = new JButton("Wybierz");
        JButton deleteSelectedSaveButton = new JButton("Usuń zapis");

        JButton goBackButton = new JButton("Wróć");
        goBackButton.addActionListener(new GoBackButtonPressed());

        confirmSelectionOfSaveButton.addActionListener(new ConfirmSelectionOfSaveButtonListener());
        deleteSelectedSaveButton.addActionListener(new DeleteSelectedSaveButtonListener());

        savesPanel.setBackground(Color.green);
        savesPanel.setLayout(new BorderLayout());
        savesPanel.add(gameModelSelectionJList, BorderLayout.CENTER);
        savesPanel.add(southPanel, BorderLayout.SOUTH);

        southPanel.add(confirmSelectionOfSaveButton);
        southPanel.add(deleteSelectedSaveButton);
        southPanel.add(goBackButton);
    }

    private void initGameModelSelectionJList() {
        DefaultListModel<GameModel> listModel = new DefaultListModel<>();

        gameModelSelectionJList = new JList<>(listModel);
        gameModelSelectionJList.setLayoutOrientation(JList.VERTICAL);
        gameModelSelectionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addAll(gameModelArrayList);
    }

    private void refreshSavesPanel() {
        ((DefaultListModel<GameModel>) gameModelSelectionJList.getModel()).removeAllElements();
        ((DefaultListModel<GameModel>) gameModelSelectionJList.getModel()).addAll(gameModelArrayList);
    }

    private void initContainerPanel() {
        containerPanel.setLayout(cardLayout);
        containerPanel.add(mainPanel, "mainPanel");
        containerPanel.add(savesPanel, "savesPanel");
        containerPanel.add(createGamePanel, "createGamePanel");
    }

    private void initMainPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JLabel gameTitle = new JLabel("Symulator giełdy kryptowalut");
        JButton savesButton = new JButton("Wczytaj zapis");
        JButton createGameButton = new JButton("Stwórz nową grę");
        JButton exitGameButton = new JButton("Wyjdź do Windows");

        savesButton.setFont(new Font("Arial", Font.BOLD, 30));
        savesButton.addActionListener(new SavesButtonClicked());

        createGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        createGameButton.addActionListener(new CreateGameButtonClicked());

        exitGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        exitGameButton.addActionListener(new ExitButtonClicked());

        savesButton.setFocusable(false);
        createGameButton.setFocusable(false);
        exitGameButton.setFocusable(false);

        gameTitle.setFocusable(false);
        gameTitle.setOpaque(false);
        gameTitle.setFont(new Font("Comic sans", Font.PLAIN, 55));
        gameTitle.setHorizontalAlignment(JTextField.CENTER);

        buttonsPanel.add(savesButton);
        buttonsPanel.add(createGameButton);
        buttonsPanel.add(exitGameButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(gameTitle, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
    }

    private void serialize() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME));
            os.writeObject(gameModelArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserialize() {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream("save.ser"));
            //noinspection unchecked
            gameModelArrayList = (ArrayList<GameModel>) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void removeSave(GameModel selectedSave) {
        gameModelArrayList.remove(selectedSave);
    }

    //====================================================Inner Classes===============================================//

    private class ExitButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }

    private class CreateGameButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrame.this.setSize(SMALL_DIMENSION);
            cardLayout.show(containerPanel, "createGamePanel");
        }
    }

    private class SavesButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshSavesPanel();
            cardLayout.show(containerPanel, "savesPanel");
        }
    }

    private class ConfirmSelectionOfSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameModel selectedSave = gameModelSelectionJList.getSelectedValue();
            if (selectedSave == null) {
                return;
            }
            gamePanel = new GamePanel(selectedSave);
            containerPanel.add(gamePanel, "gameView");
            cardLayout.show(containerPanel, "gameView");
        }
    }

    private class DeleteSelectedSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GameModel selectedSave = gameModelSelectionJList.getSelectedValue();
            int selectedIndex = gameModelSelectionJList.getSelectedIndex();
            DefaultListModel<GameModel> model = (DefaultListModel<GameModel>) gameModelSelectionJList.getModel();

            if (selectedIndex < 0) {
                return;
            }
            model.remove(selectedIndex);
            MainFrame.this.removeSave(selectedSave);
        }
    }

    private class GoBackButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(containerPanel, "mainPanel");
        }
    }

    private class MainFrameListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(
                    MainFrame.this,
                    "Czy na pewno chcesz zakończyć grę?",
                    "Czy zakończyć?", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null,
                    new Object[]{"Zapisz i zakończ", "Zakończ bez zapisywania", "Anuluj"},
                    null);
            switch (option) {
                case (0) -> {
                    serialize();
                    System.exit(0);
                }
                case (1) -> System.exit(0);
                default -> {
                    return;
                }
            }
            System.exit(1);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            windowClosing(e);
        }
    }
}

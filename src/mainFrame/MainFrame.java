package mainFrame;

import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

import static Utilities.Utilities.addJPanelsToGrid;

public class MainFrame extends JFrame implements Runnable {
    private static final Dimension SMALL_DIMENSION = new Dimension(300, 300);
    private static final Dimension BIG_DIMENSION = new Dimension(1000, 600);
    private final String TITLE = "Giełda - Wiktor Stankiewicz";
    private final String SAVE_FILE_NAME = "save.ser";

    private final JPanel containerPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savesPanel = new JPanel();
    private final JPanel createGamePanel = new JPanel();

    private final CardLayout cardLayout = new CardLayout();

    private JTextField createGameNameTextField = new JTextField();
    private JSpinner createGameStartingFunds = new JSpinner();

    private ArrayList<GameModel> gameModelArrayList = new ArrayList<>();
    private JList<GameModel> gameModelSelectionJList = new JList<>();

    private GameView gameView;

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
        this.setSize(BIG_DIMENSION);
        this.deserialize();
        cardLayout.show(containerPanel, "mainPanel");
        System.out.println("Done");
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
        JPanel[][] grid = addJPanelsToGrid(5, 2, centerPanel, 1, 1);
        JTextField title = new JTextField("Tworzenie nowej gry");
        JTextField name = new JTextField("Nazwa: ");
        JTextField gameNameInputJTextField = new JTextField();
        JTextField startingFunds = new JTextField("Srodki początkowe: ");

        createGameStartingFunds = new JSpinner(spinnerNumberModel);
        createGameNameTextField = new JTextField();

        createGamePanel.setLayout(new BorderLayout());
        createGamePanel.add(southPanel, BorderLayout.SOUTH);
        createGamePanel.add(centerPanel, BorderLayout.CENTER);
        createGamePanel.add(northPanel, BorderLayout.NORTH);

        title.setEditable(false);
        title.setFocusable(false);
        title.setOpaque(false);

        name.setEditable(false);
        name.setFocusable(false);

        startingFunds.setEditable(false);
        startingFunds.setFocusable(false);

        spinnerNumberModel.setMinimum(5000);
        spinnerNumberModel.setMaximum(1000000);
        spinnerNumberModel.setValue(125000);
        spinnerNumberModel.setStepSize(5000);

        acceptButton.addActionListener(e -> {
            addGameModel(new GameModel(gameNameInputJTextField.getText(),
                    ((Integer) createGameStartingFunds.getValue()).doubleValue()));
            System.out.println("Utworzono nowy zapis" + gameNameInputJTextField.getText());
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
        JTextField gameTitle = new JTextField("Symulator giełdy kryptowalut", 1);
        JButton savesButton = initSavesButton();
        JButton createGameButton = initCreateGameButton();
        JButton exitGameButton = initExitGameButton();

        gameTitle.setEditable(false);
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

    private JButton initExitGameButton() {
        JButton exitGameButton = new JButton("Wyjdź do Windows");

        exitGameButton.setFont(new Font("Arial", Font.BOLD, 30));
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

    private void serialize() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME));
            os.writeObject(gameModelArrayList);
            System.out.println("Pomyślnie zapisano " + gameModelArrayList.size() + " zapisów");
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
            deserialize();
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
            gameView = new GameView(selectedSave);
            containerPanel.add(gameView, "gameView");
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
            serialize();
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
                    System.out.println("Window Closed");
                    serialize();
                    System.exit(42);
                }
                case (1) -> System.exit(41);
            }
            System.exit(42);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            windowClosing(e);
        }
    }
}

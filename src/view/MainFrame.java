package view;

import model.GameModel;
import view.gamePanel.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.util.ArrayList;

import static utilities.Utilities.addGridOfJPanels;

@SuppressWarnings("ALL")
public class MainFrame extends JFrame {
    private static final Dimension SMALL_DIMENSION = new Dimension(300, 300);
    private static final Dimension BIG_DIMENSION = new Dimension(1200, 600);
    @SuppressWarnings("FieldCanBeLocal")
    private final String TITLE = "Giełda - Wiktor Stankiewicz";
    private final String SAVE_FILE_NAME = "save.ser";

    private final JPanel containerPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savesPanel = new JPanel();
    private final JPanel createGamePanel = new JPanel();

    private final JButton acceptButton = new JButton("Akceptuj");
    private final JButton confirmSelectionOfSaveButton = new JButton("Wybierz");
    private final JButton deleteSelectedSaveButton = new JButton("Usuń zapis");
    private final JButton goBackButton = new JButton("Wróć");
    private final JButton savesButton = new JButton("Wczytaj zapis");
    private final JButton createGameButton = new JButton("Stwórz nową grę");
    private final JButton exitGameButton = new JButton("Wyjdź do Windows");

    private final CardLayout cardLayout = new CardLayout();

    private JSpinner createGameStartingFunds = new JSpinner();

    private ArrayList<GameModel> gameModelArrayList = new ArrayList<>();
    private JList<GameModel> gameModelSelectionJList = new JList<>();


    //====================================================Public Methods==============================================//

    public MainFrame() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //this.addWindowListener(new MainFrameListener());
        initMainFrame();
        initMainPanel();
        initSavesPanel();
        initCreateGamePanel();
        initContainerPanel();
        this.add(containerPanel);
    }

    public void start() {
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

        //setSpinnerParameters(spinnerNumberModel);

        /*acceptButton.addActionListener(e -> {
            addGameModel(new GameModel(gameNameInputJTextField.getText(),
                    ((Integer) createGameStartingFunds.getValue()).doubleValue()));
            refreshSavesPanel();
            this.setSize(BIG_DIMENSION);
            cardLayout.show(containerPanel, "savesPanel");
        });*/

        northPanel.add(title);
        southPanel.add(acceptButton);

        grid[0][0].add(name);
        grid[1][0].add(startingFunds);
        grid[1][1].add(createGameStartingFunds);
        grid[0][1].add(gameNameInputJTextField);
    }

    private void setSpinnerParameters(int min, int max, int initialValue, int stepSize) {
        SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel) createGameStartingFunds.getModel();
        spinnerNumberModel.setMinimum(min);
        spinnerNumberModel.setMaximum(max);
        spinnerNumberModel.setValue(initialValue);
        spinnerNumberModel.setStepSize(stepSize);
    }

    private void addGameModel(GameModel gameModel) {
        this.gameModelArrayList.add(gameModel);
    }

    private void initSavesPanel() {
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 1, 1));

        initGameModelSelectionJList();

        //goBackButton.addActionListener(new GoBackButtonPressed());

        //confirmSelectionOfSaveButton.addActionListener(new ConfirmSelectionOfSaveButtonListener());
        //deleteSelectedSaveButton.addActionListener(new DeleteSelectedSaveButtonListener());

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

    public void refreshSavesPanel() {
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


        savesButton.setFont(new Font("Arial", Font.BOLD, 30));
        //savesButton.addActionListener(new SavesButtonClicked());

        createGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        //createGameButton.addActionListener(new CreateGameButtonClicked());

        exitGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        //exitGameButton.addActionListener(new ExitButtonClicked());

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

    public void showMainPanel() {
        cardLayout.show(containerPanel, "mainPanel");
    }

    public void showSavesPanel() {
        cardLayout.show(containerPanel, "savesPanel");
    }

    public void showCreateGamePanel() {
        this.setSize(SMALL_DIMENSION);
        cardLayout.show(containerPanel, "createGamePanel");
    }

    public int showClosingDialog() {
        return JOptionPane.showOptionDialog(
                this,
                "Czy na pewno chcesz zakończyć grę?",
                "Czy zakończyć?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new Object[]{"Zapisz i zakończ", "Zakończ bez zapisywania", "Anuluj"},
                null);
    }

    public void addListenersToButtons(ActionListener acceptButtonListener,
                                      ActionListener confirmSelectionOfSaveButtonListener,
                                      ActionListener deleteSelectedSaveButtonListener,
                                      ActionListener goBackButtonListener,
                                      ActionListener exitGameButtonListener,
                                      ActionListener savesButtonListener,
                                      ActionListener createGameButtonListener) {
        acceptButton.addActionListener(acceptButtonListener);
        confirmSelectionOfSaveButton.addActionListener(confirmSelectionOfSaveButtonListener);
        deleteSelectedSaveButton.addActionListener(deleteSelectedSaveButtonListener);
        goBackButton.addActionListener(goBackButtonListener);
        exitGameButton.addActionListener(exitGameButtonListener);
        savesButton.addActionListener(savesButtonListener);
        createGameButton.addActionListener(createGameButtonListener);
    }

    public void addWindowListener(WindowAdapter windowAdapter){
        this.addWindowListener(windowAdapter);
    }

    //====================================================Inner Classes===============================================//


}

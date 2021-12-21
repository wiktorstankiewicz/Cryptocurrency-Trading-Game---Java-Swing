import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

public class StartFrame extends JFrame implements Observer, Runnable {
    private static final int HEIGHT = 500;
    private static final int WIDTH = 750;
    private static final String TITLE = "Giełda - Wiktor Stankiewicz";
    private static final JPanel ContainerPanel = new JPanel();
    private static final JPanel MAIN_PANEL = new JPanel();
    private static final JPanel SAVES_PANEL = new JPanel();
    private static final JPanel GAME_PANEL = new JPanel();
    private static final JPanel CREATE_GAME_PANEL = new JPanel();


    private static final CardLayout cardLayout = new CardLayout();
    private static final JButton LOAD_BUTTON = new JButton();
    private static final JButton CREATE_GAME_BUTTON = new JButton();
    private static final JButton EXIT_BUTTON = new JButton();
    private static final JTextField TITLE_JTEXT = new JTextField();

    private Observable model;

    public StartFrame(Observable model){
        this.model = model;
    }

    @Override
    public void run() {
        this.setTitle(TITLE);
        this.setSize(WIDTH,HEIGHT);
        ContainerPanel.setLayout(cardLayout);
        cardLayout.addLayoutComponent(MAIN_PANEL,"1");
        cardLayout.addLayoutComponent(SAVES_PANEL,SAVES_PANEL.getClass().getSimpleName());
        cardLayout.addLayoutComponent(GAME_PANEL,GAME_PANEL.getClass().getSimpleName());
        cardLayout.addLayoutComponent(CREATE_GAME_PANEL,"CREATE_GAME_PANEL");
        TITLE_JTEXT.setText("Symulator giełdy kryptowalut");
        TITLE_JTEXT.setFont(new Font("Arial",Font.PLAIN,50));
        TITLE_JTEXT.setBounds(0,0, WIDTH,60);
        TITLE_JTEXT.setHorizontalAlignment(JTextField.CENTER);
        TITLE_JTEXT.setEditable(false);
        TITLE_JTEXT.setFocusable(false);
        TITLE_JTEXT.setOpaque(false);

        ContainerPanel.setSize(WIDTH,HEIGHT - TITLE_JTEXT.getHeight());

        MAIN_PANEL.setSize(WIDTH,HEIGHT - TITLE_JTEXT.getHeight());
        MAIN_PANEL.setLayout(new GridBagLayout());

        LOAD_BUTTON.setText("Wczytaj zapis");
        LOAD_BUTTON.setFont(new Font("Arial",Font.PLAIN,30));
        LOAD_BUTTON.setFocusable(false);
        LOAD_BUTTON.addActionListener(e -> System.out.println("Load game"));
        MAIN_PANEL.add(LOAD_BUTTON);

        CREATE_GAME_BUTTON.setText("Nowy zapis");
        CREATE_GAME_BUTTON.setFont(new Font("Arial",Font.PLAIN,30));
        CREATE_GAME_BUTTON.setFocusable(false);
        CREATE_GAME_BUTTON.addActionListener(e -> cardLayout.show(ContainerPanel,"CREATE_GAME_PANEL"));
        MAIN_PANEL.add(CREATE_GAME_BUTTON);

        EXIT_BUTTON.setText("Wyjdź do Windows");
        EXIT_BUTTON.setFont(new Font("Arial",Font.PLAIN,30));
        EXIT_BUTTON.setFocusable(false);
        EXIT_BUTTON.addActionListener(e -> System.exit(1));
        MAIN_PANEL.add(EXIT_BUTTON);

        this.add(BorderLayout.NORTH,TITLE_JTEXT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.lightGray);
        this.add(BorderLayout.CENTER,ContainerPanel);
        this.setVisible(true);
        cardLayout.show(ContainerPanel,"1");
    }

    @Override
    public void update() {

    }
}

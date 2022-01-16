import controllers.MainFrameController;
import model.MainModel;
import view.MainFrame;

public class Launcher {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.run();
        MainFrameController mainFrameController =
                new MainFrameController(new MainModel(), new MainFrame());
    }
}

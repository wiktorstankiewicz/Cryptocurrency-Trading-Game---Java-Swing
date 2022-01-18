import controllers.MainFrameController;
import model.MainModel;
import view.MainFrame;

public class Launcher {
    public static void main(String[] args) {
        MainFrameController mainFrameController =
                new MainFrameController(new MainModel(), new MainFrame());
        mainFrameController.run();
    }
}

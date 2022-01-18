import mainFrameMVC.MainFrameController;
import mainFrameMVC.MainFrameModel;
import mainFrameMVC.MainFrame;

public class Launcher {
    public static void main(String[] args) {
        MainFrameController mainFrameController =
                new MainFrameController(new MainFrameModel(), new MainFrame());
        mainFrameController.run();
    }
}

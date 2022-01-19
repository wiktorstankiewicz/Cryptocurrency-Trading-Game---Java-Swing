import mainFrameMVC.MainFrameController;
import mainFrameMVC.MainFrameModel;
import mainFrameMVC.MainFrameView;

public class Launcher {
    public static void main(String[] args) {
        MainFrameController mainFrameController =
                new MainFrameController(new MainFrameModel(), new MainFrameView());
        mainFrameController.run();
    }
}

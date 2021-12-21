public class Launcher {
    public static void main(String[] args) {
        Model model = new Model();
        StartFrame startFrame = new StartFrame(model);
        startFrame.run();
        System.out.println("Done");
    }
}

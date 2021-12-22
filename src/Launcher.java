import mainFrame.MainFrame;
import model.CryptoCurrency;
import model.Game;
import model.Model;

import java.util.Arrays;

public class Launcher {

    public enum CryptoCurrencies{
        BITCOIN(new CryptoCurrency("Bitcoin!!!")),
        ETHEREUM(new CryptoCurrency("Ethereum")),
        DOGECOIN(new CryptoCurrency("DogeCoin"));

        private CryptoCurrency cryptoCurrency;

        CryptoCurrencies(CryptoCurrency cryptoCurrency) {
            this.cryptoCurrency = cryptoCurrency;
        }

        public CryptoCurrency getCryptoCurrency(){
            return this.cryptoCurrency;
        }
    }

    public static void main(String[] args) {
        /*Model model = new Model();
        Game game1 = new Game("Save 1", 0);
        System.out.println(game1);
        model.addSave(game1);
        MainFrame mainFrame = new MainFrame(model);
        mainFrame.run();
        System.out.println("Done");*/

        System.out.println(CryptoCurrencies.BITCOIN.getCryptoCurrency());
    }
}

package Constants;

import Utilities.CandleStick;
import Utilities.CryptoCurrency;

import java.util.ArrayList;
import java.util.Random;

public class ExCryptocurrencies {
    public static ArrayList<CryptoCurrency> availableCryptoCurrencies = new ArrayList<>();
    public static ArrayList<CandleStick> exampleCandleStickArray = new ArrayList<>();
    private static final double VOLATILITY = 0.001;
    private static final double EAR = 0.00001;
    private static final Random generator = new Random();

    static {
        availableCryptoCurrencies.add(new CryptoCurrency("Bitcoin",
                300, "resources/btc.png", VOLATILITY, EAR));
        availableCryptoCurrencies.add(new CryptoCurrency("Ethereum",
                300, "resources/btc.png", VOLATILITY, EAR));
        availableCryptoCurrencies.add(new CryptoCurrency("SHIB",
                300, "resources/btc.png", VOLATILITY, EAR));
        availableCryptoCurrencies.add(new CryptoCurrency("Matic",
                300, "resources/btc.png", VOLATILITY, EAR));
        availableCryptoCurrencies.add(new CryptoCurrency("Cardano",
                300, "resources/btc.png", VOLATILITY, EAR));
        availableCryptoCurrencies.add(new CryptoCurrency("TFuel",
                300, "resources/btc.png", VOLATILITY, EAR));

        CandleStick[] list = new CandleStick[20];
        list[0] = new CandleStick(200, 300, 500, 50,
                availableCryptoCurrencies.get(0));

        for (int i = 1; i < list.length; i++) {
            CandleStick prev = list[i - 1];
            list[i] = new CandleStick(
                    prev.getClosePriceHeight(),
                    prev.getClosePriceHeight() + generator.nextInt(-40, 40),
                    0, 0, availableCryptoCurrencies.get(0));
        }
        exampleCandleStickArray = new ArrayList<CandleStick>(java.util.List.of(list));
    }

}

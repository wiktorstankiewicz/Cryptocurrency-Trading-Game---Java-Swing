package Constants;

import Utilities.CryptoCurrency;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Constants {
    public static final ArrayList<CryptoCurrency> AVAILABLE_CRYPTO_CURRENCIES = new ArrayList<>();
    private static final double EAR = 0.0001;
    public static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat("#.##");

    static {
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Bitcoin",
                50000, "src/resources/btc.png", 0.15, EAR));
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Ethereum",
                2500, "src/resources/eth.png", 0.005, EAR));
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Matic",
                4, "src/resources/MATIC.png", 0.02, EAR));
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Cardano",
                10, "src/resources/ADA.png", 0.03, EAR));
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("TFuel",
                7500, "src/resources/TFuel.png", 0.05, EAR));
    }
}

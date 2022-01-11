package utilities;

import utilities.CryptoCurrency;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Constants {
    public static final ArrayList<CryptoCurrency> AVAILABLE_CRYPTO_CURRENCIES = new ArrayList<>();
    public static final DecimalFormat DOUBLE_FORMATTER = new DecimalFormat("#.##");

    static {
        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Bitcoin",
                50000,
                "src/resources/btc.png",
                1.5,
                5,
                0.001));

        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Ethereum",
                2500,
                "src/resources/eth.png",
                2,
                5,
                0.001));

        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Matic",
                4,
                "src/resources/MATIC.png",
                7,
                5,
                0.001));

        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("Cardano",
                10,
                "src/resources/ADA.png",
                1.2,
                5,
                0.001));

        AVAILABLE_CRYPTO_CURRENCIES.add(new CryptoCurrency("TFuel",
                7500,
                "src/resources/TFuel.png",
                4,
                5,
                0.001));
    }
}

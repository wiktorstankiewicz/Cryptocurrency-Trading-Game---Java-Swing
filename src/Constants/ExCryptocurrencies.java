package Constants;

import model.CryptoCurrency;

import java.util.ArrayList;

public class ExCryptocurrencies {
   public static ArrayList<CryptoCurrency> availableCryptoCurrencies = new ArrayList<>();

   static{
       availableCryptoCurrencies.add(new CryptoCurrency("Bitcoin!"));
       availableCryptoCurrencies.add(new CryptoCurrency("Matic!"));
       availableCryptoCurrencies.add(new CryptoCurrency("DogeCoin!"));
       availableCryptoCurrencies.add(new CryptoCurrency("Ethereum!"));
       availableCryptoCurrencies.add(new CryptoCurrency("Shiba Inu!"));
   }

}
